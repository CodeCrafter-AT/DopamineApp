package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LuxuryProduct
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.PureWhite
import java.text.NumberFormat
import java.util.Locale

@Composable
fun LuxuryProductCard(
    product: LuxuryProduct,
    isFavorite: Boolean,
    onSelect: () -> Unit,
    onToggleFavorite: () -> Unit,
    onQuickAdd: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val formattedPrice = formatINR(product.priceInINR)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onSelect()
            }
            .padding(14.dp)
            .testTag("product_card_${product.id}")
    ) {
        // Image Canvas Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            // High fashion vector product backdrop canvas
            ProductVectorCanvas(category = product.category)

            // VIP Drop Tag (Electric Purple Accent)
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .background(
                        color = AccentPurple,
                        shape = CircleShape
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "VIP DROP",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = PureWhite,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                )
            }

            // Favorite Button
            IconButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onToggleFavorite()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(32.dp)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f), CircleShape)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(16.dp),
                    tint = if (isFavorite) AccentPurple else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Brand Label
        Text(
            text = product.brand.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Title
        Text(
            text = product.title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                letterSpacing = 0.2.sp
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Price Row (Actual Price + Limited Edition Tag)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = formattedPrice,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "LIMITED EDITION",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 7.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Action CTA Button: Add To Bag
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(14.dp)
                )
                .clickable {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onQuickAdd()
                }
                .testTag("quick_add_${product.id}"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "ADD TO CART →",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    fontSize = 10.sp
                )
            )
        }
    }
}

@Composable
fun ProductVectorCanvas(category: String) {
    val isDark = MaterialTheme.colorScheme.background.red < 0.2f
    val lineColor = if (isDark) Color.White.copy(alpha = 0.35f) else Color.Black.copy(alpha = 0.35f)

    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width
        val h = size.height

        // Subtle diagonal background styling lines
        drawLine(
            color = lineColor.copy(alpha = 0.1f),
            start = Offset(0f, 0f),
            end = Offset(w, h),
            strokeWidth = 1f
        )

        when (category) {
            "Luxury Bags" -> {
                // Draw luxury handbag silhouette
                val path = Path().apply {
                    // Bag handles
                    moveTo(w * 0.35f, h * 0.35f)
                    cubicTo(w * 0.35f, h * 0.15f, w * 0.65f, h * 0.15f, w * 0.65f, h * 0.35f)
                    // Bag body
                    moveTo(w * 0.25f, h * 0.35f)
                    lineTo(w * 0.75f, h * 0.35f)
                    lineTo(w * 0.82f, h * 0.8f)
                    lineTo(w * 0.18f, h * 0.8f)
                    close()
                }
                drawPath(path, color = lineColor, style = Stroke(width = 3f))
                // Clasp details
                drawRect(
                    color = lineColor,
                    topLeft = Offset(w * 0.46f, h * 0.45f),
                    size = Size(w * 0.08f, h * 0.08f),
                    style = Stroke(width = 2f)
                )
            }
            "Men's Couture", "Women's Haute" -> {
                // Tuxedo jacket silhouette
                val path = Path().apply {
                    moveTo(w * 0.3f, h * 0.25f)
                    lineTo(w * 0.5f, h * 0.5f)
                    lineTo(w * 0.7f, h * 0.25f)
                    lineTo(w * 0.8f, h * 0.8f)
                    lineTo(w * 0.2f, h * 0.8f)
                    close()
                }
                drawPath(path, color = lineColor, style = Stroke(width = 3f))
            }
            "Premium Perfumes" -> {
                // Perfume bottle
                drawRect(
                    color = lineColor,
                    topLeft = Offset(w * 0.3f, h * 0.35f),
                    size = Size(w * 0.4f, h * 0.45f),
                    style = Stroke(width = 3f)
                )
                // Cap
                drawRect(
                    color = lineColor,
                    topLeft = Offset(w * 0.4f, h * 0.2f),
                    size = Size(w * 0.2f, h * 0.15f),
                    style = Stroke(width = 3f)
                )
            }
            else -> {
                // High-fashion luxury diamond crown monogram silhouette
                val path = Path().apply {
                    moveTo(w * 0.5f, h * 0.2f)
                    lineTo(w * 0.8f, h * 0.5f)
                    lineTo(w * 0.5f, h * 0.8f)
                    lineTo(w * 0.2f, h * 0.5f)
                    close()
                }
                drawPath(path, color = lineColor, style = Stroke(width = 3f))
            }
        }
    }
}

fun formatINR(amount: Long): String {
    val formatter = NumberFormat.getCurrencyInstance(java.util.Locale.Builder().setLanguage("en").setRegion("IN").build())
    formatter.maximumFractionDigits = 0
    return formatter.format(amount)
}
