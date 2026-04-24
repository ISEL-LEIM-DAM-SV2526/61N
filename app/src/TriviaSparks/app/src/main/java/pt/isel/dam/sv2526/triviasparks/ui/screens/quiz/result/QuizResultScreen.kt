package pt.isel.dam.sv2526.triviasparks.ui.screens.quiz.result

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.component.StatBox
import pt.isel.dam.sv2526.triviasparks.ui.model.QuestionReview
import pt.isel.dam.sv2526.triviasparks.ui.model.QuizResultSummary
import pt.isel.dam.sv2526.triviasparks.ui.preview.sampleReview
import pt.isel.dam.sv2526.triviasparks.ui.preview.sampleSummary
import pt.isel.dam.sv2526.triviasparks.ui.theme.BottomSheetShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ButtonShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme
import pt.isel.dam.sv2526.triviasparks.ui.theme.Violet800
import pt.isel.dam.sv2526.triviasparks.ui.theme.triviasparks

// ─────────────────────────────────────────────────────────────────────────────
// STAR RATING THRESHOLDS
// Game logic constants — not sizing tokens, so they live here not in the theme.
// ─────────────────────────────────────────────────────────────────────────────

private const val STARS_TOTAL = 3   // total stars in the rating display
private const val STARS_THREE = 80  // score % threshold for 3 stars
private const val STARS_TWO = 50  // score % threshold for 2 stars

/**
 * Quiz results screen — shows the final score, a star rating, key stats,
 * and a full review of every question answered.
 *
 * Layout pattern:
 * ```
 * Scaffold → Column →
 *   ResultsTopBar    (close left, title centre, share right)
 *   ResultsHero      (fixed heroHeight — violet bg, trophy, score, stars)
 *   ResultsCard      (weight 1f, overlaps hero, scrollable review list)
 *   TryAgainButton   (pinned below card)
 * ```
 *
 * The card uses [BottomSheetShape] and [ResultsDefaults.cardOverlap] — same
 * overlap pattern as [QuizDetailScreen].
 *
 * **What is static this week and when it becomes live:**
 *
 * | Element | Static value | Becomes live |
 * |---|---|---|
 * | `summary` | `sampleSummary` | Week 5 — `QuizViewModel.uiState` result |
 * | `questions` | `sampleReview` | Week 5 — `QuizViewModel.questionReviews` |
 * | `onClose` | empty lambda | Week 4 — `NavController.popBackStack()` |
 * | `onTryAgain` | empty lambda | Week 4 — navigate back to `QuizScreen` |
 * | `onShare` | empty lambda | Week 8 — Android `ShareCompat` intent |
 *
 * Figma design:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 *
 * Wiki — ResultsScreen section:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/02-%E2%80%90-Jetpack-Compose-%E2%80%90-Compose-Fundamentals#resultsscreen
 *
 * @param summary    Quiz score summary for the hero section.
 * @param questions  Ordered list of question review entries.
 * @param onClose    Called when the user taps the X button.
 *                   TODO(Week 4): `NavController.popBackStack()`.
 * @param onTryAgain Called when the user taps Try Again.
 *                   TODO(Week 4): navigate back to `QuizScreen` with same args.
 * @param onShare    Called when the user taps the share icon.
 *                   TODO(Week 8): launch Android `ShareCompat` intent.
 */
@Composable
fun ResultsScreen(
    summary: QuizResultSummary = sampleSummary,
    questions: List<QuestionReview> = sampleReview,
    onClose: () -> Unit = {},    // TODO(Week 4): NavController.popBackStack()
    onTryAgain: () -> Unit = {},    // TODO(Week 4): navigate to QuizScreen
    onShare: () -> Unit = {}     // TODO(Week 8): ShareCompat intent
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // ── Top bar ────────────────────────────────────────────────────
            ResultsTopBar(
                onClose = onClose,
                onShare = onShare,
                modifier = Modifier.padding(
                    horizontal = Spacing.screenEdge,
                    vertical = Spacing.lg
                )
            )

            // ── Hero — violet background, score, stars ─────────────────────
            ResultsHero(
                summary = summary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ComponentSize.resultHeroHeight)   // 280dp
            )

            // ── Results card — overlaps hero, scrollable question review ───
            // offset(y = -cardOverlap) slides the card up over the hero bottom edge.
            // Never use negative padding — Compose throws IllegalArgumentException.
            ResultsCard(
                summary = summary,
                questions = questions,
                modifier = Modifier
                    .weight(1f)
                    .offset(y = -Spacing.xl)                  // Spacing.xl = 20dp — slides card over hero
                    .padding(horizontal = Spacing.screenEdge)
            )

            // ── Try Again — pinned below the card ──────────────────────────
            TryAgainButton(
                onClick = onTryAgain,
                modifier = Modifier.padding(
                    horizontal = Spacing.screenEdge,
                    vertical = Spacing.xl
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// TOP BAR
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Custom top bar — X button left, "Quiz Complete" centred, share icon right.
 *
 * Uses a plain [Row] instead of [androidx.compose.material3.TopAppBar] because
 * the share icon on the right doesn't fit the standard navigation structure.
 *
 * @param onClose   Called when the user taps the X button.
 *                  TODO(Week 4): `NavController.popBackStack()`.
 * @param onShare   Called when the user taps the share icon.
 *                  TODO(Week 8): launch share intent.
 * @param modifier  Applied to the outermost [Row] element.
 */
@Composable
private fun ResultsTopBar(
    onClose: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // X button — same size as CategoryScreen close button
        IconButton(
            onClick = onClose,
            modifier = Modifier.size(ComponentSize.close)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_close),
                contentDescription = "Close results",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(IconSize.xxs)
            )
        }

        // "Quiz Complete" — weight(1f) centres between the two fixed-size buttons
        Text(
            text = "Quiz Complete",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        // Share icon — right side
        IconButton(
            onClick = onShare,     // TODO(Week 8): ShareCompat intent
            modifier = Modifier.size(ComponentSize.close)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = "Share results",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(IconSize.md)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// HERO SECTION
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Violet hero area — trophy icon, score, percentage label, star rating.
 *
 * Background is `colorScheme.primary` (Violet400). All text and icons
 * are white or `colorScheme.secondary` (coral) for the percentage label.
 *
 * **Star rating logic:**
 * - ≥ [ResultsDefaults.threeStars]% → 3 stars
 * - ≥ [ResultsDefaults.twoStars]% → 2 stars
 * - < [ResultsDefaults.twoStars]% → 1 star
 * Filled stars use [SunnyYellow]. Empty stars use white at 40% opacity.
 * @param summary   Score summary — drives the number, percentage, and star count.
 * @param modifier  Applied to the outermost [Box] — caller sets width and height.
 */
@Composable
private fun ResultsHero(
    summary: QuizResultSummary,
    modifier: Modifier = Modifier
) {
    // Compute star count from score percentage
    val starCount = when {
        summary.scorePercentage >= STARS_THREE -> 3
        summary.scorePercentage >= STARS_TWO -> 2
        else -> 1
    }

    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            // Trophy icon — ComponentSize.avatarHero (80dp) circle
            Box(
                modifier = Modifier
                    .size(ComponentSize.avatarHero)   // 80dp — reuses avatar hero size
                    .background(
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.20f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_trophy),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(IconSize.xl)
                )
            }

            // Score number — onPrimary (white on violet)
            Text(
                text = "${summary.totalScore}",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            // Percentage label — secondary (coral)
            Text(
                text = "${summary.scorePercentage}% SCORE",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            // Star rating
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(STARS_TOTAL) { index ->
                    val filled = index < starCount
                    Icon(
                        painter = painterResource(
                            if (filled) R.drawable.ic_rating_fill else R.drawable.ic_rating_unfill
                        ),
                        contentDescription = null,
                        tint = if (filled) MaterialTheme.triviasparks.categoryHistory
                        else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.40f),
                        modifier = Modifier.size(IconSize.lg)  // 32dp — reuses IconSize.lg
                    )
                }
            }
        }
    }
}

@Composable
private fun ResultsCard(
    summary: QuizResultSummary,
    questions: List<QuestionReview>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = BottomSheetShape,
        color = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                top = Spacing.xxl,
                bottom = Spacing.lg
            )
        ) {
            // ── Stats row ──────────────────────────────────────────────────
            item {
                ResultsStatsRow(
                    summary = summary,
                    modifier = Modifier.padding(horizontal = Spacing.screenEdge)
                )
            }

            // ── Divider ────────────────────────────────────────────────────
            item {
                Divider(
                    color = MaterialTheme.colorScheme.outlineVariant,
                    modifier = Modifier.padding(
                        horizontal = Spacing.screenEdge,
                        vertical = Spacing.xl
                    )
                )
            }

            // ── Question review list ───────────────────────────────────────
            // TODO(Week 5): replaced by ViewModel-provided question review list
            itemsIndexed(
                items = questions,
                key = { _, q -> q.number }
            ) { index, question ->
                QuestionReviewItem(
                    question = question,
                    modifier = Modifier.padding(horizontal = Spacing.screenEdge)
                )
                // Divider between items, not after the last one
                if (index < questions.lastIndex) {
                    Divider(
                        color = MaterialTheme.colorScheme.outlineVariant,
                        modifier = Modifier.padding(horizontal = Spacing.screenEdge)
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// STATS ROW
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Row of three [StatBox] items — Correct, Wrong, and Time.
 *
 * Each box uses its own semantic colour from the Dreamscape token set:
 * - Correct → `colorScheme.tertiary` (mint green / easy difficulty colour)
 * - Wrong   → `colorScheme.error` (strawberry / hard difficulty colour)
 * - Time    → `colorScheme.primary` (violet)
 *
 * @param summary   Provides the three stat values.
 * @param modifier  Applied to the outermost [Row] element.
 */
@Composable
private fun ResultsStatsRow(
    summary: QuizResultSummary,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Correct answers — mint green
        StatBox(
            iconRes = R.drawable.ic_correct,
            colour = MaterialTheme.colorScheme.tertiary,
            value = "${summary.correctCount}",
            label = "CORRECT",
            modifier = Modifier.weight(1f)
        )
        // Wrong answers — coral/error
        StatBox(
            iconRes = R.drawable.ic_incorrect,
            colour = MaterialTheme.colorScheme.error,
            value = "${summary.wrongCount}",
            label = "WRONG",
            modifier = Modifier.weight(1f)
        )
        // Time — violet/primary
        StatBox(
            iconRes = R.drawable.ic_clock,
            colour = MaterialTheme.colorScheme.primary,
            value = formatTime(summary.timeTakenSeconds),
            label = "TIME",
            modifier = Modifier.weight(1f)
        )
    }
}

// StatBox is a shared component — lives in ui/components/StatBox.kt
// Imported above: import pt.isel.dam.sv2526.triviasparks.ui.component.StatBox

// ─────────────────────────────────────────────────────────────────────────────
// QUESTION REVIEW ITEM
// ─────────────────────────────────────────────────────────────────────────────

/**
 * A single question review row — question number label, question text, and answer(s).
 *
 * **Answer display rules:**
 * - **Correct:** shows [QuestionReview.userAnswer] in `colorScheme.tertiary` (mint green).
 * - **Wrong:** shows [QuestionReview.userAnswer] in `colorScheme.error` (coral),
 *   then [QuestionReview.correctAnswer] in `colorScheme.tertiary` on the next line.
 *
 * The leading badge is a circle with a check (correct) or X (wrong).
 *
 * @param question  The question review data.
 * @param modifier  Applied to the outermost [Row] element.
 */
@Composable
private fun QuestionReviewItem(
    question: QuestionReview,
    modifier: Modifier = Modifier
) {
    val badgeColour = if (question.isCorrect) MaterialTheme.colorScheme.tertiary
    else MaterialTheme.colorScheme.error

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.md),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Leading badge — check or X circle
        Box(
            modifier = Modifier
                .padding(top = Spacing.sm)
                .size(ComponentSize.reviewBadge)   // 28dp
                .background(color = badgeColour.copy(alpha = 0.15f), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(
                    if (question.isCorrect) R.drawable.ic_check
                    else R.drawable.ic_cross
                ),
                contentDescription = if (question.isCorrect) "Correct" else "Wrong",
                tint = badgeColour,
                modifier = Modifier.size(IconSize.xxxs)
            )
        }

        // Question number + text + answer(s)
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            // "QUESTION 01" label — labelSmall uppercase
            Text(
                text = "QUESTION ${question.number.toString().padStart(2, '0')}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Question text
            Text(
                text = question.text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (question.isCorrect) {
                // Correct — show user's answer in mint green
                Text(
                    text = question.userAnswer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            } else {
                // Wrong — show user's answer in coral, then correct answer in mint
                Text(
                    text = question.userAnswer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error   // wrong answer — coral
                )
                Text(
                    text = question.correctAnswer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary   // correct answer — mint
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// TRY AGAIN BUTTON
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Full-width CTA pinned to the bottom of the screen.
 *
 * Uses [Violet800] (`#3C3489`) — same deep CTA pattern as [CategoryScreen]
 * and [QuizDetailScreen]. Signals the final committed action on the screen.
 *
 * @param onClick   Called when the user taps the button.
 *                  TODO(Week 4): navigate back to `QuizScreen` with same category + difficulty.
 * @param modifier  Applied to the outermost [Button] element.
 */
@Composable
private fun TryAgainButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,     // TODO(Week 4): navigate to QuizScreen
        modifier = modifier
            .fillMaxWidth()
            .height(ComponentSize.buttonHeightLarge),
        shape = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Violet800,
            contentColor = Color.White
        )
    ) {
        Text(
            text = "Try Again",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// HELPERS
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Formats a duration in seconds as "m:ss" — e.g. 84 → "1:24s".
 *
 * TODO(Week 5): this logic moves to `QuizViewModel` or a domain use case.
 */
private fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "${m}:${s.toString().padStart(2, '0')}s"
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "ResultsScreen — light, 2 stars")
@Composable
private fun ResultsScreenLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        ResultsScreen()
    }
}

@Preview(
    showBackground = true,
    name = "ResultsScreen — dark, 2 stars",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ResultsScreenDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        ResultsScreen()
    }
}

@Preview(showBackground = true, name = "ResultsScreen — 3 stars, perfect score")
@Composable
private fun ResultsScreenPerfectPreview() {
    TriviaSparksTheme(darkTheme = false) {
        ResultsScreen(
            summary = QuizResultSummary(
                totalScore = 3000,
                scorePercentage = 100,
                correctCount = 20,
                wrongCount = 0,
                timeTakenSeconds = 55
            )
        )
    }
}

@Preview(showBackground = true, name = "ResultsScreen — 1 star, low score")
@Composable
private fun ResultsScreenLowScorePreview() {
    TriviaSparksTheme(darkTheme = false) {
        ResultsScreen(
            summary = QuizResultSummary(
                totalScore = 500,
                scorePercentage = 30,
                correctCount = 3,
                wrongCount = 17,
                timeTakenSeconds = 210
            )
        )
    }
}

@Preview(showBackground = true, name = "StatBox — Correct")
@Composable
private fun StatBoxPreview() {
    TriviaSparksTheme(darkTheme = false) {
        StatBox(
            iconRes = R.drawable.ic_correct,
            colour = MaterialTheme.colorScheme.tertiary,
            value = "17",
            label = "CORRECT",
            modifier = Modifier
                .width(100.dp)
                .padding(Spacing.md)
        )
    }
}