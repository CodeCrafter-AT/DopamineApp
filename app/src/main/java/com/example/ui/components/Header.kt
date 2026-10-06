package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LuxuryBorder
import com.example.ui.theme.NoirBlack
import com.example.ui.theme.PureWhite

@Composable
fun LuxuryHeader(
    gemsCount: Int,
    userName: String,
    vipTier: String,
    cartCount: Int,
    onOpenAuth: () -> Unit,
    onOpenBag: () -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .background(com.example.ui.theme.BgDarkTicker),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "DROPZERO IS BETTER ON THE APP | EXTRA 10% OFF | CODE: APP10",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = com.example.ui.theme.TextInverse,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.08.sp
                ),
                maxLines = 1
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .background(com.example.ui.theme.BgHeaderSticky)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hamburger
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Open menu",
                tint = com.example.ui.theme.TextPrimary,
                modifier = Modifier.size(20.dp).clickable { /* TODO: Open drawer */ }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Center Wordmark
            Text(
                text = "DROPZERO",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 20.sp,
                    color = com.example.ui.theme.TextPrimary
                ),
                modifier = Modifier.testTag("header_logo")
            )

            Spacer(modifier = Modifier.weight(1f))

            // Right Utilities: Search, Wishlist, Bag
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = com.example.ui.theme.TextPrimary,
                    modifier = Modifier.size(20.dp).clickable {  }
                )
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = "Wishlist",
                    tint = com.example.ui.theme.TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Box(
                    modifier = Modifier
                        .clickable { onOpenBag() }
                        .testTag("header_cart_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = "Bag",
                        tint = com.example.ui.theme.TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    if (cartCount > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 6.dp, y = (-4).dp)
                                .size(16.dp)
                                .background(com.example.ui.theme.TextPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cartCount.toString(),
                                color = com.example.ui.theme.BgPrimary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(com.example.ui.theme.BorderHairline))
    }
}
@Composable
private fun TextStyleDefault(color: Color, fontSize: androidx.compose.ui.unit.TextUnit, fontWeight: FontWeight): androidx.compose.ui.text.TextStyle {
    return androidx.compose.ui.text.TextStyle(
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight
    )
}
