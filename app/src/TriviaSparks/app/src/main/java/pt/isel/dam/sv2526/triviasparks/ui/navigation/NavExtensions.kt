package pt.isel.dam.sv2526.triviasparks.ui.navigation

import androidx.navigation.NavController

/**
 * Navigates to a bottom navigation tab destination without back-stack buildup.
 *
 * Standard [NavController.navigate] would push a new destination onto the back
 * stack every time a tab is tapped — pressing back would cycle through tabs
 * instead of exiting the app. This extension prevents that with three options:
 *
 * - `popUpTo(Routes.HOME) { saveState = true }` — removes everything above HOME
 *   when switching tabs, keeping HOME as the root. Saves the leaving tab's state.
 * - `launchSingleTop = true` — re-tapping the current tab does not create a duplicate.
 * - `restoreState = true` — coming back to a tab restores its previous scroll
 *   position and state rather than recreating from scratch.
 *
 * **Usage:**
 * ```kotlin
 * AppBottomBar(
 *     selectedRoute = currentRoute,
 *     onNavSelected = { route -> navController.navigateTab(route) }
 * )
 * ```
 *
 * Wiki — bottom navigation back stack explained:
 * https://github.com/your-username/trivia-sparks/wiki/Week-3-4#bottom-navigation
 *
 * @param route  The destination route to navigate to. Should be one of the
 *               bottom nav routes: [Routes.HOME], [Routes.LEADERBOARD],
 *               [Routes.HISTORY], [Routes.PROFILE].
 */
fun NavController.navigateTab(route: String) {
    navigate(route) {
        // Pop everything up to (but not including) HOME
        // saveState = true — preserves the tab's state when leaving it
        popUpTo(Routes.HOME) {
            saveState = true
        }
        // Re-tapping the current tab does not create a duplicate destination
        launchSingleTop = true
        // Restores the tab's scroll position and state when returning to it
        restoreState = true
    }
}