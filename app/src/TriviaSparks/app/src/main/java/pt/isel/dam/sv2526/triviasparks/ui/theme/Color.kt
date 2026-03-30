package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.ui.graphics.Color


// ── Primary: Soft Violet ──────────────────────
val Violet400      = Color(0xFF8B7FE8)   // primary
val Violet200      = Color(0xFFC1B9FF)   // primaryContainer
val Violet900      = Color(0xFF1A0E6B)   // onPrimaryContainer
val VioletDim      = Color(0xFF5B4FD4)   // inversePrimary

// ── Secondary: Lavender ───────────────────────
val Lavender300    = Color(0xFFB8A4FF)   // secondary
val Lavender800    = Color(0xFF2A1D80)   // onSecondary
val Lavender700    = Color(0xFF3D3399)   // secondaryContainer
val Lavender100    = Color(0xFFE6E0FF)   // onSecondaryContainer

// ── Tertiary: Mint Green ──────────────────────
val Mint300        = Color(0xFF8EEFCF)   // tertiary / tertiaryContainer
val MintDeep       = Color(0xFF003828)   // onTertiary / onTertiaryContainer

// ── Error / Strawberry ────────────────────────
val Strawberry400  = Color(0xFFF76A80)   // error
val Strawberry900  = Color(0xFF5C0020)   // onError
val Strawberry200  = Color(0xFFFFB3BC)   // errorContainer
val Strawberry950  = Color(0xFF410015)   // onErrorContainer

// ── Warning: Sunny Yellow ─────────────────────
val SunnyYellow    = Color(0xFFFFD166)   // warning (custom token)
val SunnyYellowOn  = Color(0xFF3D2900)   // onWarning (custom token)

// ── Coral Peach (accent) ──────────────────────
val CoralPeach     = Color(0xFFFFAD93)   // custom accent

// ── Surfaces: "Midnight Grape" hierarchy ─────
val MidnightGrape  = Color(0xFF13111C)   // background
val Surface0       = Color(0xFF1A1726)   // surface
val Surface1       = Color(0xFF1E1A2E)   // surfaceContainerLowest
val Surface2       = Color(0xFF231E33)   // surfaceContainerLow
val Surface3       = Color(0xFF2A2440)   // surfaceContainerHigh / surfaceContainer
val Surface4       = Color(0xFF312B4A)   // surfaceContainerHighest
val SurfaceVariant = Color(0xFF2C2640)   // surfaceVariant (sunken states)

// ── On-colors ────────────────────────────────
val OnBackground   = Color(0xFFE6E0FF)   // soft lavender-white
val OnSurface      = Color(0xFFE6E0FF)
val OnSurfaceVar   = Color(0xFFCAC4D0)

// ── Outline ──────────────────────────────────
val OutlineColor   = Color(0xFF6B5EAE)
// Ghost border: OutlineColor.copy(alpha = 0.15f) — use at call-site

// ── Inverse ──────────────────────────────────
val InverseSurface  = Color(0xFFE6E0FF)
val InverseOnSurface= Color(0xFF1A1726)

// ─────────────────────────────────────────────
//  LIGHT THEME TOKENS
//  Still "Dreamscape" — softer, airier, but
//  maintaining the violet/pastel identity.
// ─────────────────────────────────────────────

// ── Primary (light) ───────────────────────────
val VioletLight         = Color(0xFF5B4FD4)   // primary
val VioletLightOn       = Color(0xFFFFFFFF)   // onPrimary
val VioletLightContainer= Color(0xFFE6E1FF)   // primaryContainer
val VioletLightOnCont   = Color(0xFF1A0067)   // onPrimaryContainer

// ── Secondary (light) ─────────────────────────
val LavenderLight       = Color(0xFF5C539A)   // secondary
val LavenderLightOn     = Color(0xFFFFFFFF)
val LavenderLightCont   = Color(0xFFE6DEFF)   // secondaryContainer
val LavenderLightOnCont = Color(0xFF190063)

// ── Tertiary (light) ──────────────────────────
val MintLight           = Color(0xFF006C4E)   // tertiary
val MintLightOn         = Color(0xFFFFFFFF)
val MintLightCont       = Color(0xFF8EEFCF)   // tertiaryContainer (same pastel)
val MintLightOnCont     = Color(0xFF002116)

// ── Error (light) ─────────────────────────────
val StrawberryLight     = Color(0xFFB3003B)
val StrawberryLightOn   = Color(0xFFFFFFFF)
val StrawberryLightCont = Color(0xFFFFD9DF)
val StrawberryLightOnCont = Color(0xFF400011)

// ── Surfaces (light) ─────────────────────────
val LightBackground     = Color(0xFFFCF8FF)   // barely-there lavender white
val LightSurface        = Color(0xFFFCF8FF)
val LightSurface1       = Color(0xFFF2EEFF)   // surfaceContainerLowest
val LightSurface2       = Color(0xFFECE8F9)   // surfaceContainerLow
val LightSurface3       = Color(0xFFE6E0F3)   // surfaceContainer
val LightSurface4       = Color(0xFFDDD8EB)   // surfaceContainerHigh
val LightSurface5       = Color(0xFFD7D1E5)   // surfaceContainerHighest
val LightSurfaceVariant = Color(0xFFE6DEFF)   // sunken input fields

// ── On-colors (light) ─────────────────────────
val LightOnBackground   = Color(0xFF1A1726)   // Midnight Grape on white
val LightOnSurface      = Color(0xFF1A1726)
val LightOnSurfaceVar   = Color(0xFF3E3660)

// ── Outline (light) ───────────────────────────
val OutlineLight        = Color(0xFF7B6FA6)
val OutlineVariantLight = Color(0xFFCAC4D0)
