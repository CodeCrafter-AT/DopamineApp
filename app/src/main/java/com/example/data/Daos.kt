package com.example.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(cartItem: CartItemEntity)

    @Delete
    suspend fun delete(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("UPDATE orders SET currentStep = :newStep WHERE orderId = :orderId")
    suspend fun updateOrderStatus(orderId: String, newStep: String)
}

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE userId = :userId LIMIT 1")
    fun getUserProfile(userId: String = "default_user"): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfileEntity)

    @Query("UPDATE user_profile SET dopamineGems = dopamineGems + :gemsToAdd, totalSimulatedSavingsINR = totalSimulatedSavingsINR + :savingsToAdd WHERE userId = :userId")
    suspend fun addGemsAndSavings(userId: String = "default_user", gemsToAdd: Int, savingsToAdd: Long)

    @Query("UPDATE user_profile SET winStreak = winStreak + 1 WHERE userId = :userId")
    suspend fun incrementStreak(userId: String = "default_user")

    @Query("UPDATE user_profile SET winStreak = 1 WHERE userId = :userId")
    suspend fun resetStreak(userId: String = "default_user")
}
