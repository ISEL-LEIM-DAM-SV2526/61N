package pt.isel.dam.sv2526.triviasparks.ui.screens.placeholder

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.component.AppBottomBar
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * Placeholder screen shown for bottom navigation tabs that are not yet implemented.
 *
 * Displays a themed illustration (icon on a tinted circle), a title, a description
 * of what the screen will show, and the week it will be built.
 *
 * Each placeholder has full [AppBottomBar] so the player can switch between tabs
 * without getting stuck. The [selectedRoute] drives the correct tab highlight.
 *
 * Replaced week by week:
 * - [Route.HISTORY] → Week 7 (ROOM)
 * - [Route.PROFILE] → Week 8 (Firebase Auth)
 * - [Route.LEADERBOARD] → Week 9 (Firestore)
 *
 * @param title          Screen name shown as the main heading, e.g. "Leaderboard".
 * @param description    One line explaining what this screen will show.
 * @param comingIn       Which week this screen is built, e.g. "Week 9".
 * @param iconRes        Drawable resource for the centred illustration icon.
 * @param selectedRoute  Current bottom nav route — drives [AppBottomBar] highlight.
 * @param onNavSelected  Called when the user taps a bottom nav tab.
 */
@Composable
fun PlaceholderScreen(
    title: String,
    description: String,
    comingIn: String,
    iconRes: Int,
    selectedRoute: String,
    onNavSelected: (String) -> Unit = {}
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            AppBottomBar(
                selectedRoute = selectedRoute,
                onNavSelected = onNavSelected
            )
        }
    ) { innerPadding ->
        Box(
            modifier         = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Spacing.screenEdge),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // ── Illustration circle ────────────────────────────────────
                Box(
                    modifier         = Modifier
                        .size(ComponentSize.iconContainerHero)   // 100dp
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter            = painterResource(iconRes),
                        contentDescription = null,
                        tint               = MaterialTheme.colorScheme.primary,
                        modifier           = Modifier.size(IconSize.xl)   // 40dp
                    )
                }

                Spacer(Modifier.height(Spacing.xxl))

                // ── Title ──────────────────────────────────────────────────
                Text(
                    text       = title,
                    style      = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color      = MaterialTheme.colorScheme.onBackground,
                    textAlign  = TextAlign.Center
                )

                Spacer(Modifier.height(Spacing.sm))

                // ── Description ────────────────────────────────────────────
                Text(
                    text      = description,
                    style     = MaterialTheme.typography.bodyMedium,
                    color     = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(Spacing.xl))

                // ── Coming in badge ────────────────────────────────────────
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = MaterialTheme.shapes.extraSmall   // pill
                        )
                        .padding(horizontal = Spacing.lg, vertical = Spacing.sm)
                ) {
                    Text(
                        text       = "Coming in $comingIn",
                        style      = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color      = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Leaderboard placeholder — light")
@Composable
private fun LeaderboardPlaceholderPreview() {
    TriviaSparksTheme(darkTheme = false) {
        PlaceholderScreen(
            title         = "Leaderboard",
            description   = "See how you rank against players worldwide.",
            comingIn      = "Week 9",
            iconRes       = R.drawable.ic_trophy,
            selectedRoute = "leaderboard"
        )
    }
}

@Preview(showBackground = true, name = "History placeholder — light")
@Composable
private fun HistoryPlaceholderPreview() {
    TriviaSparksTheme(darkTheme = false) {
        PlaceholderScreen(
            title         = "History",
            description   = "Review all your past quizzes and scores.",
            comingIn      = "Week 7",
            iconRes       = R.drawable.ic_clock,
            selectedRoute = "history"
        )
    }
}

@Preview(showBackground = true, name = "Profile placeholder — light")
@Composable
private fun ProfilePlaceholderPreview() {
    TriviaSparksTheme(darkTheme = false) {
        PlaceholderScreen(
            title         = "Profile",
            description   = "Manage your account, stats and preferences.",
            comingIn      = "Week 8",
            iconRes       = R.drawable.ic_flame,
            selectedRoute = "profile"
        )
    }
}

@Preview(showBackground = true, name = "Leaderboard placeholder — dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LeaderboardPlaceholderDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        PlaceholderScreen(
            title         = "Leaderboard",
            description   = "See how you rank against players worldwide.",
            comingIn      = "Week 9",
            iconRes       = R.drawable.ic_trophy,
            selectedRoute = "leaderboard"
        )
    }
}