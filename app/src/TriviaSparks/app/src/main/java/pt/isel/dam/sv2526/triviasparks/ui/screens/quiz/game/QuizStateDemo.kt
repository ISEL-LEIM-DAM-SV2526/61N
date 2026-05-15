package pt.isel.dam.sv2526.triviasparks.ui.screens.quiz.game

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import pt.isel.dam.sv2526.triviasparks.ui.component.AnswerOptionCard
import pt.isel.dam.sv2526.triviasparks.ui.component.AnimatedTimerRing
import pt.isel.dam.sv2526.triviasparks.ui.model.AnswerOption
import pt.isel.dam.sv2526.triviasparks.ui.preview.sampleAnswers
import pt.isel.dam.sv2526.triviasparks.ui.theme.ButtonShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.CardShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

// ─────────────────────────────────────────────────────────────────────────────
// ⚠️  TEACHING TOOL — NOT PRODUCTION CODE
//
// This screen is for the Week 3 class session only.
// Lives in classes/week3/ — never shipped in the app.
//
// It wraps real quiz components (AnimatedTimerRing, AnswerOptionCard) with a
// visible STATE INSPECTOR panel that makes three invisible concepts visible:
//
//   remember + mutableStateOf  → inspector shows state values changing live
//   recomposition               → recompose counter increments on every redraw
//   LaunchedEffect              → effect status label shows lifecycle events
// ─────────────────────────────────────────────────────────────────────────────

private val demoAnswers = listOf(
    AnswerOption(letter = "A", text = "Eagle Nebula"),
    AnswerOption(letter = "B", text = "Orion Nebula"),
    AnswerOption(letter = "C", text = "Crab Nebula"),
    AnswerOption(letter = "D", text = "Helix Nebula")
)
private const val DEMO_CORRECT   = "B"
private const val DEMO_TOTAL_TIME = 20

/**
 * Teaching demo screen — makes `remember`, `mutableStateOf`, recomposition,
 * and `LaunchedEffect` visible during the Week 3 class session.
 *
 * **What students observe:**
 *
 * | Action | What changes in the inspector |
 * |---|---|
 * | Tap an answer | `selectedAnswer` updates instantly |
 * | Tap a different answer | `selectedAnswer` switches — no lock |
 * | Every timer tick | `timeLeft` decrements, `recomposes` increments |
 * | Tap an answer | `recomposes` increments — only answer cards recompose |
 * | Timer hits zero | `isLocked` → true, `effectStatus` → "COMPLETED" |
 * | Tap Reset | `LaunchedEffect` restarts, `effectStatus` → "RUNNING" |
 *
 * **The `recomposeCount` teaching moment:**
 * Every second, `timeLeft` changes. The [SideEffect] runs after every
 * successful recomposition and increments the counter. Students can see that
 * tapping an answer also increments it — only the composables that *read*
 * changed state recompose, not the whole screen.
 *
 * **The `LaunchedEffect` teaching moment:**
 * The `effectStatus` label changes from "RUNNING" → "COMPLETED" when the
 * timer reaches zero. Tapping Reset restarts the effect — students see
 * "RUNNING" appear again and the timer reset simultaneously. This makes
 * the `key1 = questionNumber` restart behaviour tangible.
 *
 * This file lives in `classes/week3/` — not part of the app build.
 *
 * Wiki — Week 3 state concepts:
 * https://github.com/your-username/trivia-sparks/wiki/Week-3-4#part-1--state-management
 */
@Composable
fun QuizStateDemo() {

    // ── State under observation ────────────────────────────────────────────
    var selectedAnswer  by remember { mutableStateOf<String?>(null) }
    var isAnswerLocked  by remember { mutableStateOf(false) }
    var timeLeft        by remember { mutableIntStateOf(DEMO_TOTAL_TIME) }
    var questionKey     by remember { mutableIntStateOf(0) }   // changing this restarts the effect
    var effectStatus    by remember { mutableStateOf("RUNNING") }

    // ── Recomposition counter ──────────────────────────────────────────────
    // SideEffect runs after every successful recomposition of this composable.
    // It is NOT a state change itself — it's a side effect of recomposition.
    // Students can see this counter increment on every timer tick AND on
    // every answer tap, making recomposition tangible.
    var recomposeCount by remember { mutableIntStateOf(0) }
    SideEffect {
        recomposeCount++
    }

    // ── LaunchedEffect — the countdown ────────────────────────────────────
    // key1 = questionKey — restart by changing this value (Reset button)
    // effectStatus changes label so students can see the lifecycle
    LaunchedEffect(key1 = questionKey) {
        effectStatus   = "RUNNING"
        timeLeft       = DEMO_TOTAL_TIME
        selectedAnswer = null
        isAnswerLocked = false
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        // Effect completed — timer hit zero
        isAnswerLocked = true
        effectStatus   = "COMPLETED"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.screenEdge, vertical = Spacing.lg),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {

            // ── Screen title ───────────────────────────────────────────────
            Text(
                text       = "State Demo",
                style      = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color      = MaterialTheme.colorScheme.primary
            )
            Text(
                text  = "Tap answers. Watch the inspector. Press Reset to restart the LaunchedEffect.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ── STATE INSPECTOR ────────────────────────────────────────────
            StateInspector(
                selectedAnswer = selectedAnswer,
                isAnswerLocked = isAnswerLocked,
                timeLeft       = timeLeft,
                recomposeCount = recomposeCount,
                effectStatus   = effectStatus
            )

            // ── Timer ──────────────────────────────────────────────────────
            Box(
                modifier         = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AnimatedTimerRing(timeLeft = timeLeft, totalTime = DEMO_TOTAL_TIME)
            }

            // ── Answer options ─────────────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
                demoAnswers.forEach { answer ->
                    AnswerOptionCard(
                        answer      = answer,
                        isSelected  = answer.letter == selectedAnswer,
                        isCorrect   = answer.letter == DEMO_CORRECT,
                        hasAnswered = isAnswerLocked,
                        onClick     = {
                            // No lock guard — switching freely while timer runs
                            if (!isAnswerLocked) selectedAnswer = answer.letter
                        }
                    )
                }
            }

            // ── Reset button ───────────────────────────────────────────────
            // Incrementing questionKey restarts LaunchedEffect
            ResetButton(
                onClick = { questionKey++ }
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// STATE INSPECTOR PANEL
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Debug panel showing live state values — the core teaching tool.
 *
 * Each row shows one state variable updating in real time. The [recomposeCount]
 * row makes recomposition visible. The [effectStatus] row shows the
 * [LaunchedEffect] lifecycle.
 */
@Composable
private fun StateInspector(
    selectedAnswer: String?,
    isAnswerLocked: Boolean,
    timeLeft: Int,
    recomposeCount: Int,
    effectStatus: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF0D1117))           // dark terminal background
            .border(1.dp, Color(0xFF30363D), RoundedCornerShape(12.dp))
            .padding(Spacing.lg),
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        // Panel header
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text       = "STATE INSPECTOR",
                fontFamily = FontFamily.Monospace,
                fontSize   = 10.sp,
                fontWeight = FontWeight.Bold,
                color      = Color(0xFF7D8590),
                letterSpacing = 1.5.sp
            )
            Text(
                text       = "⚠ debug only",
                fontFamily = FontFamily.Monospace,
                fontSize   = 9.sp,
                color      = Color(0xFF7D8590)
            )
        }

        Spacer(Modifier.height(2.dp))

        // State rows
        InspectorRow(
            label = "selectedAnswer",
            value = selectedAnswer ?: "null",
            valueColor = if (selectedAnswer != null) Color(0xFF79C0FF) else Color(0xFF7D8590)
        )
        InspectorRow(
            label = "isAnswerLocked",
            value = isAnswerLocked.toString(),
            valueColor = if (isAnswerLocked) Color(0xFFFF7B72) else Color(0xFF56D364)
        )
        InspectorRow(
            label = "timeLeft",
            value = "${timeLeft}s",
            valueColor = when {
                timeLeft > DEMO_TOTAL_TIME * 0.5  -> Color(0xFF56D364)   // green — safe
                timeLeft > DEMO_TOTAL_TIME * 0.2  -> Color(0xFFE3B341)   // yellow — hurry
                else                              -> Color(0xFFFF7B72)   // red — danger
            }
        )

        // Divider
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color(0xFF21262D))
        )

        // Recomposition counter — animates to draw attention
        val recomposeColor by animateColorAsState(
            targetValue   = if (recomposeCount % 2 == 0) Color(0xFFD2A8FF)
            else Color(0xFFF778BA),
            animationSpec = tween(durationMillis = 100),
            label         = "recompose_flash"
        )
        InspectorRow(
            label      = "recompositions",
            value      = recomposeCount.toString(),
            valueColor = recomposeColor,
            annotation = "← SideEffect"
        )
        InspectorRow(
            label      = "effectStatus",
            value      = effectStatus,
            valueColor = if (effectStatus == "RUNNING") Color(0xFF56D364)
            else Color(0xFF7D8590),
            annotation = "← LaunchedEffect"
        )
    }
}

/**
 * One row in the [StateInspector] panel.
 * Shows a monospace label, an arrow, and a coloured value.
 */
@Composable
private fun InspectorRow(
    label: String,
    value: String,
    valueColor: Color,
    annotation: String = "",
    modifier: Modifier = Modifier
) {
    Row(
        modifier          = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text       = label,
            fontFamily = FontFamily.Monospace,
            fontSize   = 12.sp,
            color      = Color(0xFF8B949E),
            modifier   = Modifier.width(150.dp)
        )
        Text(
            text       = "→",
            fontFamily = FontFamily.Monospace,
            fontSize   = 12.sp,
            color      = Color(0xFF30363D)
        )
        Spacer(Modifier.width(Spacing.sm))
        Text(
            text       = value,
            fontFamily = FontFamily.Monospace,
            fontSize   = 12.sp,
            fontWeight = FontWeight.Bold,
            color      = valueColor
        )
        if (annotation.isNotEmpty()) {
            Spacer(Modifier.width(Spacing.md))
            Text(
                text       = annotation,
                fontFamily = FontFamily.Monospace,
                fontSize   = 10.sp,
                color      = Color(0xFF7D8590)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// RESET BUTTON
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Restarts the demo by incrementing the `questionKey` that drives
 * `LaunchedEffect(key1 = questionKey)`.
 *
 * **This is the teaching moment for `LaunchedEffect` keys:**
 * Tapping Reset does not call any restart function — it simply changes a
 * state value. Compose detects that the key changed and automatically
 * cancels the running effect and starts a new one. Students see
 * `effectStatus` flip back to "RUNNING" and `timeLeft` reset simultaneously.
 */
@Composable
private fun ResetButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick  = onClick,
        modifier = modifier.fillMaxWidth(),
        shape    = ButtonShape,
        colors   = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor   = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Text(
            text       = "Reset  —  restart LaunchedEffect",
            style      = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "QuizStateDemo — light")
@Composable
private fun QuizStateDemoLightPreview() {
    TriviaSparksTheme(darkTheme = false) { QuizStateDemo() }
}

@Preview(showBackground = true, name = "QuizStateDemo — dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun QuizStateDemoDarkPreview() {
    TriviaSparksTheme(darkTheme = true) { QuizStateDemo() }
}