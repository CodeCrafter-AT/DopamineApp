package com.example.engine

import com.example.data.OrderDao
import com.example.data.OrderEntity
import com.example.data.UserProfileDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object OrderEngine {

    val ORDER_STEPS = listOf("Order Placed", "Processing", "Shipped", "Delivered")

    private val engineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun processOrder(
        order: OrderEntity,
        orderDao: OrderDao,
        userProfileDao: UserProfileDao
    ) {
        if (order.sapolskyStatus != "SUCCESS") return

        engineScope.launch {
            // Start at the first step
            orderDao.updateOrderStatus(order.orderId, ORDER_STEPS[0])

            // Advance through steps
            for (i in 1 until ORDER_STEPS.size) {
                delay(5000) // Delay 5 seconds between steps for simulation
                val nextStep = ORDER_STEPS[i]
                orderDao.updateOrderStatus(order.orderId, nextStep)

                // If order is delivered, allocate rewards
                if (nextStep == "Delivered") {
                    userProfileDao.addGemsAndSavings(
                        gemsToAdd = order.pointsAwarded,
                        savingsToAdd = order.totalAmountINR
                    )
                    userProfileDao.incrementStreak()
                }
            }
        }
    }
}
