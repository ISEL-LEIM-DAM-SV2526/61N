package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Trivia Sparks — Colour tokens.
 *
 * Every colour in the app is defined here as a named constant.
 * Nothing else in the codebase should contain a hardcoded colour value —
 * always reference one of these tokens, either directly or through
 * [MaterialTheme.colorScheme] and [MaterialTheme.triviasparks].
 *
 * **How colours flow into the UI:**
 * ```
 * Color.kt  →  Theme.kt (mapped to M3 roles)  →  Composables
 *                     ↓
 *           MaterialTheme.colorScheme.*   (standard M3 roles)
 *           MaterialTheme.triviasparks.*  (extended Dreamscape tokens)
 * ```
 *
 * Design reference — Figma:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 *
 * Wiki — Colour:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/00-%E2%80%90-TriviaSparks-Game#spacing--spacingkt
 *
 */

// ─────────────────────────────────────────────────────────────────────────────
// PRIMARY — SOFT VIOLET
//
// The core brand colour. Used for primary buttons, active navigation tabs,
// links, selected states, and the "Trivia Sparks" title in the top bar.
//
// MaterialTheme role → colorScheme.primary
// ─────────────────────────────────────────────────────────────────────────────

val Violet400 = Color(0xFF8B7FE8) // primary — buttons, active states, links
val Violet600 = Color(0xFF6C5FD6) // primary pressed / darker variant on tap
val Violet100 = Color(0xFFD4CFFA) // primary container — selected chip backgrounds
val Violet200 = Color(0xFFAFA9EC) // primary container mid — subtle fills
val Violet800 = Color(0xFF3C3489) // deep CTA — used on Start Quiz and Play with Friends buttons
val Violet900 = Color(0xFF2A2260) // deepest brand tone — rarely used directly

// ─────────────────────────────────────────────────────────────────────────────
// SECONDARY — CORAL PEACH
//
// Supporting accent colour. Used for XP reward badges, the score pill in the
// quiz top bar, secondary action buttons, and the score stat pill on HomeScreen.
//
// MaterialTheme role → colorScheme.secondary
// ─────────────────────────────────────────────────────────────────────────────

val Coral400 = Color(0xFFFF8C69) // secondary — XP badges, score pill, secondary CTAs
val Coral600 = Color(0xFFE06A46) // secondary pressed
val Coral100 = Color(0xFFFFD9CD) // secondary container (light)
val Coral900 = Color(0xFF5A2210) // on secondary container (dark text)

// ─────────────────────────────────────────────────────────────────────────────
// DIFFICULTY / SEMANTIC
//
// These three colours represent difficulty levels throughout the app and double
// as semantic colours for success, warning, and error states respectively.
//
// Each difficulty has three values:
//   base colour      → used for borders, icons, and fills in dark mode
//   onLight variant  → darker shade for TEXT on white/light backgrounds (WCAG AA)
//   container        → very light fill for chip backgrounds
//
// ⚠️  Never use SunnyYellow with white text — it fails contrast on light surfaces.
//     Always use SunnyYellowOnLight (#B8922A) for text on yellow backgrounds.
//
// MaterialTheme roles → colorScheme.tertiary (easy), colorScheme.error (hard)
// Extended tokens     → MaterialTheme.triviasparks.easy / medium / hard
//
// ─────────────────────────────────────────────────────────────────────────────

val MintGreen            = Color(0xFF6ECFB0) // easy difficulty / success states
val MintGreenOnLight     = Color(0xFF3AAB87) // easy text on white backgrounds
val MintGreenContainer   = Color(0xFFBFF5E5) // easy chip background fill

val SunnyYellow          = Color(0xFFFFD166) // medium difficulty / warning states
val SunnyYellowOnLight   = Color(0xFFB8922A) // medium text on white backgrounds ⚠️ required for contrast
val SunnyYellowContainer = Color(0xFFFFF0BA) // medium chip background fill

val Strawberry           = Color(0xFFFF6B8A) // hard difficulty / error states
val StrawberryOnLight    = Color(0xFFC42050) // hard text on white backgrounds
val StrawberryContainer  = Color(0xFFFFD6E0) // hard chip background fill

// ─────────────────────────────────────────────────────────────────────────────
// BACKGROUNDS
//
// The screen background — the canvas behind all cards and content.
// Light mode uses a soft lavender tint (not plain white) to give the app
// a dreamlike game identity. Dark mode uses a deep purple-black.
//
// MaterialTheme role → colorScheme.background
// ─────────────────────────────────────────────────────────────────────────────

val LavenderMist  = Color(0xFFF5F3FF) // light mode — soft lavender tint
val MidnightGrape = Color(0xFF12101F) // dark mode — deep purple-black

// ─────────────────────────────────────────────────────────────────────────────
// SURFACES (CARDS / PANELS)
//
// Card and panel backgrounds. Cards sit on top of the background and create
// the "game tile" layering central to the Dreamscape aesthetic.
// The variant colours are used for inner sections, stat pills, and tinted
// areas within a card.
//
// MaterialTheme roles → colorScheme.surface / colorScheme.surfaceVariant
// ─────────────────────────────────────────────────────────────────────────────

val SurfaceLight        = Color(0xFFFFFFFF) // light card — pure white
val SurfaceVariantLight = Color(0xFFF0EEFF) // inner sections, stat pills (light)

val SurfaceDark         = Color(0xFF1E1B35) // dark card — violet tile
val SurfaceVariantDark  = Color(0xFF252245) // inner sections, stat pills (dark)

// ─────────────────────────────────────────────────────────────────────────────
// TEXT
//
// Primary text is used for headings and main content.
// Secondary (muted) text is used for subtitles, captions, and section labels.
//
// Always use these through colorScheme roles in composables —
// never reference DeepGrape or SoftLavender directly in the UI.
//
// MaterialTheme roles → colorScheme.onBackground / colorScheme.onSurfaceVariant
// ─────────────────────────────────────────────────────────────────────────────

val DeepGrape    = Color(0xFF1E1642) // primary text — light mode
val DustyViolet  = Color(0xFF7B7599) // secondary text — light mode

val SoftLavender = Color(0xFFEDE9FF) // primary text — dark mode
val MutedViolet  = Color(0xFF9E9BBF) // secondary text — dark mode

// ─────────────────────────────────────────────────────────────────────────────
// BORDERS / DIVIDERS
//
// Used for card outlines, section separators, and chip borders.
// The subtle variants are for internal card dividers where a very light line
// is needed without adding visual weight.
//
// MaterialTheme role → colorScheme.outline / colorScheme.outlineVariant
// ─────────────────────────────────────────────────────────────────────────────

val LilacBorder           = Color(0xFFD8D4F0) // card borders (light mode)
val LilacBorderSubtle     = Color(0xFFEBE8F8) // dividers within cards (light mode)
val DarkLilacBorder       = Color(0xFF3D3860) // card borders (dark mode)
val DarkLilacBorderSubtle = Color(0xFF2A2750) // dividers within cards (dark mode)

// ─────────────────────────────────────────────────────────────────────────────
// CATEGORY COLOURS
//
// Each quiz category has a fixed accent colour used for its icon container
// background and icon tint. The pattern is always:
//   container background → accent at 15% opacity
//   icon                 → accent at 100% opacity
//
// Accessed via MaterialTheme.triviasparks.category* extended tokens.
// Do not reference these directly in composables — always go through the theme.
//
// ─────────────────────────────────────────────────────────────────────────────

val CategoryScience    = Color(0xFF4A90D9) // blue
val CategorySports     = Coral400          // coral — reuses secondary token
val CategoryHistory    = SunnyYellow       // yellow — reuses medium difficulty token
val CategoryTechnology = Violet400         // violet — reuses primary token
val CategoryArt        = Strawberry        // pink — reuses hard difficulty token
val CategoryMusic      = MintGreen         // mint — reuses easy difficulty token
val CategoryFilm       = Color(0xFFFF9F43) // orange
val CategoryGeography  = Color(0xFF48DBFB) // teal

// ─────────────────────────────────────────────────────────────────────────────
// UTILITY
//
// Generic values used for overlays, icon tints, and system-level needs.
// Transparent is used for invisible backgrounds on borderless components.
// ─────────────────────────────────────────────────────────────────────────────

val White       = Color(0xFFFFFFFF)
val Black       = Color(0xFF000000)
val Transparent = Color(0x00000000)

// ─────────────────────────────────────────────────────────────────────────────
// SCRIM / OVERLAY
//
// Semi-transparent overlays for modal backgrounds, bottom sheets, and
// dialog dimming. Dark mode uses a stronger alpha (80%) because the already-dark
// background needs more dimming to create visible depth separation.
// ─────────────────────────────────────────────────────────────────────────────

val ScrimLight = Color(0x991E1642) // 60% opacity — modal overlay (light mode)
val ScrimDark  = Color(0xCC12101F) // 80% opacity — modal overlay (dark mode)