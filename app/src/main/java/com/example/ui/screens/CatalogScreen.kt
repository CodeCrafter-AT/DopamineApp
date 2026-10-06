package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LuxuryCatalog
import com.example.data.LuxuryProduct
import com.example.ui.components.LuxuryProductCard
import com.example.ui.components.MysteryBoxModal
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleBg
import com.example.ui.theme.NoirBlack
import com.example.ui.theme.PureWhite

data class UpcomingDropItem(
    val title: String,
    val brand: String,
    val category: String,
    val price: String,
    val date: String
)

@Composable
fun CatalogScreen(
    products: List<LuxuryProduct>,
    selectedCategory: String,
    searchQuery: String,
    favoriteIds: Set<String>,
    onSelectCategory: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSelectProduct: (LuxuryProduct) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onQuickAdd: (LuxuryProduct) -> Unit,
    onOpenTracking: () -> Unit = {}
) {
    val haptic = LocalHapticFeedback.current
    var showMysteryBox by remember { mutableStateOf(false) }

    if (showMysteryBox) {
        MysteryBoxModal(
            onDismiss = { showMysteryBox = false },
            onClaimReward = { gems ->
                // Dismiss modal
                showMysteryBox = false
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("catalog_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // 1. Editorial Greeting Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Good Evening,",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = "Arpit",
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Black,
                        fontSize = 38.sp,
                        letterSpacing = (-1).sp
                    )
                )
            }
        }

        // 2. Large Hero Reward Card (Mystery Box)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .border(
                        width = 1.5.dp,
                        color = AccentPurple,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .clickable {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showMysteryBox = true
                    }
                    .padding(20.dp)
                    .testTag("hero_mystery_box_card")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(AccentPurpleBg, CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Sparkle",
                                modifier = Modifier.size(12.dp),
                                tint = AccentPurple
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "LUCKY REWARD",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = AccentPurple,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 9.sp,
                                    letterSpacing = 1.2.sp
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(NoirBlack, CircleShape)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "OPEN →",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PureWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                letterSpacing = 1.2.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Exclusive VIP Mystery Perk",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Tap to unbox your tailored luxury perk • 15% VIP Atelier discount, express courier shipping & priority drop access.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }
        }

        // 3. Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Search Luxury Atelier...", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurface) },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                )
            )
        }

        // 4. Category Filter Chips
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(LuxuryCatalog.categories) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (isSelected) NoirBlack else MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if (isSelected) NoirBlack else MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onSelectCategory(category)
                            }
                            .padding(horizontal = 18.dp, vertical = 8.dp)
                            .testTag("category_chip_$category")
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isSelected) PureWhite else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }

        // 5. Trending Section Header & Carousel
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Trending Drops",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )

                    Text(
                        text = "EXPLORE ALL",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            letterSpacing = 1.2.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(products.take(4), key = { "trending_${it.id}" }) { product ->
                        Box(modifier = Modifier.width(180.dp)) {
                            LuxuryProductCard(
                                product = product,
                                isFavorite = favoriteIds.contains(product.id),
                                onSelect = { onSelectProduct(product) },
                                onToggleFavorite = { onToggleFavorite(product.id) },
                                onQuickAdd = { onQuickAdd(product) }
                            )
                        }
                    }
                }
            }
        }

        // 5b. Auto-Rotating Upcoming Drops Section (Changes every 3s)
        item {
            val upcomingList = remember {
                listOf(
                    UpcomingDropItem("Triple S Clear Sole Sneakers", "Balenciaga", "Shoes", "₹1,25,000", "DROPS AUG 18"),
                    UpcomingDropItem("Maya Short Down Puffer Jacket", "Moncler", "Jackets", "₹1,95,000", "DROPS AUG 22"),
                    UpcomingDropItem("Re-Nylon Cargo Shorts", "Prada", "Shorts", "₹85,000", "DROPS AUG 28"),
                    UpcomingDropItem("Baccarat Rouge 540 Extrait", "Kurkdjian", "Perfumes", "₹85,000", "DROPS SEP 02"),
                    UpcomingDropItem("Nautilus 5711/1A Blue Dial", "Patek Philippe", "Watches", "₹98,00000", "DROPS SEP 10")
                )
            }

            var currentDropIndex by remember { mutableStateOf(0) }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(3000L)
                    currentDropIndex = (currentDropIndex + 1) % upcomingList.size
                }
            }

            val activeDrop = upcomingList[currentDropIndex]

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(AccentPurple, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Upcoming Drops & Pre-Booking",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(AccentPurpleBg, RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "AUTO-ROTATING (3s)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 8.sp,
                                color = AccentPurple,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Auto-Rotating Active Card
                Crossfade(
                    targetState = activeDrop,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) { drop ->
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
                            .padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(NoirBlack, CircleShape)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = drop.date,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = PureWhite,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.2.sp
                                    )
                                )
                            }

                            Text(
                                text = drop.category.uppercase(),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = AccentPurple,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp,
                                    letterSpacing = 1.2.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = drop.brand.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.sp,
                                letterSpacing = 2.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = drop.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = drop.price,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp)
                                .background(
                                    color = NoirBlack,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    val mockProd = products.firstOrNull { it.title.contains(drop.category, ignoreCase = true) }
                                        ?: products.firstOrNull()
                                    if (mockProd != null) {
                                        onQuickAdd(mockProd)
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "PRE-BOOK UPCOMING DROP →",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = PureWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.5.sp,
                                    letterSpacing = 1.5.sp
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Rotation Indicator Dots
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    upcomingList.indices.forEach { index ->
                        val isSelected = index == currentDropIndex
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (isSelected) 8.dp else 6.dp)
                                .background(
                                    color = if (isSelected) NoirBlack else MaterialTheme.colorScheme.outline,
                                    shape = CircleShape
                                )
                                .clickable { currentDropIndex = index }
                        )
                    }
                }
            }
        }

        // 6. New Drops Grid Section Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "New Drops",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                Text(
                    text = "${products.size} ITEMS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        letterSpacing = 1.sp
                    )
                )
            }
        }

        // 7. Products List Items (2-column layout in rows)
        val productPairs = products.chunked(2)
        items(productPairs.size) { index ->
            val pair = productPairs[index]
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    LuxuryProductCard(
                        product = pair[0],
                        isFavorite = favoriteIds.contains(pair[0].id),
                        onSelect = { onSelectProduct(pair[0]) },
                        onToggleFavorite = { onToggleFavorite(pair[0].id) },
                        onQuickAdd = { onQuickAdd(pair[0]) }
                    )
                }
                if (pair.size > 1) {
                    Box(modifier = Modifier.weight(1f)) {
                        LuxuryProductCard(
                            product = pair[1],
                            isFavorite = favoriteIds.contains(pair[1].id),
                            onSelect = { onSelectProduct(pair[1]) },
                            onToggleFavorite = { onToggleFavorite(pair[1].id) },
                            onQuickAdd = { onQuickAdd(pair[1]) }
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // 8. "Continue Tracking" Live Courier Card
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 24.dp, end = 20.dp, bottom = 0.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onOpenTracking() }
                    .padding(18.dp)
                    .testTag("continue_tracking_home_card")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(NoirBlack, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalShipping,
                                contentDescription = "Courier",
                                tint = PureWhite,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Continue Tracking",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                            Text(
                                text = "Live Courier #AURA-9821 • On The Way",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }

                    Text(
                        text = "VIEW →",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            letterSpacing = 1.2.sp
                        )
                    )
                }
            }
        }
    }
}

