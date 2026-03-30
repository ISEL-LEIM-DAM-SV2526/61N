package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────
//  Shapes
//
//  Philosophy: "Soft-Play" — ultra-fluid, large-radius containers.
//  Hard corners are forbidden. The corner radius communicates depth
//  and friendliness; as content becomes more important it gets rounder.
// ─────────────────────────────────────────────

val TriviaSparksShapes = Shapes(

    // extraSmall → Difficulty chips: use CircleShape at call-site
    extraSmall = RoundedCornerShape(4.dp),

    // small → Snackbars, Tooltips, compact badges
    small = RoundedCornerShape(8.dp),

    // medium → Input fields (sunken style), secondary buttons
    medium = RoundedCornerShape(12.dp),

    // large → Primary buttons (16dp per spec)
    large = RoundedCornerShape(16.dp),

    // extraLarge → Quiz Answer tiles (32dp per spec)
    extraLarge = RoundedCornerShape(32.dp),
)

// ── Named aliases (use these in component code) ──────────────────────

/** Primary buttons: 16dp */
val ShapeButton = RoundedCornerShape(16.dp)

/** Quiz cards / question containers: 32dp */
val ShapeCard = RoundedCornerShape(32.dp)

/** Bottom sheets / Modal surfaces: top corners only */
val ShapeBottomSheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)

/** Difficulty chips: fully-rounded pill */
val ShapeChip = RoundedCornerShape(percent = 50)

/** Input fields: medium radius for the "sunken" aesthetic */
val ShapeInput = RoundedCornerShape(12.dp)

/** HUD elements / floating overlays */
val ShapeHUD = RoundedCornerShape(20.dp)