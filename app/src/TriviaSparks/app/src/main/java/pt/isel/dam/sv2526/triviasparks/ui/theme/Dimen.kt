package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class TriviaSparksDimens(
    // Spacing
    val spacingXXS: Dp = 4.dp,
    val spacingXS: Dp = 8.dp,
    val spacingSM: Dp = 12.dp,
    val spacingMD: Dp = 16.dp,
    val spacingLG: Dp = 24.dp,
    val spacingXL: Dp = 32.dp,
    val spacingXXL: Dp = 40.dp,
    val spacing3XL: Dp = 64.dp,

    // Radii
    val radiusSM: Dp = 8.dp,
    val radiusMD: Dp = 16.dp,
    val radiusLG: Dp = 24.dp,
    val radiusXL: Dp = 32.dp,
    val radiusFull: Dp = 50.dp,

    // Component Sizes
    val avatarSM: Dp = 32.dp,
    val avatarMD: Dp = 48.dp,
    val avatarLG: Dp = 64.dp,

    val iconSM: Dp = 24.dp,
    val iconMD: Dp = 32.dp,
    val iconLG: Dp = 48.dp,

    val fabSize: Dp = 56.dp,
    val chipHeight: Dp = 24.dp,
    val chipPadding: Dp = 4.dp,

    val buttonHeightLG: Dp = 48.dp,
    val buttonHeightMD: Dp = 40.dp,

    val inputFieldHeight: Dp = 56.dp,

    val heroImageWidth: Dp = 200.dp,
    val heroImageHeight: Dp = 160.dp,

    // Typography sizes (sp)
    val displayLG: Float = 56f,
    val headlineLG: Float = 32f,
    val bodyLG: Float = 16f,
    val labelMD: Float = 12f,


    // ── Padding Tokens (commonly used inside cards, buttons, inputs)
    val cardPadding: Dp = 24.dp,
    val buttonPaddingH: Dp = 16.dp,
    val buttonPaddingV: Dp = 12.dp,
    val inputPaddingH: Dp = 16.dp,
    val inputPaddingV: Dp = 12.dp,
    val chipMarginH: Dp = 8.dp,
    val chipMarginV: Dp = 4.dp,

    // ── Border Widths
    val borderThin: Dp = 1.dp,   // subtle ghost borders (if needed)
    val borderThick: Dp = 2.dp,   // rarely used, e.g., highlighted states

    // ── Offsets
    val smallOffset: Dp = 4.dp,   // e.g., minor nudges
    val mediumOffset: Dp = 8.dp,  // e.g., floating chip or badge
    val largeOffset: Dp = 16.dp   // e.g., hero element, modal, or FAB lift
)