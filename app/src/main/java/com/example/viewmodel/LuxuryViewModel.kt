package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.CartItemEntity
import com.example.data.LuxuryCatalog
import com.example.data.LuxuryProduct
import com.example.data.OrderEntity
import com.example.data.UserProfileEntity
import com.example.engine.SapolskyEngine
import com.example.engine.SapolskyOutcome
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LuxuryViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val cartDao = db.cartDao()
    private val orderDao = db.orderDao()
    private val userProfileDao = db.userProfileDao()

    // State Flows from Room
    val cartItems: StateFlow<List<CartItemEntity>> = cartDao.getCartItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val orders: StateFlow<List<OrderEntity>> = orderDao.getAllOrders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userProfile: StateFlow<UserProfileEntity?> = userProfileDao.getUserProfile()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // UI state for filter, search, selection
    private val _selectedCategory = MutableStateFlow("All Luxury")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedProduct = MutableStateFlow<LuxuryProduct?>(null)
    val selectedProduct: StateFlow<LuxuryProduct?> = _selectedProduct.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<String>>(setOf("bag_01", "men_03"))
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    // Sapolsky Checkout UI State
    private val _showUpiSheet = MutableStateFlow(false)
    val showUpiSheet: StateFlow<Boolean> = _showUpiSheet.asStateFlow()

    private val _isProcessingPayment = MutableStateFlow(false)
    val isProcessingPayment: StateFlow<Boolean> = _isProcessingPayment.asStateFlow()

    private val _sapolskyOutcome = MutableStateFlow<SapolskyOutcome?>(null)
    val sapolskyOutcome: StateFlow<SapolskyOutcome?> = _sapolskyOutcome.asStateFlow()

    // Auth modal state
    private val _showAuthModal = MutableStateFlow(false)
    val showAuthModal: StateFlow<Boolean> = _showAuthModal.asStateFlow()

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    // Premium Membership & 2-unit limit state
    private val _isPremiumMember = MutableStateFlow(false)
    val isPremiumMember: StateFlow<Boolean> = _isPremiumMember.asStateFlow()

    private val _showUpgradePrompt = MutableStateFlow(false)
    val showUpgradePrompt: StateFlow<Boolean> = _showUpgradePrompt.asStateFlow()

    init {
        // Ensure default profile exists on startup
        viewModelScope.launch {
            userProfileDao.insertOrUpdateProfile(UserProfileEntity())
        }
    }

    fun selectCategory(category: String) {
        _selectedCategory.value = category
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectProduct(product: LuxuryProduct?) {
        _selectedProduct.value = product
    }

    fun toggleFavorite(productId: String) {
        val current = _favoriteIds.value.toMutableSet()
        if (current.contains(productId)) {
            current.remove(productId)
        } else {
            current.add(productId)
        }
        _favoriteIds.value = current
    }

    fun dismissUpgradePrompt() {
        _showUpgradePrompt.value = false
    }

    fun upgradeToPremiumMembership() {
        viewModelScope.launch {
            _isPremiumMember.value = true
            _showUpgradePrompt.value = false
            val current = userProfile.value ?: UserProfileEntity()
            userProfileDao.insertOrUpdateProfile(current.copy(vipTier = "VIP Atelier Pass (₹50/mo)"))
        }
    }

    fun addToCart(product: LuxuryProduct, selectedSize: String) {
        val currentTotalUnits = cartItems.value.sumOf { it.quantity }
        if (currentTotalUnits >= 2 && !_isPremiumMember.value) {
            _showUpgradePrompt.value = true
            return
        }

        viewModelScope.launch {
            val existingList = cartItems.value
            val match = existingList.find { it.productId == product.id && it.selectedSize == selectedSize }
            if (match != null) {
                cartDao.insertOrUpdate(match.copy(quantity = match.quantity + 1))
            } else {
                cartDao.insertOrUpdate(
                    CartItemEntity(
                        productId = product.id,
                        title = product.title,
                        brand = product.brand,
                        category = product.category,
                        priceInINR = product.priceInINR,
                        selectedSize = selectedSize,
                        quantity = 1
                    )
                )
            }
        }
    }

    fun updateCartQuantity(cartItem: CartItemEntity, newQuantity: Int) {
        if (newQuantity > cartItem.quantity) {
            val currentTotalUnits = cartItems.value.sumOf { it.quantity }
            val proposedTotal = currentTotalUnits + (newQuantity - cartItem.quantity)
            if (proposedTotal > 2 && !_isPremiumMember.value) {
                _showUpgradePrompt.value = true
                return
            }
        }

        viewModelScope.launch {
            if (newQuantity <= 0) {
                cartDao.delete(cartItem)
            } else {
                cartDao.insertOrUpdate(cartItem.copy(quantity = newQuantity))
            }
        }
    }

    fun removeFromCart(cartItem: CartItemEntity) {
        viewModelScope.launch {
            cartDao.delete(cartItem)
        }
    }

    fun openUpiSheet() {
        if (cartItems.value.isNotEmpty()) {
            _showUpiSheet.value = true
        }
    }

    fun dismissUpiSheet() {
        _showUpiSheet.value = false
        _isProcessingPayment.value = false
    }

    fun triggerSapolskyCheckout(upiPin: String) {
        viewModelScope.launch {
            _isProcessingPayment.value = true

            // Simulate realistic 2-second UPI authentication muscle memory delay
            kotlinx.coroutines.delay(2000)

            val currentCart = cartItems.value
            val totalAmount = currentCart.sumOf { it.priceInINR * it.quantity }
            val address = userProfile.value?.let { "${it.addressStreet}, ${it.addressCity}" }
                ?: "Bandra West, Mumbai, Maharashtra"

            val outcome = SapolskyEngine.evaluateCheckout(
                totalAmountINR = totalAmount,
                itemCount = currentCart.sumOf { it.quantity }
            )

            // Save order record to Room database
            when (outcome) {
                is SapolskyOutcome.Success -> {
                    val orderEntity = OrderEntity(
                        orderId = outcome.orderId,
                        totalAmountINR = outcome.totalAmountINR,
                        itemCount = currentCart.sumOf { it.quantity },
                        sapolskyStatus = "SUCCESS",
                        deliveryAddress = address,
                        pointsAwarded = outcome.gemsEarned,
                        itemsSummary = currentCart.joinToString(", ") { "${it.title} (x${it.quantity})" },
                        currentStep = "Order Dispatched from Delhi Hub"
                    )
                    orderDao.insertOrder(orderEntity)
                    userProfileDao.addGemsAndSavings(
                        gemsToAdd = outcome.gemsEarned,
                        savingsToAdd = outcome.totalAmountINR
                    )
                    userProfileDao.incrementStreak()
                    cartDao.clearCart()
                }
                is SapolskyOutcome.Rejected -> {
                    val orderEntity = OrderEntity(
                        orderId = outcome.orderId,
                        totalAmountINR = outcome.totalAmountINR,
                        itemCount = currentCart.sumOf { it.quantity },
                        sapolskyStatus = "OUT_OF_STOCK",
                        deliveryAddress = address,
                        pointsAwarded = outcome.gemsEarned,
                        itemsSummary = currentCart.joinToString(", ") { "${it.title} (x${it.quantity})" },
                        currentStep = "Order Refunded / Out of Stock"
                    )
                    orderDao.insertOrder(orderEntity)
                    userProfileDao.addGemsAndSavings(
                        gemsToAdd = outcome.gemsEarned,
                        savingsToAdd = 0L
                    )
                    userProfileDao.resetStreak()
                }
            }

            _isProcessingPayment.value = false
            _showUpiSheet.value = false
            _sapolskyOutcome.value = outcome
        }
    }

    fun dismissSapolskyOutcome() {
        _sapolskyOutcome.value = null
    }

    fun toggleAuthModal() {
        _showAuthModal.value = !_showAuthModal.value
    }

    fun loginUser(name: String, email: String, city: String) {
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfileEntity()
            userProfileDao.insertOrUpdateProfile(
                current.copy(
                    name = name.ifBlank { "Arpit Tripathi" },
                    email = email.ifBlank { "arpittripathi020@gmail.com" },
                    addressCity = city.ifBlank { "Mumbai, Maharashtra" }
                )
            )
            _isLoggedIn.value = true
            _showAuthModal.value = false
        }
    }

    fun logoutUser() {
        _isLoggedIn.value = false
        _showAuthModal.value = false
    }

    fun getFilteredProducts(): List<LuxuryProduct> {
        val category = _selectedCategory.value
        val query = _searchQuery.value.trim().lowercase()

        return LuxuryCatalog.products.filter { product ->
            val matchesCategory = category == "All Luxury" || product.category == category
            val matchesQuery = query.isEmpty() ||
                    product.title.lowercase().contains(query) ||
                    product.brand.lowercase().contains(query) ||
                    product.description.lowercase().contains(query)

            matchesCategory && matchesQuery
        }
    }
}
