package pt.isel.dam.sv2526.triviasparks.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.theme.AvatarShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.CardShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * A compact fixed-width card with a circular avatar image, a title, and a subtitle.
 *
 * This is a **shared component** — used wherever a horizontally scrollable list
 * of entities with a circular avatar is needed:
 * - [HomeScreen] — "Recent Friends" row
 * - Future use: multiplayer lobby player list, leaderboard compact entries
 *
 * **Layout:**
 * ```
 * ┌─────────┐
 * │  (img)  │  ← circular avatar, 64dp
 * │  Title  │  ← name, titleSmall SemiBold, 1 line
 * │ Subtitle│  ← score/label, bodySmall, primary colour
 * └─────────┘
 * ```
 *
 * Fixed width of 100dp — designed for use inside a [LazyRow] where a consistent
 * card width creates a uniform horizontal rhythm.
 *
 * The [subtitle] is always displayed in [MaterialTheme.colorScheme.primary] (violet)
 * to visually link it to the score pill on the HomeScreen — both represent the
 * same concept (a score value) in the same colour.
 *
 * Note: [Modifier.clip] must come before any background — the clip shape must be
 * established before the image is drawn into the clipped area.
 *
 * Marked `internal` — only accessible within this module.
 *
 * Figma: https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 *
 * Wiki — shared components:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/02-%E2%80%90-Jetpack-Compose-%E2%80%90-Compose-Fundamentals#shared-components-created-this-week
 *
 *
 * @param image     Drawable resource ID for the avatar image. Clipped to [AvatarShape] (circle).
 * @param title     Primary label — typically a person's display name.
 *                  Truncated to one line with ellipsis if too long.
 * @param subtitle  Secondary label shown below the title in [MaterialTheme.colorScheme.primary].
 *                  Typically a score string, e.g. "1.2k" or "890".
 * @param modifier  Applied to the outermost [Card] element.
 */
@Composable
internal fun AvatarCard(
    image: Int,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(100.dp),
        shape    = CardShape,
        colors   = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            horizontalAlignment   = Alignment.CenterHorizontally,
            verticalArrangement   = Arrangement.spacedBy(Spacing.sm)
        ) {
            // clip() FIRST — establishes the circle shape before the image is drawn
            // Reversing the order would paint a rectangle then clip, which shows
            // square corners on some devices
            Image(
                painter            = painterResource(image),
                contentDescription = "$title's avatar",
                modifier           = Modifier
                    .size(ComponentSize.avatarLarge)   // 64dp
                    .clip(AvatarShape)                 // fully circular
            )

            Text(
                text       = title,
                style      = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color      = MaterialTheme.colorScheme.onSurface,
                textAlign  = TextAlign.Center,
                maxLines   = 1,
                overflow   = TextOverflow.Ellipsis
            )

            // Subtitle in primary colour — visually links this score to the
            // score stat pill on HomeScreen (same concept, same colour)
            Text(
                text  = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "AvatarCard — light")
@Composable
private fun AvatarCardLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        AvatarCard(
            image    = R.drawable.ic_avatar5,
            title    = "Sarah",
            subtitle = "1.2k",
            modifier = Modifier.padding(Spacing.md)
        )
    }
}

@Preview(
    showBackground = true,
    name           = "AvatarCard — dark",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AvatarCardDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        AvatarCard(
            image    = R.drawable.ic_avatar5,
            title    = "Sarah",
            subtitle = "1.2k",
            modifier = Modifier.padding(Spacing.md)
        )
    }
}

@Preview(showBackground = true, name = "AvatarCard — long name overflow")
@Composable
private fun AvatarCardLongNamePreview() {
    TriviaSparksTheme(darkTheme = false) {
        AvatarCard(
            image    = R.drawable.ic_avatar5,
            title    = "Christopher",
            subtitle = "3.4k",
            modifier = Modifier.padding(Spacing.md)
        )
    }
}