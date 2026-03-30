package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────
//  TriviaSparks ARENA — Theme Entry Point
//
//  Usage:
//    TriviaSparksTheme {                          // follows system setting
//    TriviaSparksTheme(darkTheme = true) { … }   // force dark
//    TriviaSparksTheme(darkTheme = false) { … }  // force light
//
//  Access tokens anywhere inside:
//    MaterialTheme.colorScheme.primary
//    MaterialTheme.triviaSparks.warning
//    MaterialTheme.triviaSparks.surfaceHighest
//    MaterialTheme.difficulty.hardBackground
// ─────────────────────────────────────────────

// ── Dark color scheme ─────────────────────────────────────────────────

private val DarkColorScheme = darkColorScheme(

    primary             = Violet400,
    onPrimary           = Color.White,
    primaryContainer    = Violet200,
    onPrimaryContainer  = Violet900,
    inversePrimary      = VioletDim,

    secondary           = Lavender300,
    onSecondary         = Lavender800,
    secondaryContainer  = Lavender700,
    onSecondaryContainer= Lavender100,

    tertiary            = Mint300,
    onTertiary          = MintDeep,
    tertiaryContainer   = Mint300,
    onTertiaryContainer = MintDeep,

    error               = Strawberry400,
    onError             = Strawberry900,
    errorContainer      = Strawberry200,
    onErrorContainer    = Strawberry950,

    background          = MidnightGrape,
    onBackground        = OnBackground,

    surface             = Surface0,
    onSurface           = OnSurface,
    surfaceVariant      = SurfaceVariant,
    onSurfaceVariant    = OnSurfaceVar,

    surfaceContainerLowest  = Surface1,
    surfaceContainerLow     = Surface2,
    surfaceContainer        = Surface3,
    surfaceContainerHigh    = Surface3,
    surfaceContainerHighest = Surface4,

    outline             = OutlineColor,
    outlineVariant      = OutlineColor.copy(alpha = 0.30f),

    inverseSurface      = InverseSurface,
    inverseOnSurface    = InverseOnSurface,

    scrim               = MidnightGrape.copy(alpha = 0.72f),
)

// ── Light color scheme ────────────────────────────────────────────────
//
//  Still unmistakably Dreamscape — soft lavender-white surfaces,
//  violet primaries, same pastel accents — just with enough contrast
//  for bright environments. The "no-line" and "tonal layering" rules
//  still apply; surfaces shift through LightSurface1→5 instead.

private val LightColorScheme = lightColorScheme(

    primary             = VioletLight,
    onPrimary           = VioletLightOn,
    primaryContainer    = VioletLightContainer,
    onPrimaryContainer  = VioletLightOnCont,
    inversePrimary      = Violet400,

    secondary           = LavenderLight,
    onSecondary         = LavenderLightOn,
    secondaryContainer  = LavenderLightCont,
    onSecondaryContainer= LavenderLightOnCont,

    tertiary            = MintLight,
    onTertiary          = MintLightOn,
    tertiaryContainer   = MintLightCont,
    onTertiaryContainer = MintLightOnCont,

    error               = StrawberryLight,
    onError             = StrawberryLightOn,
    errorContainer      = StrawberryLightCont,
    onErrorContainer    = StrawberryLightOnCont,

    background          = LightBackground,
    onBackground        = LightOnBackground,

    surface             = LightSurface,
    onSurface           = LightOnSurface,
    surfaceVariant      = LightSurfaceVariant,
    onSurfaceVariant    = LightOnSurfaceVar,

    surfaceContainerLowest  = LightSurface1,
    surfaceContainerLow     = LightSurface2,
    surfaceContainer        = LightSurface3,
    surfaceContainerHigh    = LightSurface4,
    surfaceContainerHighest = LightSurface5,

    outline             = OutlineLight,
    outlineVariant      = OutlineVariantLight,

    inverseSurface      = Surface0,
    inverseOnSurface    = OnSurface,

    scrim               = LightOnBackground.copy(alpha = 0.32f),
)

// ── Theme composable ─────────────────────────────────────────────────

@Composable
fun TriviaSparksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content:   @Composable () -> Unit,
) {
    val colorScheme  = if (darkTheme) DarkColorScheme       else LightColorScheme
    val customColors = if (darkTheme) DarkTriviaSparksColors  else LightTriviaSparksColors
    val dimens = TriviaSparksDimens()

    CompositionLocalProvider(
        LocalTriviaSparksColors provides customColors,
        LocalTriviaSparksDifficulty provides TriviaSparksDifficulty,
        LocalTriviaSparksDimens provides dimens
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = TriviaSparksTypography,
            shapes      = TriviaSparksShapes,
            content     = content,
        )
    }
}

// ── Extension accessors ───────────────────────────────────────────────

/**
 * Access extended TriviaSparks tokens.
 * Automatically returns dark or light variant based on the current theme.
 *
 * val warnColor   = MaterialTheme.triviaSparks.warning
 * val topSurface  = MaterialTheme.triviaSparks.surfaceHighest
 */
val MaterialTheme.triviaSparks: TriviaSparksColors
    @Composable @ReadOnlyComposable
    get() = LocalTriviaSparksColors.current

/**
 * Difficulty chip colors — identical in both modes
 * (saturated pastels read well on dark and light surfaces alike).
 *
 * val bg = MaterialTheme.difficulty.hardBackground
 */
val MaterialTheme.difficulty: DifficultyColors
    @Composable @ReadOnlyComposable
    get() = LocalTriviaSparksDifficulty.current

val MaterialTheme.dimens: TriviaSparksDimens
    @Composable
    @ReadOnlyComposable
    get() = LocalTriviaSparksDimens.current