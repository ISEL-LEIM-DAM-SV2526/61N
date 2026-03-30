package pt.isel.dam.sv2526.triviasparks.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import pt.isel.dam.sv2526.triviasparks.R


val PlusJakartaSans = FontFamily(
    Font(R.font.plus_jakarta_sans_regular,  FontWeight.Normal),
    Font(R.font.plus_jakarta_sans_medium,   FontWeight.Medium),
    Font(R.font.plus_jakarta_sans_semibold, FontWeight.SemiBold),
    Font(R.font.plus_jakarta_sans_bold,     FontWeight.Bold),
    Font(R.font.plus_jakarta_sans_extrabold,FontWeight.ExtraBold),
)

val Inter = FontFamily(
    Font(R.font.inter_regular,  FontWeight.Normal),
    Font(R.font.inter_medium,   FontWeight.Medium),
    Font(R.font.inter_semibold, FontWeight.SemiBold),
    Font(R.font.inter_bold,     FontWeight.Bold),
)


// ── Typography Scale ─────────────────────────────────────────
// Based on Design System spec:
// Display  → Plus Jakarta Sans (scores, victory)
// Headline → Plus Jakarta Sans (question text, screen titles)
// Body     → Inter (answer options, descriptions)
// Label    → Inter (difficulty tags, metadata)

val TriviaSparksTypography = Typography(

    // ── Display: Score & Victory screens (3.5rem / 56sp) ─────────────
    displayLarge = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight  = FontWeight.Bold,
        fontSize    = 56.sp,
        lineHeight  = 64.sp,
        letterSpacing = (-0.25).sp,
    ),
    displayMedium = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight  = FontWeight.Bold,
        fontSize    = 45.sp,
        lineHeight  = 52.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 36.sp,
        lineHeight  = 44.sp,
    ),

    // ── Headlines: Question text (2rem / 32sp) ───────────────────────
    headlineLarge = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight  = FontWeight.Bold,
        fontSize    = 32.sp,
        lineHeight  = 40.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 28.sp,
        lineHeight  = 36.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 24.sp,
        lineHeight  = 32.sp,
    ),

    // ── Titles: Section headers, card titles ─────────────────────────
    titleLarge = TextStyle(
        fontFamily = Inter,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 22.sp,
        lineHeight  = 28.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Inter,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 16.sp,
        lineHeight  = 24.sp,
        letterSpacing = 0.15.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = Inter,
        fontWeight  = FontWeight.Medium,
        fontSize    = 14.sp,
        lineHeight  = 20.sp,
        letterSpacing = 0.1.sp,
    ),

    // ── Body: Answer options (1rem / 16sp) ───────────────────────────
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight  = FontWeight.Normal,
        fontSize    = 16.sp,
        lineHeight  = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight  = FontWeight.Normal,
        fontSize    = 14.sp,
        lineHeight  = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight  = FontWeight.Normal,
        fontSize    = 12.sp,
        lineHeight  = 16.sp,
        letterSpacing = 0.4.sp,
    ),

    // ── Labels: Difficulty chips & metadata (0.75rem / 12sp) ─────────
    labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 14.sp,
        lineHeight  = 20.sp,
        letterSpacing = 0.1.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = Inter,
        fontWeight  = FontWeight.SemiBold,
        fontSize    = 12.sp,      // 0.75rem — design spec value
        lineHeight  = 16.sp,
        letterSpacing = 0.5.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight  = FontWeight.Medium,
        fontSize    = 11.sp,
        lineHeight  = 16.sp,
        letterSpacing = 0.5.sp,
    )
)