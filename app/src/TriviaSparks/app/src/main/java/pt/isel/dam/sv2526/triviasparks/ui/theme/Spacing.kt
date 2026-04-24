package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Trivia Sparks — Layout tokens.
 *
 * Defines all spacing, elevation, icon size, and component size constants
 * used across the app. Using these tokens instead of raw dp values ensures
 * visual consistency — changing one token updates every place that uses it.
 *
 * **Four token groups:**
 * ```
 * Spacing        → padding, gaps, margins between elements
 * Elevation      → shadow depth for cards, overlays, and floating elements
 * IconSize       → icon dimensions at each scale
 * ComponentSize  → fixed sizes for reusable UI components
 * ```
 *
 * **Rule:** never write a raw `dp` value in a composable.
 * Always reference one of these tokens.
 *
 * Wiki — spacing system reference:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/00-%E2%80%90-TriviaSparks-Game#spacing--spacingkt
 *
 * Figma — spacing and sizing tokens:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 */

// ─────────────────────────────────────────────────────────────────────────────
// SPACING
//
// All padding, gap, and margin values used in the app.
// There are two layers:
//
//   Base scale (xs → xxxl) — the raw spacing steps. Use these when no
//   semantic alias fits the context.
//
//   Semantic aliases (screenEdge, cardPadding, ...) — named by purpose.
//   Always prefer these in composable code — they communicate intent and
//   make it easier to update all instances of a spacing decision at once.
//
// Usage:
//   Modifier.padding(horizontal = Spacing.screenEdge)
//   Modifier.padding(Spacing.cardPadding)
//   Arrangement.spacedBy(Spacing.cardGap)
// ─────────────────────────────────────────────────────────────────────────────

object Spacing {

    // ── Base scale ────────────────────────────────────────────────────────
    val xs = 4.dp  // icon-to-label gap, tight inline spacing
    val sm = 8.dp  // badge internal padding, small row gaps
    val md = 12.dp  // gap between list cards, chip row spacing
    val lg = 16.dp  // card internal padding, standard row gaps
    val xl = 20.dp  // screen horizontal edge padding
    val xxl = 24.dp  // section gaps, card top/bottom on detail screens
    val xxxl = 32.dp  // large section breaks, hero top padding

    // ── Semantic aliases ──────────────────────────────────────────────────
    // Prefer these in composables — they communicate purpose, not just size.
    val screenEdge = xl    // 20dp — horizontal padding on every screen
    val cardPadding = lg    // 16dp — internal padding inside cards
    val cardGap = md    // 12dp — vertical gap between list cards
    val sectionGap = xxl   // 24dp — gap between major screen sections
    val iconToText = lg    // 16dp — gap between icon container and text column

    // ── Component-specific spacing ────────────────────────────────────────
    // Used inside chips and badges where padding differs from the base scale.
    val chipHorizontal = 14.dp  // horizontal padding inside difficulty chips
    val chipVertical = 7.dp  // vertical padding inside difficulty chips

    val badgeHorizontal = 10.dp  // horizontal padding inside question count badges
    val badgeVertical = 4.dp  // vertical padding inside question count badges
}

// ─────────────────────────────────────────────────────────────────────────────
// ELEVATION
//
// Controls the shadow depth of surfaces and floating elements.
// Trivia Sparks uses subtle elevation — no heavy Material shadows.
// The game tile aesthetic relies on borders and colour contrast for depth
// rather than aggressive drop shadows.
//
// Usage:
//   Card(elevation = CardDefaults.cardElevation(defaultElevation = Elevation.medium))
//   Modifier.shadow(elevation = Elevation.high, shape = CardShape)
// ─────────────────────────────────────────────────────────────────────────────

object Elevation {
    val none = 0.dp  // flat — buttons, navigation bar, stat pills
    val low = 2.dp  // subtle lift — inner cards, secondary surfaces
    val medium = 4.dp  // standard — main content cards
    val high = 8.dp  // floating — FAB, bottom sheets
    val overlay = 16.dp  // topmost — dialogs, modal overlays
}

// ─────────────────────────────────────────────────────────────────────────────
// ICON SIZES
//
// Standardised icon dimensions for consistent visual rhythm across the app.
// Always apply these via Modifier.size() rather than hardcoding a dp value.
//
// Usage:
//   Icon(modifier = Modifier.size(IconSize.md))   // toolbar icon
//   Icon(modifier = Modifier.size(IconSize.sm))   // inside a button
//
// ─────────────────────────────────────────────────────────────────────────────

object IconSize {
    val xxxs = 8.dp  // inline icons inside result stats
    val xxs = 13.dp  // close button icon, back arrow inside ghost overlay
    val xs = 16.dp  // inline icons inside chips and info pills
    val sm = 20.dp  // button icons (rocket, play, people), stat pill icons
    val md = 24.dp  // standard toolbar icons, category icons inside cards
    val lg = 32.dp  // large FAB icons
    val xl = 40.dp  // category icon containers in CategoryScreen rows
    val xxl = 48.dp  // quiz list card icon containers on HomeScreen
    val hero = 52.dp  // featured icon on QuizDetailScreen hero circle
}

// ─────────────────────────────────────────────────────────────────────────────
// COMPONENT SIZES
//
// Fixed dimensions for specific reusable UI components. Using named sizes
// keeps all instances of a component the same size and makes global size
// changes a one-line update.
//
// Usage:
//   Modifier.height(ComponentSize.buttonHeight)
//   Modifier.size(ComponentSize.avatarMedium)
//   Modifier.size(ComponentSize.timerCircle)
// ─────────────────────────────────────────────────────────────────────────────

object ComponentSize {

    // ── Buttons ───────────────────────────────────────────────────────────
    val buttonHeight = 52.dp  // standard height — Play Solo, outlined buttons
    val buttonHeightLarge = 56.dp  // large CTA — Start Quiz, Play with Friends, Next Question

    val backButtonSize = 40.dp   // ghost back button touch

    // ── Avatars ───────────────────────────────────────────────────────────
    val avatarSmall = 40.dp  // leaderboard row avatars
    val avatarMedium = 48.dp  // HomeScreen top bar avatar + icon button touch target
    val avatarLarge = 64.dp  // friend card avatars on HomeScreen
    val avatarHero = 80.dp  // profile screen avatar

    // ── Icon containers ───────────────────────────────────────────────────
    val iconContainerSmall = 40.dp   // category row icon box — CategoryScreen
    val iconContainerMedium = 48.dp   // quiz list card icon box — HomeScreen
    val iconContainerHero = 100.dp  // quiz detail hero circle — QuizDetailScreen

    // ── Quiz components ───────────────────────────────────────────────────
    val timerCircle = 56.dp  // animated countdown ring — QuizScreen
    val difficultyChipSquare = 90.dp  // square difficulty chip — CategoryScreen
    val answerBadge = 36.dp  // letter badge circle inside answer options (A / B / C / D)

    val pillIconSize = 14.dp  // small icons inside the close pill and score pill

    // ── Navigation ────────────────────────────────────────────────────────
    val navBarHeight = 80.dp  // bottom navigation bar total height
    val topBarHeight = 56.dp  // standard top app bar height

    // ── Utility ───────────────────────────────────────────────────────────
    val close = 28.5.dp  // close button touch target size

    // ── Result  ───────────────────────────────────────────────────────────
    val resultHeroHeight = 280.dp  // violet hero area on ResultsScreen
    val reviewBadge = 28.dp   // check/X circle on question review items

    // ── QuizDetail  ───────────────────────────────────────────────────────────

    val detailHeroHeight = 240.dp  // full-bleed illustration area at the top
    val detailHeroCardOverlap = 20.dp   // negative y-offset — card slides up over the hero

}