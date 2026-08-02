package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: String,
    val title: String,
    val brand: String,
    val category: String,
    val priceInINR: Long,
    val selectedSize: String,
    val quantity: Int = 1
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val orderId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val totalAmountINR: Long,
    val itemCount: Int,
    val sapolskyStatus: String, // "SUCCESS" or "OUT_OF_STOCK"
    val deliveryAddress: String,
    val pointsAwarded: Int,
    val itemsSummary: String,
    val currentStep: String = "Order Dispatched from Delhi Hub"
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val userId: String = "default_user",
    val name: String = "Arpit Tripathi",
    val email: String = "arpittripathi020@gmail.com",
    val vipTier: String = "Black Card Atelier",
    val dopamineGems: Int = 4850,
    val winStreak: Int = 3,
    val totalSimulatedSavingsINR: Long = 12450000L,
    val addressStreet: String = "Flat 402, Sea Crest Towers, Bandra West",
    val addressCity: String = "Mumbai, Maharashtra",
    val addressPincode: String = "400050",
    val upiId: String = "arpit@cred"
)
