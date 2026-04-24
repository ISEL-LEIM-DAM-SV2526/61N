package pt.isel.dam.sv2526.triviasparks.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import pt.isel.dam.sv2526.triviasparks.ui.model.DifficultyOption
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme
import pt.isel.dam.sv2526.triviasparks.ui.theme.triviasparks

/**
 * Square-ish difficulty chip with a centred icon and label.
 *
 * This is a **shared component** — used on every screen where the player selects
 * a quiz difficulty:
 * - [CategoryScreen] — three chips in a horizontal row, square style
 * - [QuizDetailScreen] — same three chips in a horizontal row
 *
 * **Two visual states:**
 * - **Selected** — [option.colour] border (2dp), full-colour icon, label in
 *   [option.onLightColour] (the darker variant for WCAG AA contrast on white).
 * - **Unselected** — no border, icon at 60% opacity, label in
 *   [MaterialTheme.colorScheme.onSurfaceVariant].
 *
 * **Why `onLightColour` for the selected label?**
 * The base difficulty colours (especially `SunnyYellow` for Medium) fail WCAG AA
 * contrast when used as text on a white card background. `onLightColour` is a
 * pre-calculated darker variant that passes contrast while still communicating
 * the difficulty colour. In dark mode the base colours have enough contrast against
 * the dark surface and do not need a darker variant.
 *
 * **Sizing:** fixed height via [ComponentSize.difficultyChipSquare] (90dp).
 * Width is determined by the caller — typically [Modifier.weight(1f)] inside a [Row]
 * so all three chips share equal width.
 *
 * **State connection (Week 3):**
 * [isSelected] and [onClick] are currently driven by hardcoded values.
 * In Week 3 they connect to `remember { mutableStateOf("Easy") }` hoisted
 * in the parent screen — no changes to this composable are needed.
 *
 * Marked `internal` — only accessible within this module.
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=difficulty-chip
 *
 * Wiki — difficulty chip colour rules:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#difficulty-chips--three-values-per-chip
 *
 * @param option      The difficulty configuration — label, icon resource, and colour variants.
 *                    Use the values from [MaterialTheme.triviasparks] to build this.
 * @param isSelected  True when this chip is the currently active difficulty.
 * @param onClick     Called when the user taps the chip.
 *                    No-op this week — connects to hoisted state in Week 3.
 * @param modifier    Applied to the outermost [Card] element.
 *                    Callers typically pass [Modifier.weight(1f)] to equalise chip widths.
 */
@Composable
internal fun DifficultyChip(
    option: DifficultyOption,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Border colour is transparent when unselected — keeps layout stable (no size shift)
    val borderColour = if (isSelected) option.colour else Color.Transparent

    // Icon at 60% opacity when unselected — still communicates category colour subtly
    val iconTint     = if (isSelected) option.colour else option.colour.copy(alpha = 0.6f)

    // onLightColour for selected label — darker variant ensures WCAG AA on white bg
    val labelColour  = if (isSelected) option.onLightColour
    else MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        onClick  = onClick,
        modifier = modifier
            .height(ComponentSize.difficultyChipSquare)  // 90dp
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = borderColour,
                shape = MaterialTheme.shapes.medium       // 16dp — matches ButtonShape
            ),
        shape  = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(Spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter            = painterResource(option.iconRes),
                contentDescription = option.label,
                tint               = iconTint,
                modifier           = Modifier.size(IconSize.lg)  // 32dp — prominent in a 90dp chip
            )
            Spacer(modifier = Modifier.height(Spacing.xs))
            Text(
                text       = option.label,
                style      = MaterialTheme.typography.labelMedium,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color      = labelColour
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// All three difficulties previewed in both selected and unselected states,
// plus a combined row preview matching the actual CategoryScreen layout.
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "DifficultyChip — Easy selected, light")
@Composable
private fun DifficultyChipEasySelectedLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        val ext = MaterialTheme.triviasparks
        DifficultyChip(
            option = DifficultyOption(
                label         = "Easy",
                iconRes       = R.drawable.ic_star_fill,
                colour        = ext.easy,
                onLightColour = ext.easyOnLight
            ),
            isSelected = true,
            onClick    = {},
            modifier   = Modifier.width(100.dp)
        )
    }
}

@Preview(showBackground = true, name = "DifficultyChip — Easy unselected, light")
@Composable
private fun DifficultyChipEasyUnselectedPreview() {
    TriviaSparksTheme(darkTheme = false) {
        val ext = MaterialTheme.triviasparks
        DifficultyChip(
            option = DifficultyOption(
                label         = "Easy",
                iconRes       = R.drawable.ic_star_fill,
                colour        = ext.easy,
                onLightColour = ext.easyOnLight
            ),
            isSelected = false,
            onClick    = {},
            modifier   = Modifier.width(100.dp)
        )
    }
}

@Preview(showBackground = true, name = "DifficultyChip — Medium selected, light")
@Composable
private fun DifficultyChipMediumSelectedPreview() {
    TriviaSparksTheme(darkTheme = false) {
        val ext = MaterialTheme.triviasparks
        DifficultyChip(
            option = DifficultyOption(
                label         = "Medium",
                iconRes       = R.drawable.ic_light,
                colour        = ext.medium,
                onLightColour = ext.mediumOnLight
            ),
            isSelected = true,
            onClick    = {},
            modifier   = Modifier.width(100.dp)
        )
    }
}

@Preview(showBackground = true, name = "DifficultyChip — Hard selected, light")
@Composable
private fun DifficultyChipHardSelectedLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        val ext = MaterialTheme.triviasparks
        DifficultyChip(
            option = DifficultyOption(
                label         = "Hard",
                iconRes       = R.drawable.ic_flame,
                colour        = ext.hard,
                onLightColour = ext.hardOnLight
            ),
            isSelected = true,
            onClick    = {},
            modifier   = Modifier.width(100.dp)
        )
    }
}

@Preview(
    showBackground = true,
    name           = "DifficultyChip — Hard selected, dark",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun DifficultyChipHardSelectedDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        val ext = MaterialTheme.triviasparks
        DifficultyChip(
            option = DifficultyOption(
                label         = "Hard",
                iconRes       = R.drawable.ic_flame,
                colour        = ext.hard,
                onLightColour = ext.hardOnLight
            ),
            isSelected = true,
            onClick    = {},
            modifier   = Modifier.width(100.dp)
        )
    }
}

@Preview(showBackground = true, name = "DifficultyChip — all three, Easy selected")
@Composable
private fun DifficultyChipRowPreview() {
    TriviaSparksTheme(darkTheme = false) {
        val ext = MaterialTheme.triviasparks
        Row(
            modifier              = Modifier.padding(Spacing.screenEdge),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            DifficultyChip(
                option     = DifficultyOption("Easy",   R.drawable.ic_star_fill, ext.easy,   ext.easyOnLight),
                isSelected = true,
                onClick    = {},
                modifier   = Modifier.weight(1f)
            )
            DifficultyChip(
                option     = DifficultyOption("Medium", R.drawable.ic_light,  ext.medium, ext.mediumOnLight),
                isSelected = false,
                onClick    = {},
                modifier   = Modifier.weight(1f)
            )
            DifficultyChip(
                option     = DifficultyOption("Hard",   R.drawable.ic_flame,      ext.hard,   ext.hardOnLight),
                isSelected = false,
                onClick    = {},
                modifier   = Modifier.weight(1f)
            )
        }
    }
}