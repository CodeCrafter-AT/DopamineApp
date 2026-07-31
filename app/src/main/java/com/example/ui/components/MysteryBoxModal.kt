package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.AccentPurple
import com.example.ui.theme.AccentPurpleBg
import com.example.ui.theme.AccentPurpleGlow
import com.example.ui.theme.NoirBlack
import com.example.ui.theme.PureWhite
import kotlinx.coroutines.delay

@Composable
fun MysteryBoxModal(
    onDismiss: () -> Unit,
    onClaimReward: (gems: Int) -> Unit
) {
    val haptic = LocalHapticFeedback.current
    var countdownStep by remember { mutableStateOf(3) }
    var isRevealed by remember { mutableStateOf(false) }

    // Pulsing box glow effect
    val infiniteTransition = rememberInfiniteTransition(label = "MysteryGlow")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowScale"
    )

    LaunchedEffect(Unit) {
        while (countdownStep > 1) {
            delay(800)
            countdownStep--
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
        delay(800)
        isRevealed = true
        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(32.dp)
                )
                .border(
                    width = 2.dp,
                    color = AccentPurple,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(24.dp)
                .testTag("mystery_box_modal"),
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Top Close Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(AccentPurpleBg, CircleShape)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "Reward",
                                    tint = AccentPurple,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "VIP MYSTERY PERK",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = AccentPurple,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 9.sp,
                                        letterSpacing = 1.2.sp
                                    )
                                )
                            }
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (!isRevealed) {
                    // Unboxing Suspense State
                    Box(
                        modifier = Modifier
                            .scale(pulseScale)
                            .size(100.dp)
                            .background(
                                color = AccentPurpleBg,
                                shape = CircleShape
                            )
                            .border(
                                width = 2.dp,
                                color = AccentPurpleGlow,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CardGiftcard,
                            contentDescription = "Mystery Box",
                            modifier = Modifier.size(48.dp),
                            tint = AccentPurple
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "UNBOXING VIP PERK",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp,
                            fontSize = 13.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "$countdownStep",
                        style = MaterialTheme.typography.displayLarge.copy(
                            color = AccentPurple,
                            fontWeight = FontWeight.Black,
                            fontSize = 48.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Curating your tailored fashion privileges...",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                    )
                } else {
                    // Revealed State
                    AnimatedVisibility(
                        visible = isRevealed,
                        enter = fadeIn() + scaleIn()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .background(AccentPurple, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WorkspacePremium,
                                    contentDescription = "Unlocked",
                                    modifier = Modifier.size(46.dp),
                                    tint = PureWhite
                                )
                            }

                            Spacer(modifier = Modifier.height(18.dp))

                            Text(
                                text = "EXCLUSIVE VIP PERK REVEALED",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = AccentPurple,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 2.sp,
                                    fontSize = 10.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "15% VIP ATELIER DISCOUNT",
                                style = MaterialTheme.typography.displayMedium.copy(
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Congratulations! You unlocked 15% OFF your next checkout + Complimentary Express Courier Delivery & Priority Drop Access.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Box(
                                modifier = Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "CODE: VIP15ATELIER",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.5.sp,
                                        fontSize = 11.sp,
                                        color = AccentPurple
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            // Claim CTA Button
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .background(
                                        color = NoirBlack,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        onClaimReward(1500)
                                        onDismiss()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "CLAIM PERK & EXPLORE DROPS →",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        color = PureWhite,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.2.sp,
                                        fontSize = 12.sp
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
