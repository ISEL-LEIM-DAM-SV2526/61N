package pt.isel.dam.sv2526.triviasparks.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.ui.preview.bottomNavItems
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * The app-wide bottom navigation bar with four tabs: HOME, QUIZ, LEADERBOARDS, PROFILE.
 *
 * This is a **shared component** — placed in the `bottomBar` slot of [Scaffold]
 * on every screen that shows the main navigation:
 * - [HomeScreen] — always visible
 * - [LeaderboardScreen], [HistoryScreen], [ProfileScreen] — added as screens are built
 *
 * **Visual behaviour:**
 * The selected tab shows a [MaterialTheme.colorScheme.primaryContainer] indicator pill
 * behind a filled icon variant. Unselected tabs use outlined icon variants and
 * [MaterialTheme.colorScheme.onSurfaceVariant] for both icon and label.
 *
 * `tonalElevation = 0.dp` — the Dreamscape theme is flat. The [MaterialTheme.colorScheme.surface]
 * background alone separates the bar from the screen content.
 *
 * **Tab items** are declared in `mock/bottomNavItems` as a list of [BottomNavItem].
 * Each item carries two icon resource IDs — filled (selected) and outlined (unselected) —
 * following the Material3 convention for navigation icons.
 *
 * **Navigation wiring:**
 * [onNavSelected] does nothing this week — it is connected to [NavController.navigateTab]
 * in Week 4. The lambda signature (`(String) -> Unit`) already matches the final wiring,
 * so no changes to this composable are needed in Week 4.
 *
 * ```kotlin
 * // Week 4 — connecting to NavController at the call site
 * AppBottomBar(
 *     selectedRoute = currentRoute,
 *     onNavSelected = { route -> navController.navigateTab(route) }
 * )
 * ```
 *
 * Figma: https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 *
 *
 * @param selectedRoute   Route string of the currently active tab, e.g. `Routes.HOME`.
 * @param onNavSelected   Called when the user taps a tab. Receives the tab's route string.
 *                        Wired to [NavController.navigateTab] in Week 4.
 */
@Composable
internal fun AppBottomBar(
    selectedRoute: String,
    onNavSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp   // flat — Dreamscape uses surface colour, not tonal elevation
    ) {
        bottomNavItems.forEach { item ->
            val isSelected = item.route == selectedRoute

            NavigationBarItem(
                selected = isSelected,
                onClick  = { onNavSelected(item.route) },
                icon     = {
                    // Filled icon for selected tab, outlined for unselected — M3 convention
                    Icon(
                        painter = painterResource(
                            if (isSelected) item.iconSelectedRes
                            else            item.iconUnselectedRes
                        ),
                        contentDescription = item.label,
                        modifier           = Modifier.size(IconSize.md)
                    )
                },
                label = {
                    Text(
                        text     = item.label,
                        style    = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor   = MaterialTheme.colorScheme.primary,
                    indicatorColor      = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "AppBottomBar — HOME selected, light")
@Composable
private fun AppBottomBarHomeLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        AppBottomBar(
            selectedRoute = "home",
            onNavSelected = {}
        )
    }
}

@Preview(
    showBackground = true,
    name           = "AppBottomBar — HOME selected, dark",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AppBottomBarHomeDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        AppBottomBar(
            selectedRoute = "home",
            onNavSelected = {}
        )
    }
}

@Preview(showBackground = true, name = "AppBottomBar — QUIZ selected, light")
@Composable
private fun AppBottomBarQuizSelectedPreview() {
    TriviaSparksTheme(darkTheme = false) {
        AppBottomBar(
            selectedRoute = "quiz",
            onNavSelected = {}
        )
    }
}