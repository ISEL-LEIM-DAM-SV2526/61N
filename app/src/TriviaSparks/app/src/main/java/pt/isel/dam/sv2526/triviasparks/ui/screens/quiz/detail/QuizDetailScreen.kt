package pt.isel.dam.sv2526.triviasparks.ui.screens.quiz.detail

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.model.DifficultyOption
import pt.isel.dam.sv2526.triviasparks.ui.component.DifficultyChip
import pt.isel.dam.sv2526.triviasparks.ui.component.InfoPill
import pt.isel.dam.sv2526.triviasparks.ui.theme.BottomSheetShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ButtonShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ChipShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme
import pt.isel.dam.sv2526.triviasparks.ui.theme.Violet800
import pt.isel.dam.sv2526.triviasparks.ui.theme.triviasparks

// ─────────────────────────────────────────────────────────────────────────────
// SCREEN-LEVEL CONSTANTS
// ─────────────────────────────────────────────────────────────────────────────

private object QuizDetailDefaults {
    val heroHeight      = 240.dp
    val heroCardOverlap = 20.dp
    val backButtonSize  = 40.dp
}

// ─────────────────────────────────────────────────────────────────────────────
// DATA MODEL — temporary until Week 4 nav args
// ─────────────────────────────────────────────────────────────────────────────

data class QuizDetail(
    val id: Int,
    val title: String,
    val category: String,
    val questionCount: Int,
    val xpReward: Int,
    val description: String,
    val difficulty: String
)

private val sampleQuizDetail = QuizDetail(
    id            = 1,
    title         = "Quantum Physics Fun",
    category      = "SCIENCE & NATURE",
    questionCount = 15,
    xpReward      = 250,
    description   = "Dive into the fascinating world of quantum mechanics! " +
            "Explore particles, waves, and the mysteries of the " +
            "subatomic world in this playful challenge.",
    difficulty    = "Easy"
)

/**
 * Pre-game detail screen — quiz info and difficulty selector.
 *
 * **Week 3 — state now live:**
 * `selectedDifficulty` is now `remember { mutableStateOf(quiz.difficulty) }`.
 * The player can switch chips before tapping Play Solo.
 *
 * **`remember(quiz.id)`:**
 * The key `quiz.id` ensures `selectedDifficulty` resets to `quiz.difficulty`
 * if a different quiz is shown — without it, the state would persist from
 * the previous quiz.
 *
 * **`onPlaySolo` now receives the difficulty:**
 * In Week 4 the NavGraph calls `Routes.quiz(categoryId, selectedDifficulty)`
 * using the value passed here. The screen doesn't need to know about routes —
 * it just reports what the player chose.
 *
 * **What is still static and when it becomes live:**
 *
 * | Element | Current state | Becomes live |
 * |---|---|---|
 * | `quiz` data | `sampleQuizDetail` | Week 4 — nav args |
 * | `onBack` | empty lambda | Week 4 — `NavController.popBackStack()` |
 * | `onPlaySolo` | empty lambda | Week 4 — navigate to `QuizScreen` |
 * | `onPlayWithFriends` | empty lambda | Week 11 — Multiplayer |
 *
 * Figma design:
 * https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=quiz-detail-screen
 *
 * Wiki — Week 3 QuizDetailScreen state:
 * https://github.com/your-username/trivia-sparks/wiki/Week-3-4#quizdetailscreen--difficulty-selection
 *
 * @param quiz                Quiz data. TODO(Week 4): from nav args.
 * @param onBack              Called when the user taps the back arrow.
 *                            TODO(Week 4): `NavController.popBackStack()`.
 * @param onPlaySolo          Called with the selected difficulty when the user taps Play Solo.
 *                            TODO(Week 4): `navController.navigate(Routes.quiz(id, difficulty))`.
 * @param onPlayWithFriends   Called when the user taps Play with Friends.
 *                            TODO(Week 11): navigate to Multiplayer lobby.
 */
@Composable
fun QuizDetailScreen(
    quiz: QuizDetail                          = sampleQuizDetail,  // TODO(Week 4): nav args
    onBack: () -> Unit                        = {},                 // TODO(Week 4): popBackStack()
    onPlaySolo: (difficulty: String) -> Unit  = {},                 // TODO(Week 4): navigate to QuizScreen
    onPlayWithFriends: () -> Unit             = {}                  // TODO(Week 11): Multiplayer
) {
    // ── Week 3 state ───────────────────────────────────────────────────────
    // remember(quiz.id) — resets to quiz.difficulty if a different quiz is shown.
    // Without the key, the state would persist from the previous quiz.
    var selectedDifficulty by remember(quiz.id) { mutableStateOf(quiz.difficulty) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            QuizDetailHero(
                onBack   = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(QuizDetailDefaults.heroHeight)
            )

            QuizDetailCard(
                quiz               = quiz,
                selectedDifficulty = selectedDifficulty,
                onDifficultySelected = { selectedDifficulty = it },
                modifier           = Modifier
                    .weight(1f)
                    .offset(y = -QuizDetailDefaults.heroCardOverlap)
            )

            QuizDetailButtons(
                onPlaySolo          = { onPlaySolo(selectedDifficulty) },
                onPlayWithFriends   = onPlayWithFriends,
                modifier            = Modifier.padding(
                    horizontal = Spacing.screenEdge,
                    vertical   = Spacing.xl
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// HERO
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Full-bleed illustrated hero. Back arrow as a ghost overlay.
 *
 * @param onBack    Called when the user taps the back arrow.
 * @param modifier  Caller sets width and height.
 */
@Composable
private fun QuizDetailHero(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter            = painterResource(R.drawable.il_science),
            contentDescription = "Quiz illustration",
            contentScale       = ContentScale.Crop,
            modifier           = Modifier.fillMaxSize()
        )
        IconButton(
            onClick  = onBack,
            modifier = Modifier
                .padding(Spacing.lg)
                .align(Alignment.TopStart)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.80f),
                    shape = RoundedCornerShape(50)
                )
                .size(QuizDetailDefaults.backButtonSize)
        ) {
            Icon(
                painter            = painterResource(R.drawable.ic_close),
                contentDescription = "Back",
                tint               = MaterialTheme.colorScheme.onSurface,
                modifier           = Modifier.size(IconSize.md)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// DETAIL CARD
// ─────────────────────────────────────────────────────────────────────────────

/**
 * White scrollable card panel with quiz metadata.
 *
 * [onDifficultySelected] is passed down through the card to [QuizDifficultySection]
 * — this is state hoisting in action. The card does not own state, it just
 * passes events upward to [QuizDetailScreen].
 *
 * @param quiz                  Quiz data to display.
 * @param selectedDifficulty    Currently selected chip label.
 * @param onDifficultySelected  Called when the player taps a chip.
 * @param modifier              Applied to the outermost [Surface].
 */
@Composable
private fun QuizDetailCard(
    quiz: QuizDetail,
    selectedDifficulty: String,
    onDifficultySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape    = BottomSheetShape,
        color    = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.screenEdge)
                .padding(top = Spacing.xxl, bottom = Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            Text(
                text  = quiz.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text       = quiz.title,
                style      = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color      = MaterialTheme.colorScheme.onSurface
            )
            QuizInfoPills(questionCount = quiz.questionCount, xpReward = quiz.xpReward)
            QuizAboutSection(description = quiz.description)
            QuizDifficultySection(
                selectedDifficulty   = selectedDifficulty,
                onDifficultySelected = onDifficultySelected
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// INFO PILLS
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun QuizInfoPills(
    questionCount: Int,
    xpReward: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier              = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        InfoPill(
            iconRes   = R.drawable.ic_questions,
            iconTint  = MaterialTheme.colorScheme.onSurfaceVariant,
            label     = "$questionCount Questions",
            pillColor = Color.Transparent,
            modifier  = Modifier.border(1.dp, MaterialTheme.colorScheme.outline, ChipShape)
        )
        InfoPill(
            iconRes   = R.drawable.ic_star_fill,
            iconTint  = MaterialTheme.colorScheme.secondary,
            label     = "$xpReward XP",
            pillColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
            modifier  = Modifier.border(
                1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f), ChipShape
            )
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ABOUT SECTION
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun QuizAboutSection(
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text  = "ABOUT THIS QUIZ",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text       = description,
            style      = MaterialTheme.typography.bodyMedium,
            color      = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = MaterialTheme.typography.bodyMedium.fontSize * 1.6f
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// DIFFICULTY SECTION
// ─────────────────────────────────────────────────────────────────────────────

/**
 * "DIFFICULTY" label and three [DifficultyChip]s.
 *
 * Stateless — receives [selectedDifficulty] and reports taps via [onDifficultySelected].
 * The state lives in [QuizDetailScreen].
 *
 * @param selectedDifficulty    Currently active chip label.
 * @param onDifficultySelected  Called when the player taps a chip.
 * @param modifier              Applied to the outermost [Column].
 */
@Composable
private fun QuizDifficultySection(
    selectedDifficulty: String,
    onDifficultySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val ext = MaterialTheme.triviasparks

    val difficultyOptions = listOf(
        DifficultyOption("Easy",   R.drawable.ic_star_fill, ext.easy,   ext.easyOnLight),
        DifficultyOption("Medium", R.drawable.ic_light,     ext.medium, ext.mediumOnLight),
        DifficultyOption("Hard",   R.drawable.ic_flame,     ext.hard,   ext.hardOnLight)
    )

    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text  = "DIFFICULTY",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(Spacing.sm)) {
            difficultyOptions.forEach { option ->
                DifficultyChip(
                    option     = option,
                    isSelected = option.label == selectedDifficulty,
                    onClick    = { onDifficultySelected(option.label) }
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ACTION BUTTONS
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Play Solo (outlined) and Play with Friends (filled) buttons.
 *
 * [onPlaySolo] is called from [QuizDetailScreen] as:
 * `onPlaySolo = { onPlaySolo(selectedDifficulty) }`
 * The difficulty is resolved before the lambda is invoked — this composable
 * doesn't need to know about state.
 */
@Composable
private fun QuizDetailButtons(
    onPlaySolo: () -> Unit,
    onPlayWithFriends: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        OutlinedButton(
            onClick  = onPlaySolo,
            modifier = Modifier.weight(1f).height(ComponentSize.buttonHeight),
            shape    = ButtonShape,
            border   = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
            colors   = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Play Solo", style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold)
        }

        Button(
            onClick  = onPlayWithFriends,
            modifier = Modifier.weight(1f).height(ComponentSize.buttonHeight),
            shape    = ButtonShape,
            colors   = ButtonDefaults.buttonColors(
                containerColor = Violet800,
                contentColor   = Color.White
            )
        ) {
            Icon(painterResource(R.drawable.ic_multiplayer), null,
                modifier = Modifier.size(IconSize.sm))
            Spacer(Modifier.width(Spacing.xs))
            Text(text = "Play with Friends", style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "QuizDetail — light, Easy")
@Composable
private fun QuizDetailLightPreview() {
    TriviaSparksTheme(darkTheme = false) { QuizDetailScreen() }
}

@Preview(showBackground = true, name = "QuizDetail — dark, Easy",
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuizDetailDarkPreview() {
    TriviaSparksTheme(darkTheme = true) { QuizDetailScreen() }
}

@Preview(showBackground = true, name = "QuizDetail — Hard selected")
@Composable
private fun QuizDetailHardPreview() {
    TriviaSparksTheme(darkTheme = false) {
        // Pass a quiz with difficulty = "Hard" to verify the chip pre-selects correctly
        QuizDetailScreen(quiz = sampleQuizDetail.copy(difficulty = "Hard"))
    }
}