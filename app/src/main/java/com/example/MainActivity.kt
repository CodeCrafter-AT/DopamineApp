package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AuthModal
import com.example.ui.components.LuxuryHeader
import com.example.ui.components.ProductDetailModal
import com.example.ui.components.SapolskyResultDialog
import com.example.ui.components.UpiPaymentSheet
import com.example.ui.screens.BagScreen
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.LiveTrackingScreen
import com.example.ui.screens.VipVaultScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.theme.NoirTheme
import com.example.viewmodel.LuxuryViewModel

enum class NavigationTab(val label: String, val activeIcon: ImageVector, val inactiveIcon: ImageVector) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home),
    DISCOVER("Discover", Icons.Filled.Search, Icons.Outlined.Search),
    REWARDS("Rewards", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome),
    ORDERS("Orders", Icons.Filled.LocalShipping, Icons.Outlined.LocalShipping),
    PROFILE("Profile", Icons.Filled.Person, Icons.Outlined.Person)
}

class MainActivity : ComponentActivity() {

    private val viewModel: LuxuryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NoirTheme {
                val cartItems by viewModel.cartItems.collectAsState()
                val orders by viewModel.orders.collectAsState()
                val userProfile by viewModel.userProfile.collectAsState()

                val selectedCategory by viewModel.selectedCategory.collectAsState()
                val searchQuery by viewModel.searchQuery.collectAsState()
                val selectedProduct by viewModel.selectedProduct.collectAsState()
                val favoriteIds by viewModel.favoriteIds.collectAsState()

                val showUpiSheet by viewModel.showUpiSheet.collectAsState()
                val isProcessingPayment by viewModel.isProcessingPayment.collectAsState()
                val sapolskyOutcome by viewModel.sapolskyOutcome.collectAsState()

                val showAuthModal by viewModel.showAuthModal.collectAsState()
                val isLoggedIn by viewModel.isLoggedIn.collectAsState()
                val showUpgradePrompt by viewModel.showUpgradePrompt.collectAsState()

                var currentTab by remember { mutableStateOf(NavigationTab.HOME) }
                val haptic = LocalHapticFeedback.current

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.navigationBars),
                    topBar = {
                        LuxuryHeader(
                            gemsCount = userProfile?.dopamineGems ?: 4850,
                            userName = userProfile?.name ?: "Arpit Tripathi",
                            vipTier = userProfile?.vipTier ?: "Black Card Atelier",
                            cartCount = cartItems.sumOf { it.quantity },
                            onOpenAuth = { viewModel.toggleAuthModal() },
                            onOpenBag = { currentTab = NavigationTab.PROFILE }
                        )
                    },
                    bottomBar = {
                        LuxuryBottomNavigationBar(
                            currentTab = currentTab,
                            cartCount = cartItems.sumOf { it.quantity },
                            onTabSelected = { tab ->
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                currentTab = tab
                            }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Crossfade(targetState = currentTab, label = "TabSwitch") { tab ->
                            when (tab) {
                                NavigationTab.HOME -> {
                                    CatalogScreen(
                                        products = viewModel.getFilteredProducts(),
                                        selectedCategory = selectedCategory,
                                        searchQuery = searchQuery,
                                        favoriteIds = favoriteIds,
                                        onSelectCategory = { viewModel.selectCategory(it) },
                                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                                        onSelectProduct = { viewModel.selectProduct(it) },
                                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                                        onQuickAdd = { viewModel.addToCart(it, it.sizes.firstOrNull() ?: "One Size") },
                                        onOpenTracking = { currentTab = NavigationTab.ORDERS }
                                    )
                                }
                                NavigationTab.DISCOVER -> {
                                    DiscoverScreen(
                                        products = viewModel.getFilteredProducts(),
                                        selectedCategory = selectedCategory,
                                        onSelectCategory = { viewModel.selectCategory(it) },
                                        searchQuery = searchQuery,
                                        onSearchQueryChange = { viewModel.setSearchQuery(it) },
                                        favoriteIds = favoriteIds,
                                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                                        onProductClick = { viewModel.selectProduct(it) },
                                        onQuickAdd = { viewModel.addToCart(it, it.sizes.firstOrNull() ?: "One Size") }
                                    )
                                }
                                NavigationTab.REWARDS -> {
                                    VipVaultScreen(
                                        profile = userProfile,
                                        onOpenAuth = { viewModel.toggleAuthModal() }
                                    )
                                }
                                NavigationTab.ORDERS -> {
                                    LiveTrackingScreen(orders = orders)
                                }
                                NavigationTab.PROFILE -> {
                                    BagScreen(
                                        cartItems = cartItems,
                                        onUpdateQuantity = { item, q -> viewModel.updateCartQuantity(item, q) },
                                        onRemoveItem = { item -> viewModel.removeFromCart(item) },
                                        onCheckout = { viewModel.openUpiSheet() }
                                    )
                                }
                            }
                        }

                        // Product Detail Modal Sheet
                        selectedProduct?.let { product ->
                            ProductDetailModal(
                                product = product,
                                isFavorite = favoriteIds.contains(product.id),
                                onDismiss = { viewModel.selectProduct(null) },
                                onToggleFavorite = { viewModel.toggleFavorite(product.id) },
                                onAddToCart = { size ->
                                    viewModel.addToCart(product, size)
                                    viewModel.selectProduct(null)
                                }
                            )
                        }

                        // UPI Payment Sheet
                        if (showUpiSheet) {
                            val totalINR = cartItems.sumOf { it.priceInINR * it.quantity }
                            val address = userProfile?.let { "${it.addressStreet}, ${it.addressCity}" }
                                ?: "Bandra West, Mumbai, Maharashtra"

                            UpiPaymentSheet(
                                totalINR = totalINR,
                                address = address,
                                isProcessing = isProcessingPayment,
                                onDismiss = { viewModel.dismissUpiSheet() },
                                onPay = { pin -> viewModel.triggerSapolskyCheckout(pin) }
                            )
                        }

                        // Sapolsky Outcome Overlay Dialog
                        sapolskyOutcome?.let { outcome ->
                            SapolskyResultDialog(
                                outcome = outcome,
                                onDismiss = { viewModel.dismissSapolskyOutcome() },
                                onViewLiveTracking = {
                                    viewModel.dismissSapolskyOutcome()
                                    currentTab = NavigationTab.ORDERS
                                }
                            )
                        }

                        // Profile / Auth Modal
                        if (showAuthModal) {
                            AuthModal(
                                profile = userProfile,
                                isLoggedIn = isLoggedIn,
                                onDismiss = { viewModel.toggleAuthModal() },
                                onLogin = { name, email, city -> viewModel.loginUser(name, email, city) },
                                onLogout = { viewModel.logoutUser() }
                            )
                        }

                        // Premium Membership Upgrade Dialog (2-Unit Limit Exceeded)
                        if (showUpgradePrompt) {
                            androidx.compose.ui.window.Dialog(
                                onDismissRequest = { viewModel.dismissUpgradePrompt() }
                            ) {
                                androidx.compose.material3.Surface(
                                    shape = RoundedCornerShape(24.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.outline,
                                            shape = RoundedCornerShape(24.dp)
                                        )
                                        .padding(24.dp)
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "2-UNIT LIMIT REACHED",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                fontFamily = FontFamily.Serif,
                                                fontWeight = FontWeight.Bold,
                                                letterSpacing = 1.5.sp
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = "Standard Atelier accounts are capped at 2 units per bag reservation. To add 3 or more items, upgrade to VIP Atelier Pass for ₹50 / month.",
                                            style = MaterialTheme.typography.bodyMedium.copy(
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                            )
                                        )
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(48.dp)
                                                .background(
                                                    color = MaterialTheme.colorScheme.primary,
                                                    shape = RoundedCornerShape(16.dp)
                                                )
                                                .clickable {
                                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                                    viewModel.upgradeToPremiumMembership()
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "GET VIP ATELIER PASS • ₹50/MO →",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                    fontWeight = FontWeight.Bold,
                                                    letterSpacing = 1.2.sp,
                                                    fontSize = 11.sp
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(38.dp)
                                                .clickable { viewModel.dismissUpgradePrompt() },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "CANCEL",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                                    fontWeight = FontWeight.Bold,
                                                    letterSpacing = 1.2.sp,
                                                    fontSize = 10.sp
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LuxuryBottomNavigationBar(
    currentTab: NavigationTab,
    cartCount: Int,
    onTabSelected: (NavigationTab) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(24.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationTab.values().forEach { tab ->
                val isSelected = currentTab == tab

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.onBackground else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .testTag("tab_${tab.name.lowercase()}")
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isSelected) tab.activeIcon else tab.inactiveIcon,
                            contentDescription = tab.label,
                            modifier = Modifier.size(20.dp),
                            tint = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )

                        if (tab == NavigationTab.PROFILE && cartCount > 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(8.dp)
                                    .background(com.example.ui.theme.AccentPurple, CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            letterSpacing = 0.5.sp,
                            color = if (isSelected) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        }
    }
}
