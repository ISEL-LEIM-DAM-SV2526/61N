package pt.isel.dam.sv2526.triviasparks.ui.screens.quiz.game

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import pt.isel.dam.sv2526.triviasparks.ui.component.AnimatedTimerRing
import pt.isel.dam.sv2526.triviasparks.ui.component.AnswerOptionCard
import pt.isel.dam.sv2526.triviasparks.ui.model.AnswerOption
import pt.isel.dam.sv2526.triviasparks.ui.preview.sampleAnswers
import pt.isel.dam.sv2526.triviasparks.ui.theme.ButtonShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.CardShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ChipShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * Active quiz screen — one question at a time, countdown timer, four answer
 * options, and a running score.
 *
 * Layout pattern: [Scaffold] → [Column] → [QuizTopBar] + [QuizCard] (`weight(1f)`)
 * + [NextQuestionButton]. The card fills all space between the top bar and the button.
 *
 * **Animation infrastructure — built in Week 2, fires in Week 3:**
 * [AnimatedTimerRing] and [AnswerOptionCard] contain all `animate*AsState` calls.
 * They fire automatically when [timeLeft] decrements and [selectedAnswer] changes.
 * No modifications to this file are needed in Week 3.
 *
 * **What is static this week and when it becomes live:**
 *
 * | Element | Static value | Becomes live |
 * |---|---|---|
 * | `timeLeft` | `12` hardcoded | Week 3 — `LaunchedEffect` ticking every second |
 * | `selectedAnswer` | `"B"` hardcoded | Week 3 — `mutableStateOf<String?>(null)` |
 * | `correctAnswer` | `"B"` hardcoded | Week 4 — from nav args / `Question` model |
 * | `questionText` | hardcoded string | Week 4 — from nav args |
 * | `answers` | `sampleAnswers` mock | Week 4 — from nav args |
 * | `score` | `240` hardcoded | Week 5 — `QuizViewModel.uiState` |
 * | `questionNumber` / `totalQuestions` | `3` / `10` hardcoded | Week 5 — `QuizViewModel` |
 * | `onClose` | empty lambda | Week 4 — `NavController.popBackStack()` |
 * | `onNextQuestion` | empty lambda | Week 5 — `QuizViewModel.nextQuestion()` |
 *
 * Figma design:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 *
 * Wiki — Week 2 QuizScreen section:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/02-%E2%80%90-Jetpack-Compose-%E2%80%90-Compose-Fundamentals#quizscreen
 *
 * @param timeLeft          Seconds remaining on the countdown. Range: 0–[totalTime].
 * @param totalTime         Total seconds per question. Defaults to 30.
 * @param questionNumber    1-based index of the current question.
 * @param totalQuestions    Total questions in the session.
 * @param questionText      The question text. HTML-decoded before reaching this screen.
 * @param answers           Four answer options in display order (A, B, C, D).
 * @param selectedAnswer    Letter the user tapped, or null if not yet answered.
 *                          TODO(Week 3): becomes `mutableStateOf<String?>(null)` here.
 * @param correctAnswer     Letter of the correct answer.
 *                          TODO(Week 4): comes from the [Question] domain model via nav args.
 * @param score             Running score shown in the top bar.
 *                          TODO(Week 5): comes from `QuizViewModel.uiState`.
 * @param quizLevel         Level label in the close pill, e.g. "Quiz Level 01".
 * @param quizTitle         Quiz title shown centred in the top bar.
 * @param onClose           Called when the user taps the close pill.
 *                          TODO(Week 4): `NavController.popBackStack()`.
 * @param onNextQuestion    Called when the user taps Next Question.
 *                          TODO(Week 5): `QuizViewModel.nextQuestion()`.
 */
@Composable
fun QuizScreen(
    timeLeft: Int               = 12,           // TODO(Week 3): LaunchedEffect ticking every second
    totalTime: Int              = 30,
    questionNumber: Int         = 3,            // TODO(Week 5): QuizViewModel.currentIndex + 1
    totalQuestions: Int         = 10,           // TODO(Week 5): QuizViewModel.questions.size
    questionText: String        = "Which nebula is often called the \u201cNursery of Stars\u201d?",
    // TODO(Week 4): from nav args, Question.text
    answers: List<AnswerOption> = sampleAnswers,// TODO(Week 4): from nav args, Question answers
    selectedAnswer: String?     = "B",          // TODO(Week 3): mutableStateOf<String?>(null)
    correctAnswer: String       = "B",          // TODO(Week 4): from nav args, Question.correctAnswer
    score: Int                  = 240,          // TODO(Week 5): QuizViewModel.uiState.score
    quizLevel: String           = "Quiz Level 01",
    quizTitle: String           = "Quantum Physics Fun",
    onClose: () -> Unit         = {},           // TODO(Week 4): NavController.popBackStack()
    onNextQuestion: () -> Unit  = {}            // TODO(Week 5): QuizViewModel.nextQuestion()
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
            QuizTopBar(
                quizLevel = quizLevel,
                quizTitle = quizTitle,
                score     = score,
                onClose   = onClose,
                modifier  = Modifier.padding(
                    horizontal = Spacing.screenEdge,
                    vertical   = Spacing.lg
                )
            )

            // ── Main card — fills all remaining space ──────────────────────
            QuizCard(
                modifier       = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.screenEdge),
                timeLeft       = timeLeft,
                totalTime      = totalTime,
                questionNumber = questionNumber,
                totalQuestions = totalQuestions,
                questionText   = questionText,
                answers        = answers,
                selectedAnswer = selectedAnswer,
                correctAnswer  = correctAnswer
            )

            // ── Next Question — pinned below the card ──────────────────────
            NextQuestionButton(
                onClick  = onNextQuestion,
                modifier = Modifier.padding(
                    horizontal = Spacing.screenEdge,
                    vertical   = Spacing.xl
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// TOP BAR
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Custom top bar for the Quiz screen.
 *
 * Three elements in a [Row]:
 * 1. Close pill (left) — "× [quizLevel]" tappable chip to exit the quiz.
 * 2. Quiz title (centre) — `weight(1f)` fills remaining space, `maxLines = 2` wraps long names.
 * 3. Score pill (right) — coin icon + score on a `secondary` (coral) background.
 *
 * Implemented as a plain [Row] — no [androidx.compose.material3.TopAppBar] because
 * the pill-based layout doesn't fit the standard slot structure.
 *
 * @param quizLevel   Level label inside the close pill, e.g. "Quiz Level 01".
 * @param quizTitle   Quiz title shown centred between the two pills.
 * @param score       Current score shown in the right pill.
 *                    TODO(Week 5): comes from `QuizViewModel.uiState.score`.
 * @param onClose     Called when the user taps the close pill.
 *                    TODO(Week 4): `NavController.popBackStack()`.
 * @param modifier    Applied to the outermost [Row] element.
 */
@Composable
private fun QuizTopBar(
    quizLevel: String,
    quizTitle: String,
    score: Int,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier          = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Close pill — surface background, tappable
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface, shape = ChipShape)
                .clickable(onClick = onClose)
                .padding(horizontal = Spacing.md, vertical = Spacing.sm),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            Icon(
                painter            = painterResource(R.drawable.ic_close),
                contentDescription = "Close quiz",
                tint               = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier           = Modifier.size(ComponentSize.pillIconSize)
            )
            Text(
                text  = quizLevel,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Quiz title — weight(1f) + TextAlign.Center — centres between two fixed pills
        Text(
            text       = quizTitle,
            style      = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color      = MaterialTheme.colorScheme.onBackground,
            textAlign  = TextAlign.Center,
            maxLines   = 2,
            modifier   = Modifier
                .weight(1f)
                .padding(horizontal = Spacing.sm)
        )

        // Score pill — coral background (secondary), white text
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.secondary, shape = ChipShape)
                .padding(horizontal = Spacing.md, vertical = Spacing.sm),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            Icon(
                // TODO: replace R.drawable.ic_animals with the actual coin/score icon
                painter            = painterResource(R.drawable.ic_animals),
                contentDescription = null,
                tint               = Color.White,
                modifier           = Modifier.size(ComponentSize.pillIconSize)
            )
            Text(
                text       = "$score",
                style      = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color      = Color.White
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// QUIZ CARD
// ─────────────────────────────────────────────────────────────────────────────

/**
 * White card containing the timer ring, question counter, question text,
 * and four answer option cards.
 *
 * Fills all available vertical space between the top bar and the Next Question
 * button. Content is scrollable to handle long questions on small screens.
 *
 * [AnimatedTimerRing] and [AnswerOptionCard] are imported from `ui/components/` —
 * their animations are already built and will fire as soon as [timeLeft] and
 * [selectedAnswer] connect to real state in Week 3.
 *
 * @param timeLeft          Seconds remaining — drives [AnimatedTimerRing].
 * @param totalTime         Total seconds per question — computes progress ratio.
 * @param questionNumber    1-based current question index.
 * @param totalQuestions    Total questions in the session.
 * @param questionText      The question text to display.
 * @param answers           Four answer options.
 * @param selectedAnswer    Letter the user tapped, or null.
 * @param correctAnswer     Letter of the correct answer.
 * @param modifier          Applied to the outermost [Card] element.
 */
@Composable
private fun QuizCard(
    timeLeft: Int,
    totalTime: Int,
    questionNumber: Int,
    totalQuestions: Int,
    questionText: String,
    answers: List<AnswerOption>,
    selectedAnswer: String?,
    correctAnswer: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier  = modifier.fillMaxWidth(),
        shape     = CardShape,
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border    = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.xl, vertical = Spacing.xxl),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            // Animated countdown ring — all four animations built, fires in Week 3
            AnimatedTimerRing(
                timeLeft  = timeLeft,
                totalTime = totalTime
            )

            // "QUESTION 3 • 10" counter
            QuestionCounter(
                current = questionNumber,
                total   = totalQuestions
            )

            // Question text
            Text(
                text       = questionText,
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color      = MaterialTheme.colorScheme.onSurface,
                textAlign  = TextAlign.Center,
                lineHeight = MaterialTheme.typography.titleLarge.fontSize * 1.4f
            )

            Spacer(modifier = Modifier.height(Spacing.xs))

            // Answer option cards — animations built, connect state in Week 3
            Column(
                modifier            = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                answers.forEach { answer ->
                    AnswerOptionCard(
                        answer      = answer,
                        isSelected  = answer.letter == selectedAnswer,
                        isCorrect   = answer.letter == correctAnswer,
                        hasAnswered = selectedAnswer != null,
                        onClick     = { /* TODO(Week 3): selectedAnswer = answer.letter */ }
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// QUESTION COUNTER
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Displays the question position as "QUESTION [current] • [total]".
 *
 * [current] is shown in [MaterialTheme.colorScheme.primary] to visually distinguish
 * it from the muted total — the active question number is the important one.
 *
 * @param current   1-based index of the current question.
 *                  TODO(Week 5): comes from `QuizViewModel.currentIndex + 1`.
 * @param total     Total questions in the session.
 *                  TODO(Week 5): comes from `QuizViewModel.questions.size`.
 * @param modifier  Applied to the outermost [Row] element.
 */
@Composable
private fun QuestionCounter(
    current: Int,
    total: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier              = modifier,
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text  = "QUESTION",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.width(Spacing.sm))
        Text(
            text       = "$current",
            style      = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color      = MaterialTheme.colorScheme.primary   // active number — highlighted
        )
        Spacer(Modifier.width(Spacing.sm))
        Text(
            text  = "•",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.width(Spacing.sm))
        Text(
            text  = "$total",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// NEXT QUESTION BUTTON
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Full-width button to advance to the next question.
 *
 * Sits outside the quiz card on the `background` surface — visually separate
 * from the card content and the answer options.
 *
 * Uses `surface` fill with `primary` text — low emphasis that doesn't compete
 * with the answer options above it.
 *
 * TODO(Week 5): disabled (`enabled = false`) until `selectedAnswer != null` —
 * prevents skipping questions without answering.
 *
 * @param onClick   Called when the user taps the button.
 *                  TODO(Week 5): `QuizViewModel.nextQuestion()`.
 * @param modifier  Applied to the outermost [Button] element.
 */
@Composable
private fun NextQuestionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick  = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(ComponentSize.buttonHeightLarge),  // 56dp
        shape  = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor   = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Text(
            text       = "Next Question",
            style      = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "QuizScreen — light, answer selected")
@Composable
private fun QuizScreenLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        QuizScreen()
    }
}

@Preview(
    showBackground = true,
    name           = "QuizScreen — dark, answer selected",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun QuizScreenDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        QuizScreen()
    }
}

@Preview(showBackground = true, name = "QuizScreen — no answer yet")
@Composable
private fun QuizScreenNoAnswerPreview() {
    TriviaSparksTheme(darkTheme = false) {
        QuizScreen(selectedAnswer = null)
    }
}

@Preview(showBackground = true, name = "QuizScreen — critical time (≤ 6s)")
@Composable
private fun QuizScreenCriticalTimePreview() {
    TriviaSparksTheme(darkTheme = false) {
        QuizScreen(timeLeft = 5, selectedAnswer = null)
    }
}

@Preview(showBackground = true, name = "QuizScreen — safe timer (coral)")
@Composable
private fun QuizScreenSafeTimerPreview() {
    TriviaSparksTheme(darkTheme = false) {
        Box(
            modifier         = Modifier
                .size(140.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            AnimatedTimerRing(timeLeft = 22, totalTime = 30)
        }
    }
}

@Preview(showBackground = true, name = "QuizScreen — critical timer (red)")
@Composable
private fun QuizScreenCriticalTimerPreview() {
    TriviaSparksTheme(darkTheme = false) {
        Box(
            modifier         = Modifier
                .size(140.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            AnimatedTimerRing(timeLeft = 4, totalTime = 30)
        }
    }
}