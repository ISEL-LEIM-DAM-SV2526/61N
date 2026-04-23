package pt.isel.dam.sv2526.triviasparks.domain.model
import androidx.compose.ui.graphics.Color


/**
 * Represents a difficulty level shown in the difficulty chip selector.
 *
 * Each difficulty has a unique icon and accent colour from the Dreamscape theme.
 * Text colour uses the -onLight variant in light mode for WCAG AA compliance —
 * particularly important for [mediumOnLight] since yellow (#FFD166) fails contrast on white.
 *
 * Wiki — difficulty colour tokens:
 * https://github.com/your-username/trivia-sparks/wiki/App-Trivia-Sparks#design-system--dreamscape
 */
data class DifficultyOption(
    val label: String,        // "Easy" | "Medium" | "Hard"
    val iconRes: Int,         // Drawable resource ID — star / lightning / flame
    val colour: Color,        // Base accent colour (border + icon)
    val onLightColour: Color  // Darker variant for text on white backgrounds — WCAG AA
)