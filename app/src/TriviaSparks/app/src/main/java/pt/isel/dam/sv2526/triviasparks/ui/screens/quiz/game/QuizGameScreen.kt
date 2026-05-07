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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
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
 * **Answer selection behaviour:**
 * The player can tap and switch between answer options freely while the timer
 * is running. Answers become locked — and the result highlighted — only when:
 * 1. The countdown timer reaches zero, **or**
 * 2. The player taps "Next Question"
 *
 * This requires two separate state values:
 * - [selectedAnswer] — the currently highlighted option. Updates on every tap.
 * - `isAnswerLocked` — owned locally, becomes `true` at lock time. Passed as
 *   `hasAnswered` to [AnswerOptionCard] to trigger the correct/wrong highlight.
 *
 * **State summary:**
 * ```
 * selectedAnswer = null       → no option highlighted
 * selectedAnswer = "B"        → option B highlighted, others visible — STILL SWITCHABLE
 * isAnswerLocked = true       → result revealed, all options disabled
 * ```
 *
 * **Why `LaunchedEffect(key1 = questionNumber)` and not `Timer`:**
 * [LaunchedEffect] runs inside a coroutine on the main thread — safe to touch
 * Compose state. [delay] suspends without blocking. `key1 = questionNumber`
 * restarts the effect when the question changes, resetting all state automatically.
 * A `Timer` runs on a background thread and crashes when touching Compose state.
 *
 * **What is still static and when it becomes live:**
 *
 * | Element | Current state | Becomes live |
 * |---|---|---|
 * | `correctAnswer` | hardcoded `"B"` | Week 4 — from nav args |
 * | `questionText` | hardcoded string | Week 4 — from nav args |
 * | `answers` | `sampleAnswers` mock | Week 4 — from nav args |
 * | `questionNumber` / `totalQuestions` | `3` / `10` hardcoded | Week 5 — `QuizViewModel` |
 * | Score display | `correctCount × 100` locally | Week 5 — `QuizViewModel.uiState.score` |
 * | `onNextQuestion` | empty lambda | Week 5 — `QuizViewModel.nextQuestion()` |
 * | `onClose` | empty lambda | Week 4 — `NavController.popBackStack()` |
 *
 * Figma design:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773
 *
 * Wiki — Week 3 QuizScreen state:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/03-04-%E2%80%90-State-%E2%80%90-Navigation#quizscreen--timer--answer-selection
 *
 * @param totalTime         Total seconds per question. Defaults to 30.
 * @param questionNumber    1-based index of the current question.
 *                          TODO(Week 5): `QuizViewModel.currentIndex + 1`.
 * @param totalQuestions    Total questions in the session.
 *                          TODO(Week 5): `QuizViewModel.questions.size`.
 * @param questionText      The question text. HTML-decoded before this screen.
 *                          TODO(Week 4): from nav args, `Question.text`.
 * @param answers           Four answer options in display order (A, B, C, D).
 *                          TODO(Week 4): from nav args, `Question.answers`.
 * @param correctAnswer     Letter of the correct answer.
 *                          TODO(Week 4): from nav args, `Question.correctAnswer`.
 * @param quizLevel         Level label in the close pill, e.g. "Quiz Level 01".
 * @param quizTitle         Quiz title shown centred in the top bar.
 * @param onClose           Called when the user taps the close pill.
 *                          TODO(Week 4): `NavController.popBackStack()`.
 * @param onNextQuestion    Called when the player taps Next Question.
 *                          TODO(Week 5): `QuizViewModel.nextQuestion()`.
 */
@Composable
fun QuizScreen(
    totalTime: Int              = 30,
    questionNumber: Int         = 3,            // TODO(Week 5): QuizViewModel.currentIndex + 1
    totalQuestions: Int         = 10,           // TODO(Week 5): QuizViewModel.questions.size
    questionText: String        = "Which nebula is often called the \u201cNursery of Stars\u201d?",
    // TODO(Week 4): from nav args, Question.text
    answers: List<AnswerOption> = sampleAnswers,// TODO(Week 4): from nav args, Question.answers
    correctAnswer: String       = "B",          // TODO(Week 4): from nav args, Question.correctAnswer
    quizLevel: String           = "Quiz Level 01",
    quizTitle: String           = "Quantum Physics Fun",
    onClose: () -> Unit         = {},           // TODO(Week 4): NavController.popBackStack()
    onNextQuestion: () -> Unit  = {}            // TODO(Week 5): QuizViewModel.nextQuestion()
) {
    // ── Week 3 state ───────────────────────────────────────────────────────

    // The currently highlighted answer — updates freely while the timer runs.
    var selectedAnswer  by remember { mutableStateOf<String?>(null) }

    // True when the answer is locked in — either by timer expiry or Next Question tap.
    // Passed as hasAnswered to AnswerOptionCard to trigger the correct/wrong highlight.
    var isAnswerLocked  by remember { mutableStateOf(false) }

    var timeLeft        by remember { mutableIntStateOf(totalTime) }

    // Local score tracking until Week 5 connects QuizViewModel.
    // correctCount increments only once per question — at lock time.
    var correctCount    by remember { mutableIntStateOf(0) }
    val currentScore     = correctCount * 100   // TODO(Week 5): QuizViewModel.uiState.score

    // ── Countdown timer ────────────────────────────────────────────────────
    // Restarts whenever questionNumber changes — resets all per-question state.
    LaunchedEffect(key1 = questionNumber) {
        timeLeft       = totalTime
        selectedAnswer = null
        isAnswerLocked = false
        while (timeLeft > 0) {
            delay(1000L)   // suspend 1 second — never blocks the main thread
            timeLeft--
        }
        // Timer reached zero — lock the current answer and reveal the result.
        // correctCount is incremented here so it's counted exactly once per question.
        // TODO(Week 5): this logic moves to QuizViewModel
        isAnswerLocked = true
        if (selectedAnswer == correctAnswer) correctCount++
    }

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
                score     = currentScore,
                onClose   = onClose,
                modifier  = Modifier.padding(
                    horizontal = Spacing.screenEdge,
                    vertical   = Spacing.lg
                )
            )

            // ── Main card — fills all remaining space ──────────────────────
            QuizCard(
                modifier         = Modifier
                    .weight(1f)
                    .padding(horizontal = Spacing.screenEdge),
                timeLeft         = timeLeft,
                totalTime        = totalTime,
                questionNumber   = questionNumber,
                totalQuestions   = totalQuestions,
                questionText     = questionText,
                answers          = answers,
                selectedAnswer   = selectedAnswer,
                correctAnswer    = correctAnswer,
                isAnswerLocked   = isAnswerLocked,
                onAnswerSelected = { letter ->
                    // Allow switching answers freely while the timer is running.
                    // Once isAnswerLocked is true, taps are ignored.
                    if (!isAnswerLocked) selectedAnswer = letter
                }
            )

            // ── Next Question — pinned below the card ──────────────────────
            NextQuestionButton(
                enabled  = selectedAnswer != null,   // must select something before advancing
                // TODO(Week 5): enabled driven by QuizViewModel
                onClick  = {
                    // Lock the answer on tap — increment only if not already locked
                    // (timer may have locked it just before the tap)
                    if (!isAnswerLocked) {
                        isAnswerLocked = true
                        if (selectedAnswer == correctAnswer) correctCount++
                    }
                    onNextQuestion()
                },
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
 * 1. Close pill (left) — "× [quizLevel]" tappable chip to exit.
 * 2. Quiz title (centre) — `weight(1f)` fills remaining space, `maxLines = 2`.
 * 3. Score pill (right) — coin icon + score on `secondary` (coral) background.
 *
 * Plain [Row] — no [androidx.compose.material3.TopAppBar] because the pill
 * layout doesn't fit the standard slot structure.
 *
 * @param quizLevel   Level label inside the close pill, e.g. "Quiz Level 01".
 * @param quizTitle   Quiz title shown centred between the two pills.
 * @param score       Current score value.
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
 * White card containing the timer, question counter, question text, and
 * four [AnswerOptionCard]s.
 *
 * **State flows down, events flow up:**
 * - [selectedAnswer] and [isAnswerLocked] flow down to each [AnswerOptionCard].
 * - [onAnswerSelected] flows up — the guard (`!isAnswerLocked`) lives in
 *   [QuizScreen.onAnswerSelected], not here. State decisions belong in the owner.
 *
 * @param timeLeft          Seconds remaining — drives [AnimatedTimerRing].
 * @param totalTime         Total seconds — computes the progress ratio.
 * @param questionNumber    1-based question index.
 * @param totalQuestions    Total questions in the session.
 * @param questionText      The question text.
 * @param answers           Four answer options.
 * @param selectedAnswer    Currently highlighted letter, or null if nothing selected.
 * @param correctAnswer     Letter of the correct answer.
 * @param isAnswerLocked    True when the answer is locked — triggers highlight.
 * @param onAnswerSelected  Called with the tapped letter. Guard lives in [QuizScreen].
 * @param modifier          Applied to the outermost [Card].
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
    isAnswerLocked: Boolean,
    onAnswerSelected: (String) -> Unit,
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
            AnimatedTimerRing(timeLeft = timeLeft, totalTime = totalTime)

            QuestionCounter(current = questionNumber, total = totalQuestions)

            Text(
                text       = questionText,
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color      = MaterialTheme.colorScheme.onSurface,
                textAlign  = TextAlign.Center,
                lineHeight = MaterialTheme.typography.titleLarge.fontSize * 1.4f
            )

            Spacer(modifier = Modifier.height(Spacing.xs))

            Column(
                modifier            = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing.md)
            ) {
                answers.forEach { answer ->
                    AnswerOptionCard(
                        answer      = answer,
                        isSelected  = answer.letter == selectedAnswer,
                        isCorrect   = answer.letter == correctAnswer,
                        // hasAnswered drives the lock highlight — only true when locked,
                        // not on every selection. This is what allows answer switching.
                        hasAnswered = isAnswerLocked,
                        onClick     = { onAnswerSelected(answer.letter) }
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
 * Displays "QUESTION [current] • [total]".
 *
 * [current] uses `colorScheme.primary` to visually distinguish the active
 * question number from the muted total.
 *
 * @param current   1-based index of the current question.
 * @param total     Total questions in the session.
 * @param modifier  Applied to the outermost [Row].
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
        Text("QUESTION", style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.width(Spacing.sm))
        Text("$current", style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(Spacing.sm))
        Text("•", style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.width(Spacing.sm))
        Text("$total", style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// NEXT QUESTION BUTTON
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Full-width button to advance to the next question.
 *
 * Disabled when no answer is selected — the player must tap an option before
 * they can advance. This prevents skipping questions entirely.
 *
 * Low-emphasis style (`surface` fill, `primary` text) — does not compete
 * visually with the answer options.
 *
 * TODO(Week 5): [enabled] driven by `QuizViewModel` — also needs to handle
 * the last question case (navigates to ResultsScreen instead of next question).
 *
 * @param enabled   True when an answer is selected. False otherwise.
 * @param onClick   Called when tapped. Locks the answer before advancing.
 * @param modifier  Applied to the outermost [Button].
 */
@Composable
private fun NextQuestionButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick  = onClick,
        enabled  = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(ComponentSize.buttonHeightLarge),
        shape  = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor         = MaterialTheme.colorScheme.surface,
            contentColor           = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor   = MaterialTheme.colorScheme.onSurfaceVariant
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

@Preview(showBackground = true, name = "QuizScreen — light")
@Composable
private fun QuizScreenLightPreview() {
    TriviaSparksTheme(darkTheme = false) { QuizScreen() }
}

@Preview(showBackground = true, name = "QuizScreen — dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuizScreenDarkPreview() {
    TriviaSparksTheme(darkTheme = true) { QuizScreen() }
}

@Preview(showBackground = true, name = "Timer — safe (coral, >50%)")
@Composable
private fun TimerSafePreview() {
    TriviaSparksTheme(darkTheme = false) {
        Box(Modifier.size(140.dp).background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center) {
            AnimatedTimerRing(timeLeft = 22, totalTime = 30)
        }
    }
}

@Preview(showBackground = true, name = "Timer — warning (yellow, 20–50%)")
@Composable
private fun TimerWarningPreview() {
    TriviaSparksTheme(darkTheme = false) {
        Box(Modifier.size(140.dp).background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center) {
            AnimatedTimerRing(timeLeft = 9, totalTime = 30)
        }
    }
}

@Preview(showBackground = true, name = "Timer — critical (red, ≤20%)")
@Composable
private fun TimerCriticalPreview() {
    TriviaSparksTheme(darkTheme = false) {
        Box(Modifier.size(140.dp).background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center) {
            AnimatedTimerRing(timeLeft = 4, totalTime = 30)
        }
    }
}