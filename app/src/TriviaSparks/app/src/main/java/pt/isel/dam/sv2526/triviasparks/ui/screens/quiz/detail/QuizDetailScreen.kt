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
// Magic numbers extracted here — change once, updates everywhere in the file.
// ─────────────────────────────────────────────────────────────────────────────

private object QuizDetailDefaults {
    val heroHeight      = 240.dp  // full-bleed illustration area at the top
    val heroCardOverlap = 20.dp   // negative y-offset — card slides up over the hero
    val backButtonSize  = 40.dp   // ghost back button touch target in the hero
}

// ─────────────────────────────────────────────────────────────────────────────
// DATA MODEL
// Temporary — moved to domain/model/ in Week 5 when ViewModel is introduced.
// Wiki: https://github.com/your-username/trivia-sparks/wiki/Week-5
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Represents the full detail of a quiz shown on [QuizDetailScreen].
 *
 * Temporary UI model — lives in the screen package until Week 4/5.
 * In Week 4 it is populated from nav args passed from [CategoryScreen].
 * In Week 6 it is replaced by the domain Quiz model from the Open Trivia API.
 *
 * Wiki — data models reference:
 * https://github.com/your-username/trivia-sparks/wiki/App-Trivia-Sparks#data-models
 */
data class QuizDetail(
    val id: Int,             // Quiz ID — passed as nav arg in Week 4
    val title: String,       // e.g. "Quantum Physics Fun"
    val category: String,    // Uppercase label, e.g. "SCIENCE & NATURE"
    val questionCount: Int,  // Total questions available
    val xpReward: Int,       // XP awarded on completion
    val description: String, // "About this quiz" body text
    val difficulty: String   // "Easy" | "Medium" | "Hard" — default selected chip
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

// ─────────────────────────────────────────────────────────────────────────────
// QUIZ DETAIL SCREEN
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Pre-game detail screen — shows quiz metadata and lets the player pick a difficulty.
 *
 * Layout pattern:
 * ```
 * Scaffold → Column →
 *   QuizDetailHero    (fixed heroHeight, full bleed illustration)
 *   QuizDetailCard    (weight 1f, scrollable, overlaps hero by heroCardOverlap)
 *   QuizDetailButtons (pinned below the card, always visible)
 * ```
 *
 * **What is static this week and when it becomes live:**
 *
 * | Element | Static value | Becomes live |
 * |---|---|---|
 * | `quiz` data | `sampleQuizDetail` | Week 4 — `NavController` arguments |
 * | `selectedDifficulty` | `quiz.difficulty` | Week 3 — `mutableStateOf` hoisted here |
 * | `onBack` | empty lambda | Week 4 — `NavController.popBackStack()` |
 * | `onPlaySolo` | empty lambda | Week 4 — navigate to `QuizScreen` |
 * | `onPlayWithFriends` | empty lambda | Week 11 — Multiplayer lobby |
 *
 * Figma design:
 * https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=quiz-detail-screen
 *
 * Wiki — Week 2 QuizDetailScreen section:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#quizdetailscreen
 *
 * @param quiz                Quiz data to display.
 * @param selectedDifficulty  Currently selected difficulty chip label.
 *                            TODO(Week 3): becomes `remember { mutableStateOf(quiz.difficulty) }` here.
 * @param onBack              Called when the user taps the back arrow.
 *                            TODO(Week 4): `NavController.popBackStack()`.
 * @param onPlaySolo          Called when the user taps "Play Solo".
 *                            TODO(Week 4): navigate to `QuizScreen` with difficulty arg.
 * @param onPlayWithFriends   Called when the user taps "Play with Friends".
 *                            TODO(Week 11): navigate to Multiplayer lobby.
 */
@Composable
fun QuizDetailScreen(
    quiz: QuizDetail              = sampleQuizDetail,
    selectedDifficulty: String    = quiz.difficulty,    // TODO(Week 3): mutableStateOf hoisted here
    onBack: () -> Unit            = {},                 // TODO(Week 4): NavController.popBackStack()
    onPlaySolo: () -> Unit        = {},                 // TODO(Week 4): navigate to QuizScreen
    onPlayWithFriends: () -> Unit = {}                  // TODO(Week 11): navigate to Multiplayer lobby
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // ── Hero illustration ──────────────────────────────────────────
            QuizDetailHero(
                onBack   = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(QuizDetailDefaults.heroHeight)
            )

            // ── Detail card — slides up over the hero by heroCardOverlap ───
            QuizDetailCard(
                quiz               = quiz,
                selectedDifficulty = selectedDifficulty,
                modifier           = Modifier
                    .weight(1f)
                    .offset(y = -QuizDetailDefaults.heroCardOverlap)
            )

            // ── Action buttons — always visible below the card ─────────────
            QuizDetailButtons(
                onPlaySolo        = onPlaySolo,
                onPlayWithFriends = onPlayWithFriends,
                modifier          = Modifier.padding(
                    horizontal = Spacing.screenEdge,
                    vertical   = Spacing.xl
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// HERO SECTION
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Full-bleed illustrated hero at the top of the screen.
 *
 * The back arrow is a ghost [IconButton] overlay anchored to [Alignment.TopStart]
 * inside a [Box]. The semi-transparent surface background keeps it readable
 * over any illustration colour.
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=quiz-detail-hero
 *
 * @param onBack    Called when the user taps the back arrow.
 *                  TODO(Week 4): `NavController.popBackStack()`.
 * @param modifier  Applied to the outermost [Box] — caller sets width and height.
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

        // Ghost back button — 80% surface background keeps it readable over illustration
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
 * White scrollable card panel containing all quiz metadata.
 *
 * Uses [BottomSheetShape] (top 20dp corners, 0dp bottom) to create the visual
 * overlap with the hero illustration. The caller applies a negative y-offset
 * of [QuizDetailDefaults.heroCardOverlap] to achieve this effect.
 *
 * @param quiz                Quiz data to display.
 * @param selectedDifficulty  Currently selected difficulty label — drives chip highlight.
 * @param modifier            Applied to the outermost [Surface] element.
 */
@Composable
private fun QuizDetailCard(
    quiz: QuizDetail,
    selectedDifficulty: String,
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
            // Category label — uppercase, primary colour
            Text(
                text  = quiz.category,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )

            // Quiz title
            Text(
                text       = quiz.title,
                style      = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color      = MaterialTheme.colorScheme.onSurface
            )

            // Question count + XP reward pills
            QuizInfoPills(
                questionCount = quiz.questionCount,
                xpReward      = quiz.xpReward
            )

            // About section
            QuizAboutSection(description = quiz.description)

            // Difficulty selector — selectedDifficulty flows all the way here
            QuizDifficultySection(selectedDifficulty = selectedDifficulty)
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// INFO PILLS
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Row of two [InfoPill]s: question count (outline) and XP reward (coral tint).
 *
 * - Question count: transparent fill + `outline` border — neutral, informational.
 * - XP reward: `secondary` at 15% fill + 50% border — warm, signals reward.
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=quiz-info-pills
 *
 * @param questionCount  Number of questions in the quiz.
 * @param xpReward       XP awarded on completion.
 * @param modifier       Applied to the outermost [Row] element.
 */
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
        // Question count — outline style, neutral visual weight
        InfoPill(
            iconRes   = R.drawable.ic_questions,
            iconTint  = MaterialTheme.colorScheme.onSurfaceVariant,
            label     = "$questionCount Questions",
            pillColor = Color.Transparent,
            modifier  = Modifier.border(1.dp, MaterialTheme.colorScheme.outline, ChipShape)
        )

        // XP reward — coral tint, signals reward value
        InfoPill(
            iconRes   = R.drawable.ic_star_fill,
            iconTint  = MaterialTheme.colorScheme.secondary,
            label     = "$xpReward XP",
            pillColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
            modifier  = Modifier.border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                shape = ChipShape
            )
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ABOUT SECTION
// ─────────────────────────────────────────────────────────────────────────────

/**
 * "ABOUT THIS QUIZ" uppercase label and body description.
 *
 * Relaxed `lineHeight` (1.6× the font size) for comfortable multi-line reading.
 *
 * @param description  The quiz description text.
 * @param modifier     Applied to the outermost [Column] element.
 */
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
 * "DIFFICULTY" label and three [DifficultyChip]s in a horizontal [Row].
 *
 * All three difficulties are always shown. [selectedDifficulty] determines
 * which chip is highlighted. Chips wrap their content — no `weight(1f)` —
 * giving a compact pill style suited to the detail screen's limited vertical space.
 *
 * Tapping does nothing this week.
 * TODO(Week 3): `onClick` becomes `{ selectedDifficulty = option.label }` with
 * `mutableStateOf` hoisted to [QuizDetailScreen].
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=quiz-detail-difficulty
 *
 * Wiki — difficulty chips:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#difficulty-chips--three-values-per-chip
 *
 * @param selectedDifficulty  Label of the highlighted chip — "Easy", "Medium", or "Hard".
 *                            TODO(Week 3): driven by `mutableStateOf` in parent screen.
 * @param modifier            Applied to the outermost [Column] element.
 */
@Composable
private fun QuizDifficultySection(
    selectedDifficulty: String,
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
                    onClick    = { /* TODO(Week 3): selectedDifficulty = option.label */ }
                    // No weight(1f) — chips wrap content, compact pill style
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// ACTION BUTTONS
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Row of two action buttons — "Play Solo" (outlined) and "Play with Friends" (filled).
 *
 * Different emphasis levels — see the Wiki link below.
 * Both buttons share equal width via [Modifier.weight(1f)].
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=quiz-detail-buttons
 *
 * Wiki — button emphasis explained:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#quizdetailscreen
 *
 * @param onPlaySolo          Called when the user taps "Play Solo".
 *                            TODO(Week 4): navigate to `QuizScreen` with difficulty arg.
 * @param onPlayWithFriends   Called when the user taps "Play with Friends".
 *                            TODO(Week 11): navigate to Multiplayer lobby.
 * @param modifier            Applied to the outermost [Row] element.
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
        // Play Solo — outlined, lower emphasis
        OutlinedButton(
            onClick  = onPlaySolo,
            modifier = Modifier
                .weight(1f)
                .height(ComponentSize.buttonHeight),   // 52dp
            shape  = ButtonShape,
            border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text       = "Play Solo",
                style      = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }

        // Play with Friends — filled, highest emphasis, Violet800 deep CTA
        Button(
            onClick  = onPlayWithFriends,
            modifier = Modifier
                .weight(1f)
                .height(ComponentSize.buttonHeight),   // 52dp
            shape  = ButtonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Violet800,   // deeper than primary — strong CTA
                contentColor   = Color.White
            )
        ) {
            Icon(
                painter            = painterResource(R.drawable.ic_multiplayer),
                contentDescription = null,
                modifier           = Modifier.size(IconSize.sm)
            )
            Spacer(modifier = Modifier.width(Spacing.xs))
            Text(
                text       = "Play with Friends",
                style      = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "QuizDetailScreen — light, Easy selected")
@Composable
private fun QuizDetailScreenLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        QuizDetailScreen()
    }
}

@Preview(
    showBackground = true,
    name           = "QuizDetailScreen — dark, Easy selected",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun QuizDetailScreenDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        QuizDetailScreen()
    }
}

@Preview(showBackground = true, name = "QuizDetailScreen — Hard selected")
@Composable
private fun QuizDetailScreenHardPreview() {
    TriviaSparksTheme(darkTheme = false) {
        QuizDetailScreen(selectedDifficulty = "Hard")
    }
}

@Preview(showBackground = true, name = "QuizInfoPills")
@Composable
private fun QuizInfoPillsPreview() {
    TriviaSparksTheme(darkTheme = false) {
        QuizInfoPills(
            questionCount = 15,
            xpReward      = 250,
            modifier      = Modifier.padding(Spacing.screenEdge)
        )
    }
}