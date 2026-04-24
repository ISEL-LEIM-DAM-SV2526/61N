package pt.isel.dam.sv2526.triviasparks.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.theme.ChipShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * A pill-shaped row displaying an icon and a text label on a coloured background.
 *
 * This is a **shared component** — used wherever a compact, rounded information
 * badge with an icon is needed:
 * - [HomeScreen] — score stat pill (coral tint) and rank stat pill (lavender tint)
 * - [QuizDetailScreen] — question count pill (neutral outline) and XP reward pill (coral tint)
 * - Future use: any screen that needs a compact labelled badge
 *
 * **Layout:**
 * ```
 * ╭──────────────────╮
 * │  [icon]  label   │
 * ╰──────────────────╯
 * ```
 *
 * The [pillColor] and [iconTint] are always provided by the caller — this
 * composable has no opinion about colour. This makes it flexible across contexts:
 * the same component renders a coral score pill and a violet rank pill by
 * simply passing different colour arguments.
 *
 * **Usage examples:**
 * ```kotlin
 * // HomeScreen — score pill (coral tint)
 * InfoPill(
 *     iconRes   = R.drawable.ic_trophy,
 *     iconTint  = MaterialTheme.colorScheme.secondary,
 *     label     = "2,450",
 *     pillColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
 * )
 *
 * // HomeScreen — rank pill (lavender tint)
 * InfoPill(
 *     iconRes   = R.drawable.ic_crown,
 *     iconTint  = MaterialTheme.colorScheme.primary,
 *     label     = "#12",
 *     pillColor = MaterialTheme.colorScheme.surfaceVariant
 * )
 *
 * // QuizDetailScreen — XP reward pill (coral tint with border)
 * InfoPill(
 *     iconRes   = R.drawable.ic_star,
 *     iconTint  = MaterialTheme.colorScheme.secondary,
 *     label     = "250 XP",
 *     pillColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
 * )
 * ```
 *
 * Marked `internal` — only accessible within this module.
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=info-pill
 *
 * Wiki — InfoPill usage and colour decisions:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#stat-pills--two-different-tints
 *
 * @param iconRes    Drawable resource ID for the leading icon.
 * @param iconTint   Tint colour applied to the icon. Provided by the caller.
 * @param label      The text value displayed to the right of the icon.
 * @param pillColor  Background fill colour for the pill. Provided by the caller.
 *                   Use `color.copy(alpha = 0.12f)` for a washed tint effect.
 * @param modifier   Applied to the outermost [Row] element.
 */
@Composable
internal fun InfoPill(
    iconRes: Int,
    iconTint: Color,
    label: String,
    pillColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier              = modifier
            .background(color = pillColor, shape = ChipShape)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter            = painterResource(iconRes),
            contentDescription = null,   // decorative — label already describes the value
            tint               = iconTint,
            modifier           = Modifier.size(IconSize.sm)   // 20dp
        )
        Spacer(modifier = Modifier.width(Spacing.sm))
        Text(
            text       = label,
            style      = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color      = MaterialTheme.colorScheme.onBackground
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "InfoPill — score, light")
@Composable
private fun InfoPillScoreLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        InfoPill(
            iconRes   = R.drawable.ic_trophy,
            iconTint  = MaterialTheme.colorScheme.secondary,
            label     = "2,450",
            pillColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
            modifier  = androidx.compose.ui.Modifier.padding(Spacing.md)
        )
    }
}

@Preview(
    showBackground = true,
    name           = "InfoPill — score, dark",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun InfoPillScoreDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        InfoPill(
            iconRes   = R.drawable.ic_trophy,
            iconTint  = MaterialTheme.colorScheme.secondary,
            label     = "2,450",
            pillColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
            modifier  = androidx.compose.ui.Modifier.padding(Spacing.md)
        )
    }
}

@Preview(showBackground = true, name = "InfoPill — rank, light")
@Composable
private fun InfoPillRankLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        InfoPill(
            iconRes   = R.drawable.ic_crown,
            iconTint  = MaterialTheme.colorScheme.primary,
            label     = "#12",
            pillColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier  = androidx.compose.ui.Modifier.padding(Spacing.md)
        )
    }
}

@Preview(showBackground = true, name = "InfoPill — XP reward, light")
@Composable
private fun InfoPillXpLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        InfoPill(
            iconRes   = R.drawable.ic_light,
            iconTint  = MaterialTheme.colorScheme.secondary,
            label     = "250 XP",
            pillColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
            modifier  = androidx.compose.ui.Modifier.padding(Spacing.md)
        )
    }
}