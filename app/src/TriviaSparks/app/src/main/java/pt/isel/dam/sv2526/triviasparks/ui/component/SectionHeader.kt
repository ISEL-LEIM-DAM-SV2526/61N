package pt.isel.dam.sv2526.triviasparks.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * A two-element row with a bold section title on the left and a "See all"
 * text link on the right.
 *
 * This is a **shared component** — used above every scrollable section that
 * has a dedicated full list screen:
 * - [HomeScreen] — "Recent Friends" section
 * - [HomeScreen] — "Latest Quizzes" section
 *
 * **Layout:**
 * ```
 * Recent Friends                          See all →
 * ```
 *
 * The title uses [MaterialTheme.typography.headlineSmall] Bold and
 * [MaterialTheme.colorScheme.onBackground] — it is a section label, not
 * a primary content headline, so it stays at the smaller headline scale.
 *
 * The "See all" link uses [MaterialTheme.colorScheme.primary] (violet) to signal
 * it is an interactive element without needing an underline or icon. The
 * [Modifier.semantics] block marks it as a [Role.Button] so accessibility tools
 * (TalkBack) announce it correctly — plain `clickable` alone does not convey role.
 *
 * Marked `internal` — only accessible within this module.
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=section-header
 *
 * Wiki — shared components:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#shared-components-created-this-week
 *
 * @param title     Section label displayed on the left, e.g. "Recent Friends".
 * @param onSeeAll  Called when the user taps the "See all" link.
 *                  Wired to [NavController.navigate] in Week 4.
 * @param modifier  Applied to the outermost [Row] element.
 *                  Callers typically add horizontal and vertical padding here.
 */
@Composable
internal fun SectionHeader(
    title: String,
    onSeeAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        // "See all" — primary colour signals interactivity without underline or icon
        // semantics(role = Button) ensures TalkBack announces this as a button,
        // not just plain text — clickable alone does not convey the interactive role
        Text(
            text = "See all",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(onClick = onSeeAll)
                .semantics { role = Role.Button }
                .padding(Spacing.xs)   // small padding increases the tap target area
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "SectionHeader — light")
@Composable
private fun SectionHeaderLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        SectionHeader(
            title = "Recent Friends",
            onSeeAll = {},
            modifier = Modifier.padding(horizontal = Spacing.screenEdge)
        )
    }
}

@Preview(
    showBackground = true,
    name = "SectionHeader — dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SectionHeaderDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        SectionHeader(
            title = "Latest Quizzes",
            onSeeAll = {},
            modifier = Modifier.padding(horizontal = Spacing.screenEdge)
        )
    }
}