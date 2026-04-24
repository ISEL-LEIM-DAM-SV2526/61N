package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pt.isel.dam.sv2526.triviasparks.R

/**
 * Trivia Sparks — Typography system.
 *
 * Defines the font family and the complete Material3 type scale used across
 * the app. Typography is one of the three pillars of the Material3 theme
 * (alongside colour and shape) — it gives the Dreamscape system its bold,
 * game-appropriate character.
 *
 * **Font family: Nunito**
 * Rounded, friendly, and legible at both small body sizes and large score
 * displays. Four weights are loaded: Regular, SemiBold, Bold, ExtraBold.
 *
 * **How typography flows into the UI:**
 * ```
 * Type.kt  →  Theme.kt (passed to MaterialTheme)  →  Composables
 *                     ↓
 *           MaterialTheme.typography.*   (access the scale in composables)
 * ```
 *
 * **Rule:** never set `fontSize` directly in a composable.
 * Always use `style = MaterialTheme.typography.*` to pick the right scale step.
 *
 * Wiki — typography:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/00-%E2%80%90-TriviaSparks-Game#typography--typekt
 *
 *
 * Figma — typography tokens:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 */

// ─────────────────────────────────────────────────────────────────────────────
// FONT FAMILY
//
// Nunito is loaded in four weights from res/font/. Each weight maps to the
// FontWeight constant that the type scale references below.
//
//   nunito_regular.ttf    → FontWeight.Normal    (body text, captions)
//   nunito_semibold.ttf   → FontWeight.SemiBold  (card titles, list items)
//   nunito_bold.ttf       → FontWeight.Bold      (headlines, buttons, labels)
//   nunito_extrabold.ttf  → FontWeight.ExtraBold (display — scores, timer)
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Nunito font family with all four weights used in the Dreamscape system.
 *
 * Loaded from `res/font/` as static assets — no network request required.
 * Passed to [TriviaSparksTypography] through [TriviaSparksFont].
 */
val NunitoFamily = FontFamily(
    Font(R.font.nunito_regular,   FontWeight.Normal),
    Font(R.font.nunito_semibold,  FontWeight.SemiBold),
    Font(R.font.nunito_bold,      FontWeight.Bold),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold)
)

/** Alias for the app font — swap this single reference to change the font globally. */
val TriviaSparksFont = NunitoFamily

// ─────────────────────────────────────────────────────────────────────────────
// TYPOGRAPHY SCALE
//
// 13 named styles following the Material3 type scale. Picking the right style
// is as important as picking the right colour token.
//
// Quick reference — which style for which element:
//
//   displayLarge   → XP totals, huge hero numbers (rarely used)
//   displayMedium  → ResultsScreen final score "8 / 10"
//   displaySmall   → Countdown timer number inside AnimatedTimerRing
//   headlineLarge  → App name on splash / quiz title on QuizDetailScreen
//   headlineMedium → "Hello, Alexa" greeting, quiz title on detail screen
//   headlineSmall  → "Recent Friends", "Latest Quizzes", TopAppBar titles
//   titleLarge     → Question text on QuizScreen
//   titleMedium    → Category names, quiz card titles in lists
//   titleSmall     → Friend names, creator name on QuizDetailScreen
//   bodyLarge      → Login/register form field text
//   bodyMedium     → Quiz description, HomeScreen subtitle, "See all" links
//   bodySmall      → "15 Questions • Advanced" subtitles, captions, counts
//   labelLarge     → All button text — "Start Quiz", "Play Solo"
//   labelMedium    → Chip labels, bottom nav labels, score badges
//   labelSmall     → UPPERCASE section headings — "DIFFICULTY", "CATEGORIES"
//
// ─────────────────────────────────────────────────────────────────────────────

val TriviaSparksTypography = Typography(

    // ── DISPLAY ──────────────────────────────────────────────────────────────
    // Large impactful numbers for scores, timers, and hero stats.
    // ExtraBold weight gives these the chunky game-style feel.

    displayLarge = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.ExtraBold,
        fontSize      = 48.sp,
        lineHeight    = 52.sp,
        letterSpacing = (-1).sp
        // Usage: XP totals, huge hero stat numbers — rarely used directly
    ),

    displayMedium = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.ExtraBold,
        fontSize      = 36.sp,
        lineHeight    = 40.sp,
        letterSpacing = (-0.5).sp
        // Usage: ResultsScreen final score "8 / 10", leaderboard top scores
    ),

    displaySmall = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.ExtraBold,
        fontSize      = 28.sp,
        lineHeight    = 34.sp,
        letterSpacing = 0.sp
        // Usage: Countdown timer number inside AnimatedTimerRing on QuizScreen
    ),

    // ── HEADLINE ─────────────────────────────────────────────────────────────
    // Screen titles and primary section headers. Bold weight — never ExtraBold.

    headlineLarge = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.Bold,
        fontSize      = 28.sp,
        lineHeight    = 34.sp,
        letterSpacing = 0.sp
        // Usage: App name on splash/onboarding, quiz title on QuizDetailScreen
    ),

    headlineMedium = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.Bold,
        fontSize      = 22.sp,
        lineHeight    = 28.sp,
        letterSpacing = 0.sp
        // Usage: "Hello, Alexa" greeting on HomeScreen, quiz title on QuizDetailScreen
    ),

    headlineSmall = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.Bold,
        fontSize      = 18.sp,
        lineHeight    = 24.sp,
        letterSpacing = 0.sp
        // Usage: "Recent Friends", "Latest Quizzes" section headers, TopAppBar titles
    ),

    // ── TITLE ────────────────────────────────────────────────────────────────
    // Card titles, list item names, key content labels. SemiBold weight.

    titleLarge = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.SemiBold,
        fontSize      = 16.sp,
        lineHeight    = 22.sp,
        letterSpacing = 0.sp
        // Usage: Question text on QuizScreen — must be readable at a glance under time pressure
    ),

    titleMedium = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.SemiBold,
        fontSize      = 14.sp,
        lineHeight    = 20.sp,
        letterSpacing = 0.1.sp
        // Usage: Category names in CategoryRow, quiz titles in QuizListCard on HomeScreen
    ),

    titleSmall = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.SemiBold,
        fontSize      = 13.sp,
        lineHeight    = 18.sp,
        letterSpacing = 0.1.sp
        // Usage: Friend names in FriendCard, creator name on QuizDetailScreen
    ),

    // ── BODY ─────────────────────────────────────────────────────────────────
    // Paragraph text, descriptions, and general readable content. Normal weight.

    bodyLarge = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.Normal,
        fontSize      = 16.sp,
        lineHeight    = 24.sp,
        letterSpacing = 0.5.sp
        // Usage: Login/register form field text
    ),

    bodyMedium = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.Normal,
        fontSize      = 14.sp,
        lineHeight    = 22.sp,
        letterSpacing = 0.25.sp
        // Usage: "About this quiz" description, HomeScreen subtitle "Ready to boost?",
        //        "See all" links, search bar placeholder text
    ),

    bodySmall = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.Normal,
        fontSize      = 12.sp,
        lineHeight    = 18.sp,
        letterSpacing = 0.4.sp
        // Usage: "15 Questions • Advanced" card subtitles, friend score "1.2k", captions
    ),

    // ── LABEL ────────────────────────────────────────────────────────────────
    // Buttons, chips, navigation items, and badges. Bold weight throughout —
    // these must remain legible at small sizes and in constrained spaces.

    labelLarge = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.Bold,
        fontSize      = 14.sp,
        lineHeight    = 18.sp,
        letterSpacing = 0.1.sp
        // Usage: All button text — "Start Quiz", "Play Solo", "Play with Friends",
        //        "Next Question", "Join Room"
    ),

    labelMedium = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.Bold,
        fontSize      = 12.sp,
        lineHeight    = 16.sp,
        letterSpacing = 0.5.sp
        // Usage: Difficulty chip labels (Easy/Medium/Hard), bottom nav tab labels,
        //        question count badges, score pills in quiz top bar
    ),

    labelSmall = TextStyle(
        fontFamily    = TriviaSparksFont,
        fontWeight    = FontWeight.Bold,
        fontSize      = 11.sp,
        lineHeight    = 14.sp,
        letterSpacing = 1.5.sp
        // Usage: UPPERCASE section headings — "DIFFICULTY", "CATEGORIES", "ABOUT THIS QUIZ"
        //        The 1.5sp letter spacing creates the spaced uppercase label look
    )
)