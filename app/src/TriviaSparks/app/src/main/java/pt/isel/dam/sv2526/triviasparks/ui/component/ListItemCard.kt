package pt.isel.dam.sv2526.triviasparks.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.theme.CardShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.CategoryScience
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconBoxShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * A tappable list item card with a coloured icon container on the left
 * and a title + optional subtitle column on the right.
 *
 * This is a **shared component** — the same card is reused across multiple
 * screens wherever a vertical list of items with a category icon is needed:
 * - [HomeScreen] — "Latest Quizzes" list
 * - [CategoryScreen] — category list rows
 *
 * **Layout:**
 * ```
 * ┌──────────────────────────────────────────────┐
 * │ [icon box]  Title                            │
 * │             Subtitle (optional)              │
 * └──────────────────────────────────────────────┘
 * ```
 *
 * The icon container background is [iconTint] at 15% opacity — a washed
 * background with a full-colour icon on top. This is the standard Dreamscape
 * category icon pattern used consistently across all screens.
 *
 * Long titles and subtitles are truncated to one line with [TextOverflow.Ellipsis]
 * — never wrap to a second line inside a list card.
 *
 * Marked `internal` — only accessible within this module.
 * Move to `public` if reused across modules in a multi-module project.
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=list-item-card
 *
 * Wiki — shared components:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#shared-components-created-this-week
 *
 * Wiki — icon container pattern:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#category-icon-containers
 *
 * @param title     Primary text shown in [MaterialTheme.typography.titleMedium] SemiBold.
 *                  Truncated to one line with ellipsis if too long.
 * @param subtitle  Optional secondary text shown below the title in [MaterialTheme.typography.bodySmall].
 *                  Pass null to show the title only — the card height adjusts automatically.
 * @param iconRes   Drawable resource ID for the category icon.
 * @param iconTint  Category accent colour — used for both the icon (100% opacity) and the
 *                  container background (15% opacity). See Dreamscape category colour table.
 * @param onClick   Called when the user taps anywhere on the card.
 * @param modifier  Applied to the outermost [Card] element.
 */
@Composable
internal fun ListItemCard(
    title: String,
    subtitle: String? = null,
    iconRes: Int,
    iconTint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick  = onClick,
        modifier = modifier.fillMaxWidth(),
        shape    = CardShape,
        colors   = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(Spacing.cardPadding),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.iconToText)
        ) {
            // Icon container — 15% opacity background, full-colour icon
            // clip() is not needed here — background() with a shape clips correctly
            // when the shape is set before the content is drawn
            Box(
                modifier         = Modifier
                    .size(ComponentSize.iconContainerMedium)
                    .background(
                        color  = iconTint.copy(alpha = 0.15f),
                        shape  = IconBoxShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter            = painterResource(iconRes),
                    contentDescription = null,
                    tint               = iconTint,
                    modifier           = Modifier.size(IconSize.md)
                )
            }

            // Title + optional subtitle — weight(1f) prevents overflow on long text
            Column(
                modifier            = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                Text(
                    text       = title,
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color      = MaterialTheme.colorScheme.onSurface,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                subtitle?.let {
                    Text(
                        text     = it,
                        style    = MaterialTheme.typography.bodySmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "ListItemCard — with subtitle, light")
@Composable
private fun ListItemCardWithSubtitleLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        ListItemCard(
            title    = "Quantum Physics",
            subtitle = "15 Questions • Advanced",
            iconRes  = R.drawable.ic_science,
            iconTint = CategoryScience,
            onClick  = {},
            modifier = Modifier.padding(horizontal = Spacing.screenEdge, vertical = Spacing.xs)
        )
    }
}

@Preview(showBackground = true, name = "ListItemCard — with subtitle, dark")
@Composable
private fun ListItemCardWithSubtitleDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        ListItemCard(
            title    = "Quantum Physics",
            subtitle = "15 Questions • Advanced",
            iconRes  = R.drawable.ic_science,
            iconTint = CategoryScience,
            onClick  = {},
            modifier = Modifier.padding(horizontal = Spacing.screenEdge, vertical = Spacing.xs)
        )
    }
}

@Preview(showBackground = true, name = "ListItemCard — title only, light")
@Composable
private fun ListItemCardTitleOnlyPreview() {
    TriviaSparksTheme(darkTheme = false) {
        ListItemCard(
            title    = "Quantum Physics",
            subtitle = null,
            iconRes  = R.drawable.ic_science,
            iconTint = CategoryScience,
            onClick  = {},
            modifier = Modifier.padding(horizontal = Spacing.screenEdge, vertical = Spacing.xs)
        )
    }
}

@Preview(showBackground = true, name = "ListItemCard — long title overflow")
@Composable
private fun ListItemCardLongTitlePreview() {
    TriviaSparksTheme(darkTheme = false) {
        ListItemCard(
            title    = "This Is A Very Long Quiz Title That Should Be Truncated With Ellipsis",
            subtitle = "30 Questions • Hard",
            iconRes  = R.drawable.ic_science,
            iconTint = CategoryScience,
            onClick  = {},
            modifier = Modifier.padding(horizontal = Spacing.screenEdge, vertical = Spacing.xs)
        )
    }
}