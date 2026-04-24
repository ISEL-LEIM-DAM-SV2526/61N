package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Trivia Sparks — TriviaSparksTheme entry point.
 *
 * This is the main theme configuration file. It wires together all four theme
 * pillars — colour, typography, shapes, and extended tokens — and exposes them
 * to the entire composable tree through [MaterialTheme].
 *
 * **Wrap the entire app with [TriviaSparksTheme] in `MainActivity`:**
 * ```kotlin
 * setContent {
 *     TriviaSparksTheme {
 *         AppNavGraph(navController = navController)
 *     }
 * }
 * ```
 *
 * **Accessing theme values in composables:**
 * ```kotlin
 * // Standard Material3 roles — colour, typography, shapes
 * MaterialTheme.colorScheme.primary
 * MaterialTheme.typography.headlineMedium
 * MaterialTheme.shapes.extraLarge
 *
 * // Extended Tokens — difficulty colours, category colours
 * MaterialTheme.triviasparks.easy
 * MaterialTheme.triviasparks.categoryScience
 * MaterialTheme.triviasparks.scoreNumber
 * ```
 *
 * **How the full theme system fits together:**
 * ```
 * Color.kt    → raw colour tokens
 * Type.kt     → typography scale
 * Shape.kt    → corner radius tokens
 *      ↓
 * Theme.kt    → maps tokens to M3 roles → MaterialTheme
 *               + TriviaSparksThemeExtendedColors → MaterialTheme.triviasparks
 * ```
 *
 * Wiki — full theme documentation:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/00-%E2%80%90-TriviaSparks-Game#design-system--triviasparks
 *
 * Figma — design system reference:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 */

// ─────────────────────────────────────────────────────────────────────────────
// LIGHT COLOUR SCHEME
//
// Maps TriviaSparksTheme colour tokens to Material3 roles. M3 components read from
// these roles automatically — for example, every Button uses `primary` as its
// fill without any explicit colour call.
//
// Token → M3 role:
//   Violet400    → primary          (buttons, active states, links)
//   Coral400     → secondary        (XP badges, score pills)
//   MintGreen    → tertiary         (easy difficulty, success states)
//   Strawberry   → error            (hard difficulty, destructive actions)
//   LavenderMist → background       (screen canvas)
//   SurfaceLight → surface          (card backgrounds)
//   DeepGrape    → onBackground     (primary text on screen)
//   DustyViolet  → onSurfaceVariant (secondary/muted text)
// ─────────────────────────────────────────────────────────────────────────────

private val LightColorScheme = lightColorScheme(

    // Primary — soft violet — buttons, active nav, links
    primary              = Violet400,
    onPrimary            = White,
    primaryContainer     = Violet100,           // selected chip background
    onPrimaryContainer   = Violet800,

    // Secondary — coral peach — XP badges, score pills, secondary CTAs
    secondary            = Coral400,
    onSecondary          = White,
    secondaryContainer   = Coral100,
    onSecondaryContainer = Coral900,

    // Tertiary — mint green — easy difficulty, success states
    tertiary             = MintGreen,
    onTertiary           = White,
    tertiaryContainer    = MintGreenContainer,
    onTertiaryContainer  = Color(0xFF0A4033),

    // Error — strawberry — hard difficulty, destructive actions
    error                = Strawberry,
    onError              = White,
    errorContainer       = StrawberryContainer,
    onErrorContainer     = Color(0xFF5C001A),

    // Background — lavender mist — the canvas behind all cards
    background           = LavenderMist,
    onBackground         = DeepGrape,

    // Surface — white — card backgrounds
    surface              = SurfaceLight,
    onSurface            = DeepGrape,
    surfaceVariant       = SurfaceVariantLight,  // inner sections, stat pills
    onSurfaceVariant     = DustyViolet,           // secondary/muted text

    // Inverse surfaces — used by Snackbar automatically
    inverseSurface       = DeepGrape,
    inverseOnSurface     = SoftLavender,
    inversePrimary       = Violet200,

    // Borders and dividers
    outline              = LilacBorder,
    outlineVariant       = LilacBorderSubtle,

    // Scrim — modal overlay dimming
    scrim                = ScrimLight,
    surfaceTint          = Violet400
)

// ─────────────────────────────────────────────────────────────────────────────
// DARK COLOUR SCHEME
//
// Mirrors the light scheme with dark-optimised values. Accent colours
// (violet, coral, mint, yellow, strawberry) are intentionally unchanged —
// they read well on dark backgrounds without needing darker variants.
//
// The deep MidnightGrape background (#12101F) makes cards look like glowing
// violet tiles — this is the game-tile aesthetic in dark mode.
// ─────────────────────────────────────────────────────────────────────────────

private val DarkColorScheme = darkColorScheme(

    // Primary — violet unchanged — works on dark backgrounds
    primary              = Violet400,
    onPrimary            = White,
    primaryContainer     = Color(0xFF3D3580),
    onPrimaryContainer   = Violet100,

    // Secondary — coral unchanged
    secondary            = Coral400,
    onSecondary          = White,
    secondaryContainer   = Color(0xFF6B2D18),
    onSecondaryContainer = Coral100,

    // Tertiary — mint unchanged
    tertiary             = MintGreen,
    onTertiary           = Color(0xFF003826),
    tertiaryContainer    = Color(0xFF0A5240),
    onTertiaryContainer  = MintGreenContainer,

    // Error — strawberry unchanged
    error                = Strawberry,
    onError              = White,
    errorContainer       = Color(0xFF5C001A),
    onErrorContainer     = StrawberryContainer,

    // Background — midnight grape — deep purple-black
    background           = MidnightGrape,
    onBackground         = SoftLavender,

    // Surface — dark violet tile — card backgrounds in dark mode
    surface              = SurfaceDark,
    onSurface            = SoftLavender,
    surfaceVariant       = SurfaceVariantDark,
    onSurfaceVariant     = MutedViolet,

    inverseSurface       = SoftLavender,
    inverseOnSurface     = DeepGrape,
    inversePrimary       = Violet600,

    outline              = DarkLilacBorder,
    outlineVariant       = DarkLilacBorderSubtle,

    scrim                = ScrimDark,
    surfaceTint          = Violet400
)

// ─────────────────────────────────────────────────────────────────────────────
// EXTENDED COLOURS — TriviaSparksThemeExtendedColors
//
// Material3's ColorScheme has no slots for difficulty colours or category
// accent colours — these are Trivia Sparks-specific tokens. They are injected
// into the composable tree via CompositionLocal and read anywhere through
// MaterialTheme.triviasparks.
//
// Wiki — extended vs standard colour tokens:
// https://github.com/your-username/trivia-sparks/wiki/App-Trivia-Sparks#colours
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Extended colour tokens for the TriviaSparksTheme design system.
 *
 * Holds tokens that have no equivalent in Material3's [ColorScheme]:
 * difficulty colours with their text and container variants, category accent
 * colours, and game feedback colours.
 *
 * Do not instantiate directly — access via [MaterialTheme.triviasparks].
 *
 * Wiki — when to use extended vs colorScheme tokens:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#theme--how-TriviaSparksTheme-is-applied-this-week
 */
data class TriviaSparksThemeExtendedColors(

    // ── Difficulty ────────────────────────────────────────────────────────
    // Each difficulty has three values — see Color.kt for the contrast rules.
    val easy: Color,            // mint green — border, icon, dark mode text
    val easyOnLight: Color,     // darker mint — text on white backgrounds (WCAG AA)
    val easyContainer: Color,   // very light mint — chip background fill

    val medium: Color,          // sunny yellow — border, icon, dark mode text
    val mediumOnLight: Color,   // darker yellow — text on white (⚠️ white fails contrast)
    val mediumContainer: Color, // very light yellow — chip background fill

    val hard: Color,            // strawberry red — border, icon, dark mode text
    val hardOnLight: Color,     // darker red — text on white backgrounds (WCAG AA)
    val hardContainer: Color,   // very light pink — chip background fill

    // ── Category colours ──────────────────────────────────────────────────
    // One fixed accent per quiz category. Container background is always the
    // accent at 15% opacity. Icon tint is always the accent at 100% opacity.
    val categoryScience: Color,
    val categorySports: Color,
    val categoryHistory: Color,
    val categoryTechnology: Color,
    val categoryArt: Color,
    val categoryMusic: Color,
    val categoryFilm: Color,
    val categoryGeography: Color,

    // ── Game feedback ─────────────────────────────────────────────────────
    val scoreNumber: Color,  // colour for large score display numbers — Violet400
    val xpColour: Color,     // colour for XP reward text and icons — Coral400
    val cardGlow: Color      // base colour for the card soft shadow glow — Violet400
)

// ─────────────────────────────────────────────────────────────────────────────
// LIGHT EXTENDED COLOURS
//
// In light mode, difficulty text uses the -OnLight variants for WCAG AA
// contrast on white card backgrounds.
// ─────────────────────────────────────────────────────────────────────────────

private val LightExtendedColors = TriviaSparksThemeExtendedColors(
    easy               = MintGreen,
    easyOnLight        = MintGreenOnLight,       // #3AAB87 — darker for white bg contrast
    easyContainer      = MintGreenContainer,

    medium             = SunnyYellow,
    mediumOnLight      = SunnyYellowOnLight,     // #B8922A — required, white fails on yellow
    mediumContainer    = SunnyYellowContainer,

    hard               = Strawberry,
    hardOnLight        = StrawberryOnLight,      // #C42050 — darker for white bg contrast
    hardContainer      = StrawberryContainer,

    categoryScience    = CategoryScience,
    categorySports     = CategorySports,
    categoryHistory    = CategoryHistory,
    categoryTechnology = CategoryTechnology,
    categoryArt        = CategoryArt,
    categoryMusic      = CategoryMusic,
    categoryFilm       = CategoryFilm,
    categoryGeography  = CategoryGeography,

    scoreNumber        = Violet400,
    xpColour           = Coral400,
    cardGlow           = Violet400
)

// ─────────────────────────────────────────────────────────────────────────────
// DARK EXTENDED COLOURS
//
// In dark mode, the base difficulty colours are used directly for text —
// the dark background provides enough contrast without the -OnLight variants.
// ─────────────────────────────────────────────────────────────────────────────

private val DarkExtendedColors = TriviaSparksThemeExtendedColors(
    easy               = MintGreen,
    easyOnLight        = MintGreen,              // full brightness — dark bg gives contrast
    easyContainer      = Color(0xFF0A5240),

    medium             = SunnyYellow,
    mediumOnLight      = SunnyYellow,            // full brightness — dark bg gives contrast
    mediumContainer    = Color(0xFF4A3800),

    hard               = Strawberry,
    hardOnLight        = Strawberry,             // full brightness — dark bg gives contrast
    hardContainer      = Color(0xFF5C001A),

    categoryScience    = CategoryScience,
    categorySports     = CategorySports,
    categoryHistory    = CategoryHistory,
    categoryTechnology = CategoryTechnology,
    categoryArt        = CategoryArt,
    categoryMusic      = CategoryMusic,
    categoryFilm       = CategoryFilm,
    categoryGeography  = CategoryGeography,

    scoreNumber        = Violet400,
    xpColour           = Coral400,
    cardGlow           = Violet400
)

// ─────────────────────────────────────────────────────────────────────────────
// COMPOSITION LOCAL
//
// CompositionLocal passes extended colours down the composable tree without
// threading them through every function parameter. [TriviaSparksTheme] provides
// the correct instance (light or dark) at the root. [MaterialTheme.triviasparks]
// reads the current instance from anywhere in the tree.
// ─────────────────────────────────────────────────────────────────────────────

/** Internal holder for extended colours. Do not access directly — use [MaterialTheme.triviasparks]. */
val LocalTriviaSparksColors = staticCompositionLocalOf { LightExtendedColors }

// ─────────────────────────────────────────────────────────────────────────────
// THEME ENTRY POINT
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Root theme composable for Trivia Sparks.
 *
 * Place this at the top of the composable tree in `MainActivity`. All Material3
 * components inside automatically receive the correct colour scheme, typography,
 * and shapes. Extended tokens are accessible via [MaterialTheme.triviasparks].
 *
 * The [darkTheme] parameter defaults to the system setting. In Week 12 this
 * default is overridden by the user's preference stored in DataStore:
 * ```kotlin
 * val darkTheme by userPreferences.darkMode.collectAsState(initial = isSystemInDarkTheme())
 * TriviaSparksTheme(darkTheme = darkTheme) { ... }
 * ```
 *
 * @param darkTheme  True applies the dark colour scheme. Defaults to [isSystemInDarkTheme].
 * @param content    The composable content to theme — typically [AppNavGraph].
 */
@Composable
fun TriviaSparksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme    = if (darkTheme) DarkColorScheme    else LightColorScheme
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(
        LocalTriviaSparksColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = TriviaSparksTypography,
            shapes      = TriviaSparksShapes,
            content     = content
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// EXTENDED COLOUR ACCESSOR
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Convenience accessor for [TriviaSparksThemeExtendedColors] from any composable.
 *
 * Reads from [LocalTriviaSparksColors] provided by [TriviaSparksTheme].
 * Returns the light or dark variant automatically based on the active theme.
 *
 * ```kotlin
 * val ext = MaterialTheme.triviasparks
 *
 * ext.easy            // mint green — easy difficulty
 * ext.medium          // sunny yellow — medium difficulty
 * ext.hard            // strawberry — hard difficulty
 * ext.easyOnLight     // darker mint — text on white backgrounds
 * ext.scoreNumber     // violet — large score display numbers
 * ext.categoryScience // blue — Science category accent
 * ```
 */
val MaterialTheme.triviasparks: TriviaSparksThemeExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalTriviaSparksColors.current

