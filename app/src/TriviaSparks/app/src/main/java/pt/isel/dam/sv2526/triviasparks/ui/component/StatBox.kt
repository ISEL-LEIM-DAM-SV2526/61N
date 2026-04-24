package pt.isel.dam.sv2526.triviasparks.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.theme.CardShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * A stat display box with a coloured icon circle, a value, and an uppercase label.
 *
 * This is a **shared component** — used wherever a compact stat with a semantic
 * colour needs to be shown:
 * - [ResultsScreen] — Correct / Wrong / Time stats row
 * - Future use: Profile screen (total quizzes, correct rate, streak)
 *
 * **Layout:**
 * ```
 * ╭──────────────╮
 * │    (icon)    │  ← coloured circle, statIconSize
 * │      17      │  ← value in accent colour
 * │   CORRECT    │  ← uppercase label in accent colour
 * ╰──────────────╯
 * ```
 *
 * Background is [colour] at 10% opacity. The icon circle is [colour] at 15% opacity.
 * Value and label use [colour] at full opacity.
 * The caller always provides the colour — this composable has no opinion about which
 * colour to use. This is the same philosophy as [InfoPill].
 *
 * **Usage example:**
 * ```kotlin
 * // Results screen — three stat boxes side by side
 * Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Spacing.md)) {
 *     StatBox(iconRes = R.drawable.ic_check_circle, colour = colorScheme.tertiary,
 *             value = "17", label = "CORRECT", modifier = Modifier.weight(1f))
 *     StatBox(iconRes = R.drawable.ic_close_circle, colour = colorScheme.error,
 *             value = "3",  label = "WRONG",   modifier = Modifier.weight(1f))
 *     StatBox(iconRes = R.drawable.ic_clock, colour = colorScheme.primary,
 *             value = "1:24s", label = "TIME", modifier = Modifier.weight(1f))
 * }
 * ```
 *
 * Marked `internal` — only accessible within this module.
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=stat-box
 *
 * Wiki — shared components:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#shared-components-created-this-week
 *
 * @param iconRes   Drawable resource ID for the icon inside the circle.
 * @param colour    Accent colour for the background, icon circle, value, and label.
 *                  Always provided by the caller — no default.
 * @param value     Formatted string displayed as the main value, e.g. "17" or "1:24s".
 * @param label     UPPERCASE string displayed below the value, e.g. "CORRECT" or "TIME".
 * @param modifier  Applied to the outermost [Column] element.
 *                  Callers typically pass [Modifier.weight(1f)] for equal width in a [Row].
 */
@Composable
internal fun StatBox(
    iconRes: Int,
    colour: Color,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier            = modifier
            .background(color = colour.copy(alpha = 0.10f), shape = CardShape)
            .padding(vertical = Spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        // Icon circle — colour at 15% opacity, icon at full opacity
        Box(
            modifier         = Modifier
                .size(StatBoxDefaults.iconCircleSize)
                .background(color = colour.copy(alpha = 0.15f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter            = painterResource(iconRes),
                contentDescription = null,   // decorative — label describes the stat
                tint               = colour,
                modifier           = Modifier.size(IconSize.sm)   // 20dp
            )
        }

        // Value — titleLarge Bold in the accent colour
        Text(
            text       = value,
            style      = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color      = colour
        )

        // Label — labelSmall uppercase in the accent colour
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall,
            color = colour
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// STAT BOX DEFAULTS
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Size constants for [StatBox].
 * Extracted here so they can be referenced both inside the component
 * and in the calling screen (e.g. to set the outer row height).
 */
internal object StatBoxDefaults {
    val iconCircleSize = 36.dp  // coloured icon circle
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "StatBox — Correct, light")
@Composable
private fun StatBoxCorrectLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        StatBox(
            iconRes  = R.drawable.ic_correct,
            colour   = MaterialTheme.colorScheme.tertiary,
            value    = "17",
            label    = "CORRECT",
            modifier = Modifier.padding(Spacing.md)
        )
    }
}

@Preview(showBackground = true, name = "StatBox — Wrong, light")
@Composable
private fun StatBoxWrongLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        StatBox(
            iconRes  = R.drawable.ic_incorrect,
            colour   = MaterialTheme.colorScheme.error,
            value    = "3",
            label    = "WRONG",
            modifier = Modifier.padding(Spacing.md)
        )
    }
}

@Preview(showBackground = true, name = "StatBox — Time, light")
@Composable
private fun StatBoxTimeLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        StatBox(
            iconRes  = R.drawable.ic_clock,
            colour   = MaterialTheme.colorScheme.primary,
            value    = "1:24s",
            label    = "TIME",
            modifier = Modifier.padding(Spacing.md)
        )
    }
}

@Preview(showBackground = true, name = "StatBox — all three, light")
@Composable
private fun StatBoxRowPreview() {
    TriviaSparksTheme(darkTheme = false) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(Spacing.screenEdge),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            StatBox(
                iconRes  = R.drawable.ic_check,
                colour   = MaterialTheme.colorScheme.tertiary,
                value    = "17",
                label    = "CORRECT",
                modifier = Modifier.weight(1f)
            )
            StatBox(
                iconRes  = R.drawable.ic_cross,
                colour   = MaterialTheme.colorScheme.error,
                value    = "3",
                label    = "WRONG",
                modifier = Modifier.weight(1f)
            )
            StatBox(
                iconRes  = R.drawable.ic_clock,
                colour   = MaterialTheme.colorScheme.primary,
                value    = "1:24s",
                label    = "TIME",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(
    showBackground = true,
    name           = "StatBox — all three, dark",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun StatBoxRowDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(Spacing.screenEdge),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            StatBox(
                iconRes  = R.drawable.ic_correct,
                colour   = MaterialTheme.colorScheme.tertiary,
                value    = "17",
                label    = "CORRECT",
                modifier = Modifier.weight(1f)
            )
            StatBox(
                iconRes  = R.drawable.ic_incorrect,
                colour   = MaterialTheme.colorScheme.error,
                value    = "3",
                label    = "WRONG",
                modifier = Modifier.weight(1f)
            )
            StatBox(
                iconRes  = R.drawable.ic_clock,
                colour   = MaterialTheme.colorScheme.primary,
                value    = "1:24s",
                label    = "TIME",
                modifier = Modifier.weight(1f)
            )
        }
    }
}