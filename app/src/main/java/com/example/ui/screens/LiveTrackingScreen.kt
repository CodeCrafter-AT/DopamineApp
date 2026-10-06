package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.OrderEntity
import com.example.ui.components.formatINR
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleBg
import com.example.ui.theme.NoirBlack
import com.example.ui.theme.PureWhite
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LiveTrackingScreen(
    orders: List<OrderEntity>
) {
    val activeOrder = orders.firstOrNull { it.sapolskyStatus == "SUCCESS" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp)
            .testTag("live_tracking_screen"),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(top = 16.dp, bottom = 90.dp)
    ) {
        // Title Header
        item {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                Text(
                    text = "Live Courier Tracking",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Black,
                        fontSize = 28.sp,
                        letterSpacing = (-0.5).sp
                    )
                )
                Text(
                    text = "Real-time express dispatch & order history",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                )
            }
        }

        // Active Order Card with Map & Route
        item {
            if (activeOrder != null) {
                Column(
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
                        .padding(20.dp)
                ) {
                    // Modern Route Canvas Header
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .border(
                                width = 0.5.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CourierRouteMapCanvas()

                        // Route courier icon overlay
                        Row(
                            modifier = Modifier
                                .background(NoirBlack, CircleShape)
                                .padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalShipping,
                                contentDescription = "Rider",
                                modifier = Modifier.size(16.dp),
                                tint = PureWhite
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "EXPRESS COURIER #9821",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = PureWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "ORDER ID: ${activeOrder.orderId}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            letterSpacing = 0.5.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Destination: ${activeOrder.deliveryAddress}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Status Timeline: Packed -> Warehouse -> On The Way -> Nearby -> Delivered
                    Text(
                        text = "DELIVERY MILESTONES",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            letterSpacing = 1.5.sp,
                            color = AccentPurple
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    TrackingTimelineView(currentStep = activeOrder.currentStep)
                }
            } else {
                // Standby Card
                Column(
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
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(AccentPurpleBg, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalShipping,
                            contentDescription = "Standby",
                            modifier = Modifier.size(28.dp),
                            tint = AccentPurple
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "No Active Dispatches",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Claim luxury items in Bag to dispatch a live courier.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }

        // Order History Logs Title
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "RECENT ORDERS",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    letterSpacing = 1.5.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (orders.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No previous order history",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        } else {
            items(orders, key = { it.orderId }) { order ->
                val isSuccess = order.sapolskyStatus == "SUCCESS"
                val formattedDate = SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()).format(Date(order.timestamp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    color = if (isSuccess) NoirBlack else MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                )
                                .border(width = 1.dp, color = MaterialTheme.colorScheme.outline, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isSuccess) Icons.Default.Check else Icons.Default.Close,
                                contentDescription = "Status",
                                modifier = Modifier.size(16.dp),
                                tint = if (isSuccess) PureWhite else MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = if (isSuccess) "Order Confirmed" else "Out Of Stock",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = "${order.orderId} • $formattedDate",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = formatINR(order.totalAmountINR),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                fontSize = 14.sp
                            )
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Gems",
                                modifier = Modifier.size(10.dp),
                                tint = AccentPurple
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "+${order.pointsAwarded} GEMS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp,
                                    color = AccentPurple
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackingTimelineView(currentStep: String) {
    val steps = listOf(
        "Packed" to Icons.Default.Inventory2,
        "Warehouse" to Icons.Default.Warehouse,
        "On The Way" to Icons.Default.LocalShipping,
        "Nearby" to Icons.Default.NearMe,
        "Delivered" to Icons.Default.LocationOn
    )

    // Index calculation
    val currentStepIndex = when (currentStep.lowercase()) {
        "packed" -> 0
        "warehouse" -> 1
        "on the way" -> 2
        "nearby" -> 3
        "delivered" -> 4
        else -> 2
    }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        steps.forEachIndexed { index, (stepName, icon) ->
            val isCompleted = index <= currentStepIndex
            val isCurrent = index == currentStepIndex

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = if (isCompleted) NoirBlack else MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        )
                        .border(
                            width = if (isCurrent) 2.dp else 1.dp,
                            color = if (isCurrent) AccentPurple else MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stepName,
                        modifier = Modifier.size(14.dp),
                        tint = if (isCompleted) PureWhite else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = stepName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            color = if (isCompleted) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    )
                    if (isCurrent) {
                        Text(
                            text = "Current status in progress",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = AccentPurple,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CourierRouteMapCanvas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val p1 = Offset(width * 0.15f, height * 0.7f)
        val p2 = Offset(width * 0.45f, height * 0.3f)
        val p3 = Offset(width * 0.85f, height * 0.6f)

        // Draw dotted path
        val stroke = Stroke(
            width = 4f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )

        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(p1.x, p1.y)
            lineTo(p2.x, p2.y)
            lineTo(p3.x, p3.y)
        }

        drawPath(path = path, color = com.example.ui.theme.TextMuted, style = stroke)

        // Draw node circles
        drawCircle(color = com.example.ui.theme.NoirBlack, radius = 8f, center = p1)
        drawCircle(color = com.example.ui.theme.AccentPurple, radius = 12f, center = p2)
        drawCircle(color = com.example.ui.theme.NoirBlack, radius = 8f, center = p3)
    }
}

