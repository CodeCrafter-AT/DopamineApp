package com.example.engine

import kotlin.random.Random

sealed class SapolskyOutcome {
    data class Success(
        val orderId: String,
        val totalAmountINR: Long,
        val gemsEarned: Int,
        val estimatedDeliveryMinutes: Int = 15,
        val headline: String = "ACQUISITION CONFIRMED",
        val message: String = "Your order has been allocated from our Private Atelier. Live 15-minute express delivery courier has been dispatched."
    ) : SapolskyOutcome()

    data class Rejected(
        val orderId: String,
        val totalAmountINR: Long,
        val gemsEarned: Int,
        val headline: String = "EXCLUSIVE ITEM OUT OF STOCK",
        val message: String = "EXCLUSIVE ITEM: This limited edition product is currently out of stock. Please try again later.",
        val rewardPredictionErrorTriggered: Boolean = true
    ) : SapolskyOutcome()
}

data class SapolskyConfig(
    val successProbability: Float = 0.5f
)

object SapolskyEngine {

    var config = SapolskyConfig()

    /**
     * Evaluates a checkout attempt using Robert Sapolsky's variable reward probability.
     * Generates maximum dopamine by balancing anticipatory excitement and Reward Prediction Error (RPE).
     */
    fun evaluateCheckout(
        totalAmountINR: Long,
        itemCount: Int,
        forceOutcome: Boolean? = null // For testing or user override if desired
    ): SapolskyOutcome {
        val randomRoll = Random.nextFloat() // 0.0 to 1.0
        val isSuccess = forceOutcome ?: (randomRoll <= config.successProbability)

        val orderId = "NOIR-" + (100000..999999).random().toString()
        val baseGems = 250 + (totalAmountINR / 25000).toInt().coerceAtMost(2500)

        return if (isSuccess) {
            SapolskyOutcome.Success(
                orderId = orderId,
                totalAmountINR = totalAmountINR,
                gemsEarned = baseGems + 100 // Bonus for success
            )
        } else {
            SapolskyOutcome.Rejected(
                orderId = orderId,
                totalAmountINR = totalAmountINR,
                gemsEarned = baseGems // Consolation gems awarded to trigger RPE re-engagement
            )
        }
    }
}
