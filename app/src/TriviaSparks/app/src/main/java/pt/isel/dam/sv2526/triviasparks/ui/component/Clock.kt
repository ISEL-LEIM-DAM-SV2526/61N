package pt.isel.dam.sv2526.triviasparks.ui.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme
import pt.isel.dam.sv2526.triviasparks.ui.theme.triviasparks

// ─────────────────────────────────────────────────────────────────────────────
// TIMER DEFAULTS
//
// All magic numbers used by [AnimatedTimerRing] are named here.
// Change a value once — it updates everywhere in the component.
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Configuration constants for [AnimatedTimerRing].
 *
 * Centralises every tunable value so the component contains no magic numbers.
 * Adjust these to change the timer's look and feel without touching animation logic.
 */
private object TimerDefaults {

    // ── Size ──────────────────────────────────────────────────────────────
    val ringSize         = 116.dp  // outer size of the Box containing the ring and number
    val strokeWidth      = 8.dp    // ring arc and track stroke width

    // ── Colour thresholds ─────────────────────────────────────────────────
    // Fraction of totalTime remaining when the ring colour changes.
    // Example: safeThreshold = 0.5f means coral shows when > 50% time remains.
    const val safeThreshold     = 0.5f  // above this → coral (safe)
    const val warningThreshold  = 0.2f  // above this → yellow (hurry), below → red (danger)

    // ── Critical pulse ────────────────────────────────────────────────────
    // The ring pulses when time remaining ≤ warningThreshold.
    const val pulseTargetScale   = 1.08f  // max scale during pulse — subtle, not jarring
    const val pulseDurationMs    = 400    // ms for one pulse cycle (expand + contract)

    // ── Animation durations ───────────────────────────────────────────────
    const val progressDurationMs = 800   // arc sweep animation — matches 1 second tick
    const val colourDurationMs   = 600   // ring colour cross-fade — slower than the tick
    const val numberEnterMs      = 200   // number fade+scale-in duration
    const val numberExitMs       = 150   // number fade+scale-out — slightly faster than enter

    // ── Number crossfade scales ───────────────────────────────────────────
    // The entering number grows from a smaller scale; the exiting number grows away.
    // This creates a "popping" feel on each tick.
    const val numberEnterScale   = 0.8f   // entering number starts at 80% size
    const val numberExitScale    = 1.15f  // exiting number grows to 115% before disappearing

    // ── Arc geometry ─────────────────────────────────────────────────────
    const val arcStartAngle      = -90f   // 12 o'clock position (Canvas default is 3 o'clock)
}

// ─────────────────────────────────────────────────────────────────────────────
// ANIMATED TIMER RING
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Animated circular countdown timer ring with a centred number display.
 *
 * Built with [Canvas] for precise control over stroke width, round caps, and
 * the sweep direction. Four layers of animation run simultaneously:
 *
 * **1. Progress arc** (`animateFloatAsState`)
 * The arc sweeps smoothly from the previous progress value to the new one over
 * [TimerDefaults.progressDurationMs] with [LinearEasing] — matches the feel of a
 * real clock hand moving steadily.
 *
 * **2. Colour transition** (`animateColorAsState`)
 * The ring and number colour shifts through three stages as time runs out:
 * - `> 50%` remaining → `colorScheme.secondary` (coral) — safe
 * - `20–50%` remaining → `dreamscape.medium` (yellow) — hurry
 * - `≤ 20%` remaining → `dreamscape.hard` (red) — danger
 * Transition takes [TimerDefaults.colourDurationMs] — smooth, never jarring.
 *
 * **3. Critical pulse** (`rememberInfiniteTransition`)
 * When time drops to ≤ [TimerDefaults.warningThreshold], the ring pulses between
 * 1.0× and [TimerDefaults.pulseTargetScale] every [TimerDefaults.pulseDurationMs].
 * Applied via [graphicsLayer] — GPU-accelerated, zero recomposition cost.
 *
 * **4. Number crossfade** (`AnimatedContent`)
 * On each [timeLeft] change the number exits with scale-out + fade and enters with
 * scale-in + fade, creating a satisfying "pop" on every tick.
 *
 * **Week-3-ready:** all animations fire automatically when [timeLeft] changes.
 * Connecting `LaunchedEffect` in Week 3 requires no changes to this composable.
 *
 * ```kotlin
 * // Week 3 — at the call site in QuizScreen
 * var timeLeft by remember { mutableStateOf(30) }
 * LaunchedEffect(questionNumber) {
 *     timeLeft = 30
 *     while (timeLeft > 0) { delay(1000L); timeLeft-- }
 *     onNextQuestion()
 * }
 * AnimatedTimerRing(timeLeft = timeLeft, totalTime = 30)
 * ```
 *
 * Figma: https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 *
 * Wiki — timer animations explained:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/02-%E2%80%90-Jetpack-Compose-%E2%80%90-Compose-Fundamentals#quizscreen
 *
 * @param timeLeft   Seconds remaining. Range: 0 → [totalTime]. Drives all four animations.
 * @param totalTime  Total seconds per question — used to compute the progress ratio.
 * @param modifier   Applied to the outermost [Box] element.
 */
@Composable
fun AnimatedTimerRing(
    timeLeft: Int,
    totalTime: Int,
    modifier: Modifier = Modifier
) {
    val ext = MaterialTheme.triviasparks

    // ── 1. Progress arc ────────────────────────────────────────────────────
    // Ratio 0.0 → 1.0. LinearEasing matches a steady clock — no acceleration.
    val animatedProgress by animateFloatAsState(
        targetValue   = timeLeft.toFloat() / totalTime.toFloat(),
        animationSpec = tween(
            durationMillis = TimerDefaults.progressDurationMs,
            easing         = LinearEasing
        ),
        label = "timer_progress"
    )

    // ── 2. Colour transition ───────────────────────────────────────────────
    val targetColour = when {
        timeLeft > (totalTime * TimerDefaults.safeThreshold)    -> MaterialTheme.colorScheme.secondary
        timeLeft > (totalTime * TimerDefaults.warningThreshold) -> ext.medium
        else                                                     -> ext.hard
    }
    val ringColour by animateColorAsState(
        targetValue   = targetColour,
        animationSpec = tween(durationMillis = TimerDefaults.colourDurationMs),
        label         = "timer_colour"
    )

    // ── 3. Critical pulse ──────────────────────────────────────────────────
    // isCritical flips to true when time crosses warningThreshold.
    // The InfiniteTransition runs at all times but only produces a visible effect
    // when targetValue differs from initialValue (i.e. when isCritical is true).
    val isCritical         = timeLeft <= (totalTime * TimerDefaults.warningThreshold)
    val infiniteTransition = rememberInfiniteTransition(label = "timer_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue  = 1f,
        targetValue   = if (isCritical) TimerDefaults.pulseTargetScale else 1f,
        animationSpec = infiniteRepeatable(
            animation  = tween(
                durationMillis = TimerDefaults.pulseDurationMs,
                easing         = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val trackColour = MaterialTheme.colorScheme.surfaceVariant

    // ── Outer Box — applies the pulse scale via graphicsLayer ─────────────
    Box(
        modifier         = modifier
            .size(TimerDefaults.ringSize)
            .graphicsLayer {
                scaleX = pulseScale
                scaleY = pulseScale
            },
        contentAlignment = Alignment.Center
    ) {
        // ── Canvas — draws the track ring and the progress arc ─────────────
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = TimerDefaults.strokeWidth.toPx()
            val radius   = (size.minDimension / 2f) - (strokePx / 2f)
            val centre   = Offset(size.width / 2f, size.height / 2f)

            // Full 360° track — always visible, muted colour
            drawCircle(
                color  = trackColour,
                radius = radius,
                center = centre,
                style  = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // Progress arc — sweeps clockwise from 12 o'clock
            // Guard against drawing a 0-length arc which can cause artefacts
            if (animatedProgress > 0f) {
                drawArc(
                    color      = ringColour,
                    startAngle = TimerDefaults.arcStartAngle,
                    sweepAngle = 360f * animatedProgress,
                    useCenter  = false,
                    style      = Stroke(width = strokePx, cap = StrokeCap.Round),
                    size       = Size(radius * 2f, radius * 2f),
                    topLeft    = Offset(centre.x - radius, centre.y - radius)
                )
            }
        }

        // ── 4. Number with crossfade ───────────────────────────────────────
        // AnimatedContent triggers on every timeLeft change.
        // Exit: grows slightly and fades out. Enter: grows in from smaller size.
        AnimatedContent(
            targetState    = timeLeft,
            transitionSpec = {
                (fadeIn(tween(TimerDefaults.numberEnterMs)) +
                        scaleIn(tween(TimerDefaults.numberEnterMs),
                            initialScale = TimerDefaults.numberEnterScale)) togetherWith
                        (fadeOut(tween(TimerDefaults.numberExitMs)) +
                                scaleOut(tween(TimerDefaults.numberExitMs),
                                    targetScale = TimerDefaults.numberExitScale))
            },
            label = "timer_number"
        ) { displayTime ->
            Text(
                text       = "$displayTime",
                style      = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.ExtraBold,
                color      = ringColour   // number always matches ring colour
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Timer — safe (coral, >50%)")
@Composable
private fun TimerSafePreview() {
    TriviaSparksTheme(darkTheme = false) {
        Box(
            modifier         = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(Spacing.xxl),
            contentAlignment = Alignment.Center
        ) {
            AnimatedTimerRing(timeLeft = 22, totalTime = 30)
        }
    }
}

@Preview(showBackground = true, name = "Timer — warning (yellow, 20–50%)")
@Composable
private fun TimerWarningPreview() {
    TriviaSparksTheme(darkTheme = false) {
        Box(
            modifier         = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(Spacing.xxl),
            contentAlignment = Alignment.Center
        ) {
            AnimatedTimerRing(timeLeft = 9, totalTime = 30)
        }
    }
}

@Preview(showBackground = true, name = "Timer — critical (red, ≤20%)")
@Composable
private fun TimerCriticalPreview() {
    TriviaSparksTheme(darkTheme = false) {
        Box(
            modifier         = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(Spacing.xxl),
            contentAlignment = Alignment.Center
        ) {
            AnimatedTimerRing(timeLeft = 4, totalTime = 30)
        }
    }
}

@Preview(
    showBackground = true,
    name           = "Timer — critical, dark",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun TimerCriticalDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        Box(
            modifier         = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(Spacing.xxl),
            contentAlignment = Alignment.Center
        ) {
            AnimatedTimerRing(timeLeft = 4, totalTime = 30)
        }
    }
}