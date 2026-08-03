package com.example.engine

/**
 * DPDP Privacy Middleware
 * Implement a backend data-masking interceptor that permanently strips PII (names, exact IPs)
 * and coarsens location data to Tier-2/3 district codes (e.g., UP-Sitapur) before writing
 * intent vectors to the database.
 */
object DPDPPrivacyMiddleware {
    fun maskPII(name: String): String {
        return name.firstOrNull()?.let { "$it***" } ?: "***"
    }

    fun coarsenLocation(address: String): String {
        val lower = address.lowercase()
        return when {
            lower.contains("mumbai") || lower.contains("bandra") -> "MH-Mumbai"
            lower.contains("sitapur") || lower.contains("up") -> "UP-Sitapur"
            lower.contains("delhi") -> "DL-Delhi"
            lower.contains("bangalore") -> "KA-Bangalore"
            else -> "XX-District"
        }
    }
}
