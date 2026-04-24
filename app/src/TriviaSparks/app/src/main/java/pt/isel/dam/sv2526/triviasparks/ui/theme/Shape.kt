package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Trivia Sparks — Shape tokens.
 *
 * Every corner radius in the app is defined here. Shapes are one of the three
 * pillars of the Material3 theme (alongside colours and typography) — they
 * give the Dreamscape system its rounded, game-tile feel.
 *
 * **How shapes flow into the UI:**
 * ```
 * Shape.kt  →  Theme.kt (passed to MaterialTheme)  →  Composables
 *                     ↓
 *           MaterialTheme.shapes.*       (used by M3 components automatically)
 *           TriviaSparksRadius.*         (raw dp values — used in Modifier calls)
 *           CardShape / ChipShape / ...  (convenience objects — shorthand for composables)
 * ```
 *
 * **Which one should I use?**
 * - Inside a Material3 component (`Card`, `Button`, `Chip`) → `MaterialTheme.shapes.*`
 * - Inside `Modifier.clip()`, `Modifier.background()`, `Modifier.border()` → named shape
 *   object (`CardShape`, `ChipShape`, etc.)
 *
 * Wiki — shape token reference:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/00-%E2%80%90-TriviaSparks-Game#spacing--spacingkt
 *
 * Figma — shape tokens:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 */

// ─────────────────────────────────────────────────────────────────────────────
// MATERIAL3 SHAPES
//
// Passed to MaterialTheme in Theme.kt. M3 components pick their shape from
// this object automatically based on their role. You can override the default
// on any individual component:
//   Card(shape = MaterialTheme.shapes.extraLarge)   →  24dp corners
//
// Default M3 assignments (before our overrides):
//   extraSmall → Chip, Badge, AssistChip, SuggestionChip, InputChip
//   small      → Snackbar, Tooltip
//   medium     → Button, OutlinedButton, FloatingActionButton, TextField
//   large      → AlertDialog, ModalBottomSheet, NavigationDrawer
//   extraLarge → Card (M3 default is 12dp — we override to 24dp for the game-tile feel)
// ─────────────────────────────────────────────────────────────────────────────

val TriviaSparksShapes = Shapes(
    extraSmall = RoundedCornerShape(percent = 50), // pill — chips, badges, stat pills
    small      = RoundedCornerShape(8.dp),          // subtle — snackbars, tooltips
    medium     = RoundedCornerShape(16.dp),         // standard — buttons, text fields, FABs
    large      = RoundedCornerShape(20.dp),         // overlays — dialogs, bottom sheets
    extraLarge = RoundedCornerShape(24.dp)          // game tiles — main cards, quiz cards
)

// ─────────────────────────────────────────────────────────────────────────────
// RAW RADIUS TOKENS
//
// Use these when you need the raw dp value — typically inside Modifier calls
// where a RoundedCornerShape needs to be built manually:
//
//   Modifier.clip(RoundedCornerShape(TriviaSparksRadius.Card))
//   Modifier.background(color, RoundedCornerShape(TriviaSparksRadius.IconBox))
//
// Prefer the named shape objects below (CardShape, ChipShape, etc.) — they
// already wrap these values in a RoundedCornerShape, keeping composables cleaner.
// ─────────────────────────────────────────────────────────────────────────────

object TriviaSparksRadius {
    val Card      = 24.dp   // main content cards — quiz tiles, friend cards, quiz list cards
    val Button    = 16.dp   // all buttons — primary, outlined, text
    val Chip      = 99.dp   // fully pill — difficulty chips, badges, stat pills
    val IconBox   = 12.dp   // category icon containers — 40dp and 48dp boxes
    val SearchBar = 99.dp   // pill-shaped search input on CategoryScreen
    val Dialog    = 20.dp   // top corners of bottom sheet panels (bottom corners are 0dp)
    val Avatar    = 999.dp  // fully circular — profile photos, friend card avatars
    val Tag       = 8.dp    // small inline labels — version chips, small tags
}

// ─────────────────────────────────────────────────────────────────────────────
// NAMED SHAPE OBJECTS
//
// Convenience RoundedCornerShape instances — use these in composables instead
// of repeating dp values. Each maps directly to a TriviaSparksRadius value.
//
// Examples:
//   Modifier.clip(AvatarShape)
//   Modifier.background(color = iconTint.copy(0.15f), shape = IconBoxShape)
//   Card(shape = CardShape)
//   OutlinedTextField(shape = SearchBarShape)
// ─────────────────────────────────────────────────────────────────────────────

val CardShape      = RoundedCornerShape(TriviaSparksRadius.Card)       // 24dp — game tile cards
val ButtonShape    = RoundedCornerShape(TriviaSparksRadius.Button)     // 16dp — all buttons
val ChipShape      = RoundedCornerShape(TriviaSparksRadius.Chip)       // pill — difficulty chips, badges
val IconBoxShape   = RoundedCornerShape(TriviaSparksRadius.IconBox)    // 12dp — category icon containers
val SearchBarShape = RoundedCornerShape(TriviaSparksRadius.SearchBar)  // pill — search inputs
val AvatarShape    = RoundedCornerShape(TriviaSparksRadius.Avatar)     // circle — avatars

/**
 * Bottom sheet shape — only the top two corners are rounded.
 *
 * Used for panels that slide up from the bottom of the screen, where the
 * bottom corners must sit flush with the screen edge. Applied to:
 * - [QuizDetailScreen] — the white card panel that overlaps the hero illustration
 * - Modal bottom sheets and full-width overlays throughout the app
 *
 * Top corners: [TriviaSparksRadius.Dialog] (20dp)
 * Bottom corners: 0dp — flush with the screen edge
 *
 */
val BottomSheetShape = RoundedCornerShape(
    topStart    = TriviaSparksRadius.Dialog,
    topEnd      = TriviaSparksRadius.Dialog,
    bottomStart = 0.dp,
    bottomEnd   = 0.dp
)