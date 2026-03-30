package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


// ─────────────────────────────────────────────
//  TriviaSparks ARENA — Custom Tokens
//
//  Extends Material3 with tokens that have no M3 equivalent:
//    • Full surface-container tier system
//    • Warning color role
//    • Difficulty chip colors
//    • Gradient & glassmorphism helpers
//    • Ambient glow parameters
// ─────────────────────────────────────────────

// ── Extended color roles ──────────────────────────────────────────────

@Immutable
data class TriviaSparksColors(

    // Surface tier system (base → highest)
    val surfaceLowest:  Color,   // deepest sunken layer
    val surfaceLow:     Color,   // secondary background elements
    val surfaceDefault: Color,   // surface alias (mirrors M3 surface)
    val surfaceHigh:    Color,   // container / elevated sections
    val surfaceHighest: Color,   // active tiles, primary question cards

    // Warning role (no M3 equivalent)
    val warning:        Color,
    val onWarning:      Color,
    val warningContainer: Color,
    val onWarningContainer: Color,

    // Ghost border (accessibility fallback — 15 % opacity outline)
    val ghostBorder:    Color,

    // Coral Peach accent
    val accentCoral:    Color,
)

val DarkTriviaSparksColors = TriviaSparksColors(
    surfaceLowest         = Surface1,
    surfaceLow            = Surface2,
    surfaceDefault        = Surface0,
    surfaceHigh           = Surface3,
    surfaceHighest        = Surface4,

    warning               = SunnyYellow,
    onWarning             = SunnyYellowOn,
    warningContainer      = Color(0xFF4A3800),
    onWarningContainer    = SunnyYellow,

    ghostBorder           = OutlineColor.copy(alpha = 0.15f),
    accentCoral           = CoralPeach,
)

val LightTriviaSparksColors = TriviaSparksColors(
    surfaceLowest         = LightSurface1,
    surfaceLow            = LightSurface2,
    surfaceDefault        = LightSurface,
    surfaceHigh           = LightSurface4,
    surfaceHighest        = LightSurface5,

    warning               = Color(0xFF7A5900),   // darker yellow for light bg legibility
    onWarning             = Color(0xFFFFFFFF),
    warningContainer      = SunnyYellow,
    onWarningContainer    = Color(0xFF251A00),

    ghostBorder           = OutlineLight.copy(alpha = 0.15f),
    accentCoral           = Color(0xFFBF5A3A),   // deeper coral for light mode contrast
)

// ── Difficulty chip colors ────────────────────────────────────────────

@Immutable
data class DifficultyColors(
    val easyBackground:   Color,   // Mint tertiary-container  #8EEFCF
    val mediumBackground: Color,   // Sunny Yellow  #FFD166
    val hardBackground:   Color,   // Strawberry error-container  #F76A80
    val easyContent:      Color,
    val mediumContent:    Color,
    val hardContent:      Color,
)

val TriviaSparksDifficulty = DifficultyColors(
    easyBackground   = Mint300,
    mediumBackground = SunnyYellow,
    hardBackground   = Strawberry400,
    easyContent      = MintDeep,
    mediumContent    = SunnyYellowOn,
    hardContent      = Color(0xFF3D0010),
)

// ── Gradient brushes ─────────────────────────────────────────────────

/**
 * Signature CTA gradient: primary (#8B7FE8) → primaryContainer (#C1B9FF).
 * Apply to all primary buttons and main action surfaces.
 */
val GradientPrimaryCTA: Brush
    get() = Brush.linearGradient(
        colors = listOf(Violet400, Violet200),
        start  = Offset(0f, 0f),
        end    = Offset(Float.POSITIVE_INFINITY, 0f),
    )

/**
 * Subtle background gradient for hero sections and Home screen canvas.
 * Flows top-to-bottom from Midnight Grape into a deeper purple.
 */
val GradientBackground: Brush
    get() = Brush.verticalGradient(
        colors = listOf(
            MidnightGrape,
            Color(0xFF0F0D18),
        ),
    )

/**
 * Victory / score screen radial glow emanating from the center.
 */
val GradientVictoryGlow: Brush
    get() = Brush.radialGradient(
        colors = listOf(
            Violet400.copy(alpha = 0.35f),
            Color.Transparent,
        ),
    )

// ── Glassmorphism helpers ─────────────────────────────────────────────

/**
 * Standard glass surface: semi-transparent primary tint.
 * Pair with Modifier.blur() or a RenderEffect backdrop blur of 12–20dp.
 */
val GlassSurface: Color get() = Violet400.copy(alpha = 0.12f)
val GlassSurfaceStrong: Color get() = Violet400.copy(alpha = 0.22f)

// ── Ambient glow parameters ───────────────────────────────────────────

/**
 * "Soft Violet Glow" used on floating / active elements.
 *
 * Usage with shadow modifier:
 *   Modifier.shadow(
 *       elevation      = AmbientGlow.spread,
 *       shape          = ShapeCard,
 *       ambientColor   = AmbientGlow.color,
 *       spotColor      = AmbientGlow.color,
 *   )
 *
 * For a Compose-native glow, wrap in a Canvas drawBehind with
 * a blurred circle at [blur] radius, [color], [spread] offset.
 */
object AmbientGlow {
    val color:  Color = OnPrimaryContainerGlow   // see below
    val blur:   Dp    = 40.dp                    // 32dp–48dp range per spec
    val spread: Dp    = (-4).dp
    val alpha:  Float = 0.08f
}

private val OnPrimaryContainerGlow = Violet200.copy(alpha = 0.08f)

// ── Spacing tokens (named) ────────────────────────────────────────────

object TriviaSparksSpacing {
    val cardPadding    : Dp = 24.dp   // minimum internal card padding
    val optionGap      : Dp = 12.dp   // vertical gap between answer options (no dividers)
    val heroSection    : Dp = 68.dp   // breathing room for hero / score sections (≈8.5rem)
    val chipPadding    : Dp = 4.dp    // internal chip margin
}

// ── CompositionLocal providers ────────────────────────────────────────

val LocalTriviaSparksColors = staticCompositionLocalOf { DarkTriviaSparksColors }
val LocalTriviaSparksDifficulty = staticCompositionLocalOf { TriviaSparksDifficulty }

val LocalTriviaSparksDimens = staticCompositionLocalOf { TriviaSparksDimens() }
