package pt.isel.dam.sv2526.triviasparks.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.SearchBarShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * Pill-shaped search input field.
 *
 * This is a **shared component** — used wherever a text search is needed:
 * - [CategoryScreen] — filter categories by name
 * - Future use: friend search, quiz browser search
 *
 * Named `AppSearchBar` to avoid a name conflict with
 * [androidx.compose.material3.SearchBar], which is a different, larger M3 component.
 *
 * **Layout:**
 * ```
 * ╭──────────────────────────────────╮
 * │  🔍  Search categories...        │
 * ╰──────────────────────────────────╯
 * ```
 *
 * Uses [SearchBarShape] (pill, 99dp) and a transparent unfocused border —
 * looks like a filled pill at rest and shows a `primary` border on focus.
 *
 * **Usage example — CategoryScreen:**
 * ```kotlin
 * var searchQuery by remember { mutableStateOf("") }
 * val filteredCategories by remember {
 *     derivedStateOf {
 *         if (searchQuery.isBlank()) categories
 *         else categories.filter { it.name.contains(searchQuery, ignoreCase = true) }
 *     }
 * }
 * AppSearchBar(
 *     value         = searchQuery,
 *     onValueChange = { searchQuery = it },
 *     placeholder   = "Search categories..."
 * )
 * ```
 *
 * **Usage example — Friend search:**
 * ```kotlin
 * AppSearchBar(
 *     value         = friendQuery,
 *     onValueChange = { friendQuery = it },
 *     placeholder   = "Search friends..."
 * )
 * ```
 *
 * Marked `internal` — only accessible within this module.
 *
 * Figma: https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 *
 * Wiki — shared components:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/02-%E2%80%90-Jetpack-Compose-%E2%80%90-Compose-Fundamentals#shared-components-created-this-week
 *
 * @param value          Current text value — hoisted from the caller.
 * @param onValueChange  Called on every keystroke with the updated text.
 * @param placeholder    Hint text shown when the field is empty, e.g. "Search categories...".
 * @param modifier       Applied to the [OutlinedTextField] element.
 */
@Composable
internal fun AppSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value         = value,
        onValueChange = onValueChange,
        placeholder   = {
            Text(
                text  = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingIcon = {
            Icon(
                painter            = painterResource(R.drawable.ic_search),
                contentDescription = null,   // decorative — placeholder describes the purpose
                tint               = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier           = Modifier.size(IconSize.sm)   // 20dp
            )
        },
        singleLine = true,
        modifier   = modifier.fillMaxWidth(),
        shape      = SearchBarShape,   // 99dp pill
        colors     = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor    = Color.Transparent,                   // invisible at rest
            focusedBorderColor      = MaterialTheme.colorScheme.primary,   // primary border on focus
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor   = MaterialTheme.colorScheme.surface
        )
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "AppSearchBar — empty, light")
@Composable
private fun AppSearchBarEmptyLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        AppSearchBar(
            value         = "",
            onValueChange = {},
            placeholder   = "Search categories...",
            modifier      = Modifier.padding(Spacing.screenEdge)
        )
    }
}

@Preview(
    showBackground = true,
    name           = "AppSearchBar — empty, dark",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AppSearchBarEmptyDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        AppSearchBar(
            value         = "",
            onValueChange = {},
            placeholder   = "Search categories...",
            modifier      = Modifier.padding(Spacing.screenEdge)
        )
    }
}

@Preview(showBackground = true, name = "AppSearchBar — with text, light")
@Composable
private fun AppSearchBarWithTextPreview() {
    TriviaSparksTheme(darkTheme = false) {
        AppSearchBar(
            value         = "Science",
            onValueChange = {},
            placeholder   = "Search categories...",
            modifier      = Modifier.padding(Spacing.screenEdge)
        )
    }
}

@Preview(showBackground = true, name = "AppSearchBar — friend search placeholder")
@Composable
private fun AppSearchBarFriendPreview() {
    TriviaSparksTheme(darkTheme = false) {
        AppSearchBar(
            value         = "",
            onValueChange = {},
            placeholder   = "Search friends...",
            modifier      = Modifier.padding(Spacing.screenEdge)
        )
    }
}