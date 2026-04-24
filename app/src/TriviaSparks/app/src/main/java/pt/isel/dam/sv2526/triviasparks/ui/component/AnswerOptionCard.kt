package pt.isel.dam.sv2526.triviasparks.ui.component

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.ui.preview.sampleAnswers
import pt.isel.dam.sv2526.triviasparks.ui.model.AnswerOption
import pt.isel.dam.sv2526.triviasparks.ui.theme.CardShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * Animated answer option card used on [QuizScreen].
 *
 * Displays a single answer option as a tappable card with a lettered badge (A/B/C/D),
 * the answer text, and an optional checkmark icon. All visual state changes are
 * animated — no instant colour swaps.
 *
 * **Four visual states:**
 * - **Default** — white surface, muted outline border, grey letter badge.
 * - **Selected correct** (`isSelected && isCorrect`) — primary tinted background,
 *   primary border (2dp), filled primary letter badge, checkmark icon.
 * - **Selected wrong** (`isSelected && !isCorrect`) — error tinted background,
 *   error border (2dp), filled error letter badge.
 * - **Disabled/faded** (`hasAnswered && !isSelected`) — 55% opacity via [graphicsLayer].
 *   The card is still visible but visually subordinate to the selected answer.
 *
 * **Animation infrastructure:**
 * All `animateColorAsState` calls are in place from Week 2. They fire automatically
 * as soon as the boolean parameters change — no modifications to this composable
 * are needed when connecting real state in Week 3.
 *
 * ```kotlin
 * // Week 3 — connecting real state at the call site
 * var selectedAnswer by remember { mutableStateOf<String?>(null) }
 *
 * answers.forEach { answer ->
 *     AnswerOptionCard(
 *         answer      = answer,
 *         isSelected  = answer.letter == selectedAnswer,
 *         isCorrect   = answer.letter == correctAnswer,
 *         hasAnswered = selectedAnswer != null,
 *         onClick     = { if (selectedAnswer == null) selectedAnswer = answer.letter }
 *     )
 * }
 * ```
 *
 * **Note on the checkmark icon:**
 * The checkmark is currently commented out — pending the final icon drawable.
 * Uncomment and replace `R.drawable.ic_check` with your actual drawable ID
 * to enable it. The logic (`isSelected && isCorrect`) is already correct.
 *
 * Figma: https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=answer-option-card
 *
 * Wiki — answer option animation explained:
 * https://github.com/your-username/trivia-sparks/wiki/Week-2#answer-option-cards--visual-states
 *
 * @param answer        The answer option data — letter ("A"/"B"/"C"/"D") and text.
 * @param isSelected    True when this option is the one the user tapped.
 * @param isCorrect     True when this option is the correct answer.
 * @param hasAnswered   True when the user has tapped any option this question.
 *                      Disables all cards and fades unselected options.
 * @param onClick       Called when the user taps the card.
 *                      Caller must guard against double-taps:
 *                      `if (selectedAnswer == null) selectedAnswer = answer.letter`
 * @param modifier      Applied to the outermost [Card] element.
 */
@Composable
fun AnswerOptionCard(
    answer: AnswerOption,
    isSelected: Boolean,
    isCorrect: Boolean,
    hasAnswered: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // ── Animated colours ───────────────────────────────────────────────────
    // All four values animate simultaneously over 300ms when the boolean
    // parameters change. The labels include answer.letter so Android Studio's
    // animation inspector can distinguish the four instances on screen.

    val borderColour by animateColorAsState(
        targetValue = when {
            isSelected && isCorrect  -> MaterialTheme.colorScheme.primary
            isSelected && !isCorrect -> MaterialTheme.colorScheme.error
            else                     -> MaterialTheme.colorScheme.outlineVariant
        },
        animationSpec = tween(durationMillis = 300),
        label         = "answer_border_${answer.letter}"
    )

    val bgColour by animateColorAsState(
        targetValue = when {
            isSelected && isCorrect  -> MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
            isSelected && !isCorrect -> MaterialTheme.colorScheme.error.copy(alpha = 0.08f)
            else                     -> MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(durationMillis = 300),
        label         = "answer_bg_${answer.letter}"
    )

    val badgeColour by animateColorAsState(
        targetValue = when {
            isSelected && isCorrect  -> MaterialTheme.colorScheme.primary
            isSelected && !isCorrect -> MaterialTheme.colorScheme.error
            else                     -> MaterialTheme.colorScheme.surfaceVariant
        },
        animationSpec = tween(durationMillis = 300),
        label         = "answer_badge_${answer.letter}"
    )

    val badgeTextColour by animateColorAsState(
        targetValue   = if (isSelected) Color.White
        else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(durationMillis = 300),
        label         = "answer_badge_text_${answer.letter}"
    )

    // ── State-derived values ───────────────────────────────────────────────
    val borderWidth = if (isSelected) 2.dp else 1.dp

    // graphicsLayer alpha — GPU-accelerated, does not trigger recomposition
    val cardAlpha = if (hasAnswered && !isSelected) 0.55f else 1f

    // ── Card ───────────────────────────────────────────────────────────────
    Card(
        onClick   = onClick,
        enabled   = !hasAnswered,  // first tap locks all options
        modifier  = modifier
            .fillMaxWidth()
            .graphicsLayer { alpha = cardAlpha }
            .border(width = borderWidth, color = borderColour, shape = CardShape),
        shape     = CardShape,
        colors    = CardDefaults.cardColors(containerColor = bgColour),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.lg, vertical = Spacing.md),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Letter badge — animated fill colour
            Box(
                modifier         = Modifier
                    .size(ComponentSize.answerBadge)   // 36dp
                    .background(color = badgeColour, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = answer.letter,
                    style      = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color      = badgeTextColour
                )
            }

            // Answer text — weight(1f) fills space between badge and optional checkmark
            Text(
                text     = answer.text,
                style    = MaterialTheme.typography.titleMedium,
                color    = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            // ── Checkmark icon ─────────────────────────────────────────────
            // TODO: uncomment when ic_check drawable is added to res/drawable/
            // Replace R.drawable.ic_check with your actual drawable resource ID
            //
            // if (isSelected && isCorrect) {
            //     Icon(
            //         painter            = painterResource(R.drawable.ic_check),
            //         contentDescription = "Correct answer",
            //         tint               = MaterialTheme.colorScheme.primary,
            //         modifier           = Modifier.size(IconSize.md)   // 24dp
            //     )
            // }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// All four visual states are previewed — default, selected-correct,
// selected-wrong, and faded/disabled. The faded state is the most important
// to verify visually because it affects all unselected options simultaneously.
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "AnswerOptionCard — default, light")
@Composable
private fun AnswerOptionCardDefaultLightPreview() {
    TriviaSparksTheme(darkTheme = false) {
        AnswerOptionCard(
            answer      = sampleAnswers.first(),
            isSelected  = false,
            isCorrect   = false,
            hasAnswered = false,
            onClick     = {}
        )
    }
}

@Preview(
    showBackground = true,
    name           = "AnswerOptionCard — default, dark",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AnswerOptionCardDefaultDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        AnswerOptionCard(
            answer      = sampleAnswers.first(),
            isSelected  = false,
            isCorrect   = false,
            hasAnswered = false,
            onClick     = {}
        )
    }
}

@Preview(showBackground = true, name = "AnswerOptionCard — selected correct, light")
@Composable
private fun AnswerOptionCardSelectedCorrectPreview() {
    TriviaSparksTheme(darkTheme = false) {
        AnswerOptionCard(
            answer      = sampleAnswers.first(),
            isSelected  = true,
            isCorrect   = true,
            hasAnswered = true,
            onClick     = {}
        )
    }
}

@Preview(showBackground = true, name = "AnswerOptionCard — selected wrong, light")
@Composable
private fun AnswerOptionCardSelectedWrongPreview() {
    TriviaSparksTheme(darkTheme = false) {
        AnswerOptionCard(
            answer      = sampleAnswers.first(),
            isSelected  = true,
            isCorrect   = false,
            hasAnswered = true,
            onClick     = {}
        )
    }
}

@Preview(showBackground = true, name = "AnswerOptionCard — all four states")
@Composable
private fun AnswerOptionCardAllStatesPreview() {
    TriviaSparksTheme(darkTheme = false) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.md),
            modifier = Modifier.padding(Spacing.screenEdge)
        ) {
            // Default — no answer yet
            AnswerOptionCard(
                answer = sampleAnswers[0], isSelected = false,
                isCorrect = false, hasAnswered = false, onClick = {}
            )
            // Selected correct — primary green tint
            AnswerOptionCard(
                answer = sampleAnswers[1], isSelected = true,
                isCorrect = true, hasAnswered = true, onClick = {}
            )
            // Selected wrong — error red tint
            AnswerOptionCard(
                answer = sampleAnswers[2], isSelected = true,
                isCorrect = false, hasAnswered = true, onClick = {}
            )
            // Faded/disabled — unselected after answering
            AnswerOptionCard(
                answer = sampleAnswers[3], isSelected = false,
                isCorrect = false, hasAnswered = true, onClick = {}
            )
        }
    }
}