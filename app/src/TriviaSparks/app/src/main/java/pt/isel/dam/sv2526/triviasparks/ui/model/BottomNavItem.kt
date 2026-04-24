package pt.isel.dam.sv2526.triviasparks.ui.model

/**
 * Navigation destination for the bottom navigation bar.
 *
 * Holds two icon resource IDs — filled for the selected state and outlined for
 * the unselected state. This follows the Material3 convention: filled icons for
 * active tabs, outlined icons for inactive tabs.
 *
 * Wiki — NavigationBar icon convention:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#navigationbar
 */
data class BottomNavItem(
    val route: String,            // Navigation route string — wired to NavController in Week 4
    val label: String,            // Tab label shown below the icon, always UPPERCASE
    val iconSelectedRes: Int,     // Filled icon drawable — active tab state
    val iconUnselectedRes: Int    // Outlined icon drawable — inactive tab state
)
