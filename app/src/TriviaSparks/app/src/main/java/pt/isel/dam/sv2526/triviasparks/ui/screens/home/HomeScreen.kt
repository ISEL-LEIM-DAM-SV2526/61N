package pt.isel.dam.sv2526.triviasparks.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.model.Friend
import pt.isel.dam.sv2526.triviasparks.ui.model.QuizItem
import pt.isel.dam.sv2526.triviasparks.ui.component.AppBottomBar
import pt.isel.dam.sv2526.triviasparks.ui.component.AvatarCard
import pt.isel.dam.sv2526.triviasparks.ui.component.InfoPill
import pt.isel.dam.sv2526.triviasparks.ui.component.ListItemCard
import pt.isel.dam.sv2526.triviasparks.ui.component.SectionHeader
import pt.isel.dam.sv2526.triviasparks.ui.preview.sampleFriends
import pt.isel.dam.sv2526.triviasparks.ui.preview.sampleQuizItems
import pt.isel.dam.sv2526.triviasparks.ui.theme.AvatarShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ButtonShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

// ─────────────────────────────────────────────────────────────────────────────
// HOME SCREEN
// ─────────────────────────────────────────────────────────────────────────────

/**
 * The app's main screen after login.
 *
 * Displays the user's greeting, score and rank pills, a quick-start button,
 * a horizontal friends row, and a vertical list of latest quizzes.
 *
 * **What is static this week and when it becomes live:**
 *
 * | Element | Static value | Becomes live |
 * |---|---|---|
 * | `userName` | `"Alex"` | Week 8 — Firebase Auth `currentUser.displayName` |
 * | `totalScore` | `"2,450"` | Week 9 — Firestore user document |
 * | `rank` | `"#12"` | Week 9 — Firestore leaderboard |
 * | `friends` | `sampleFriends` mock | Week 9 — Firestore friends collection |
 * | `quizItems` | `mock/quizItems` | Week 6 — Open Trivia Database API |
 * | Bottom nav taps | empty lambdas | Week 4 — `NavController.navigateTab()` |
 * | Start Quiz tap | empty lambda | Week 4 — `NavController.navigate(Routes.CATEGORY)` |
 *
 * Figma design:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 *
 * Wiki — HomeScreen section:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/02-%E2%80%90-Jetpack-Compose-%E2%80%90-Compose-Fundamentals#homescreen
 *
 * @param userName              Display name shown in the greeting.
 * @param totalScore            Formatted score string, e.g. "2,450".
 * @param rank                  Formatted rank string, e.g. "#12".
 * @param friends               List of friends shown in the Recent Friends row.
 * @param onStartQuiz           Called when the user taps Start Quiz.
 * @param onSeeAllFriends       Called when the user taps "See all" above friends.
 * @param onSeeAllQuizzes       Called when the user taps "See all" above quizzes.
 * @param onQuizClick           Called when the user taps a quiz card. Receives the quiz ID.
 * @param onNotificationClick   Called when the user taps the notification bell.
 * @param onNavSelected         Called when the user taps a bottom navigation tab.
 *                              Receives the route string. Wired to [NavController] in Week 4.
 */
@Composable
fun HomeScreen(
    userName: String                = "Alex",       // TODO(Week 8): Firebase Auth currentUser.displayName
    totalScore: String              = "2,450",      // TODO(Week 9): Firestore user.totalScore
    rank: String                    = "#12",        // TODO(Week 9): Firestore leaderboard rank
    friends: List<Friend>           = sampleFriends,// TODO(Week 9): Firestore friends collection
    quizItems: List<QuizItem>       = sampleQuizItems, // TODO(Week 9): Firestore quizzes collection
    onStartQuiz: () -> Unit         = {},           // TODO(Week 4): navigate to CategoryScreen
    onSeeAllFriends: () -> Unit     = {},           // TODO(Week 4): navigate to friends list screen
    onSeeAllQuizzes: () -> Unit     = {},           // TODO(Week 4): navigate to quizzes list screen
    onQuizClick: (String) -> Unit   = {},           // TODO(Week 4): navigate to QuizDetailScreen
    onNotificationClick: () -> Unit = {},           // TODO(Week 8): open notifications screen
    onNavSelected: (String) -> Unit = {}            // TODO(Week 4): NavController.navigateTab()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            AppBottomBar(
                selectedRoute = "home",             // TODO(Week 4): driven by NavController currentRoute
                onNavSelected = onNavSelected
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier       = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = Spacing.xxl)
        ) {

            // ── Top bar ────────────────────────────────────────────────────
            item {
                HomeTopBar(
                    userAvatarRes       = R.drawable.ic_avatar1, // TODO(Week 8): Coil AsyncImage from Firebase URL
                    onNotificationClick = onNotificationClick
                )
            }

            // ── Greeting ───────────────────────────────────────────────────
            item {
                HomeGreeting(
                    userName = userName,
                    modifier = Modifier.padding(
                        horizontal = Spacing.screenEdge,
                        vertical   = Spacing.xl
                    )
                )
            }

            // ── Stat pills ─────────────────────────────────────────────────
            item {
                HomeStatRow(
                    score    = totalScore,
                    rank     = rank,
                    modifier = Modifier.padding(horizontal = Spacing.screenEdge)
                )
            }

            // ── Start Quiz button ──────────────────────────────────────────
            item {
                StartQuizButton(
                    onClick  = onStartQuiz,
                    modifier = Modifier
                        .padding(horizontal = Spacing.screenEdge)
                        .padding(top = Spacing.xl)
                )
            }

            // ── Recent Friends ─────────────────────────────────────────────
            item {
                SectionHeader(
                    title    = "Recent Friends",
                    onSeeAll = onSeeAllFriends,
                    modifier = Modifier.padding(
                        horizontal = Spacing.screenEdge,
                        vertical   = Spacing.xxl
                    )
                )
            }
            item {
                FriendsRow(
                    friends  = friends,
                    modifier = Modifier.padding(bottom = Spacing.sm)
                )
            }

            // ── Latest Quizzes ─────────────────────────────────────────────
            item {
                SectionHeader(
                    title    = "Latest Quizzes",
                    onSeeAll = onSeeAllQuizzes,
                    modifier = Modifier.padding(
                        horizontal = Spacing.screenEdge,
                        vertical   = Spacing.xxl
                    )
                )
            }

            // TODO(Week 6): replace mock quizItems with Open Trivia Database API data
            items(quizItems) { quiz ->
                ListItemCard(
                    title    = quiz.title,
                    subtitle = quiz.subtitle,
                    iconRes  = quiz.iconRes,
                    iconTint = quiz.iconTint,
                    onClick  = { onQuizClick(quiz.id) },
                    modifier = Modifier
                        .padding(horizontal = Spacing.screenEdge)
                        .padding(bottom = Spacing.cardGap)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// TOP BAR
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Custom top bar — avatar left, "Trivia Sparks" centred, notification bell right.
 *
 * Uses a plain [Row] instead of [androidx.compose.material3.TopAppBar] because
 * `TopAppBar` imposes a slot structure that doesn't match this layout.
 * [Modifier.weight(1f)] on the title centres it between the two fixed-size ends.
 *
 * The notification bell sits on a [CircleShape] `surfaceVariant` background —
 * gives it a subtle tappable area without a visible border.

 * @param userAvatarRes         Drawable resource ID for the user's avatar.
 *                              TODO(Week 8): replaced with Coil [AsyncImage] loading from Firebase URL.
 * @param onNotificationClick   Called when the user taps the notification bell.
 * @param modifier              Applied to the outermost [Row] element.
 */
@Composable
private fun HomeTopBar(
    userAvatarRes: Int,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier          = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.screenEdge)
            .padding(top = Spacing.xxl, bottom = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // clip() FIRST — establishes the circle shape before the image is drawn
        Image(
            painter            = painterResource(userAvatarRes),
            contentDescription = stringResource(R.string.home_top_bar_avatar_image_cd),
            modifier           = Modifier
                .size(ComponentSize.avatarMedium)
                .clip(AvatarShape)
        )

        // weight(1f) + TextAlign.Center — centres the title between two fixed-size ends
        Text(
            text       = stringResource(R.string.app_name),
            style      = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color      = MaterialTheme.colorScheme.primary,
            modifier   = Modifier.weight(1f),
            textAlign  = TextAlign.Center
        )

        // surfaceVariant circle gives the button a tap area without a border
        IconButton(
            onClick  = onNotificationClick,
            modifier = Modifier
                .size(ComponentSize.avatarMedium)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = CircleShape
                )
        ) {
            Icon(
                painter            = painterResource(R.drawable.ic_bell),
                contentDescription = stringResource(R.string.home_top_bar_notification_icon_cd),
                tint               = MaterialTheme.colorScheme.primary,
                modifier           = Modifier.size(IconSize.md)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// GREETING
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Two-line greeting below the top bar.
 *
 * - Line 1: "Hello, {userName}!" — `headlineMedium` Bold, `onBackground`
 * - Line 2: subtitle — `bodyMedium`, `onSurfaceVariant`
 *
 * @param userName  The user's display name interpolated into the greeting.
 *                  TODO(Week 8): comes from Firebase Auth `currentUser.displayName`.
 * @param modifier  Applied to the outermost [Column] element.
 */
@Composable
private fun HomeGreeting(
    userName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Text(
            text       = "Hello, $userName!",
            style      = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color      = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text  = "Ready to boost your score?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// STAT PILLS ROW
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Two [InfoPill]s showing score (coral tint) and rank (lavender tint).
 *
 * The two pills use different tints intentionally — see the Wiki link below.
 *
 * @param score     Formatted score string, e.g. "2,450".
 *                  TODO(Week 9): comes from Firestore `users/{uid}.totalScore`.
 * @param rank      Formatted rank string, e.g. "#12".
 *                  TODO(Week 9): calculated server-side from Firestore leaderboard.
 * @param modifier  Applied to the outermost [Row] element.
 */
@Composable
private fun HomeStatRow(
    score: String,
    rank: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Score — coral wash (secondary at 12%) draws the eye first
        InfoPill(
            modifier  = Modifier.weight(1f),
            iconRes   = R.drawable.ic_trophy,
            iconTint  = MaterialTheme.colorScheme.secondary,
            label     = score,
            pillColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f)
        )
        // Rank — primaryContainer (lavender) — neutral secondary information
        InfoPill(
            modifier  = Modifier.weight(1f),
            iconRes   = R.drawable.ic_crown,
            iconTint  = MaterialTheme.colorScheme.primary,
            label     = rank,
            pillColor = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// START QUIZ BUTTON
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Full-width primary CTA — launches the quiz category selection.
 *
 * Uses `colorScheme.primary` fill and [ButtonShape] (16dp).
 * Rocket icon sits to the right of the label inside the button content slot.
 *
 * @param onClick   Called when the user taps the button.
 *                  TODO(Week 4): navigates to `CategoryScreen` via `NavController`.
 * @param modifier  Applied to the outermost [Button] element.
 */
@Composable
private fun StartQuizButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick  = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(ComponentSize.buttonHeightLarge),
        shape  = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor   = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text       = "Start Quiz",
            style      = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(Spacing.sm))
        Icon(
            painter            = painterResource(R.drawable.ic_spaceship),
            contentDescription = null,
            modifier           = Modifier.size(IconSize.sm)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// FRIENDS ROW
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Horizontally scrollable row of [AvatarCard] items.
 *
 * [contentPadding] adds breathing room at both scroll ends without clipping
 * the first or last card against the screen edge.
 *
 * @param friends   List of friends to display.
 *                  TODO(Week 9): populated from Firestore friends collection.
 * @param modifier  Applied to the outermost [LazyRow] element.
 */
@Composable
private fun FriendsRow(
    friends: List<Friend>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier              = modifier,
        contentPadding        = PaddingValues(horizontal = Spacing.screenEdge),
        horizontalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        items(friends) { friend ->
            AvatarCard(
                image    = friend.avatarRes,
                title    = friend.name,
                subtitle = friend.score
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "HomeScreen — light")
@Composable
private fun HomeScreenLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        HomeScreen()
    }
}

@Preview(
    showBackground = true,
    name           = "HomeScreen — dark",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeScreenDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        HomeScreen()
    }
}