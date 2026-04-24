package pt.isel.dam.sv2526.triviasparks.ui.model

/**
 * Summary data for the quiz result hero section.
 *
 * Temporary UI model — replaced by the domain [QuizResult] model in Week 5.
 *
 * Wiki — data models reference:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/00-%E2%80%90-TriviaSparks-Game#data-models
 */
data class QuizResultSummary(
    val totalScore: Int,           // Raw score, e.g. 2450
    val scorePercentage: Int,      // 0–100 — drives star rating
    val correctCount: Int,         // Number of correct answers
    val wrongCount: Int,           // Number of wrong answers
    val timeTakenSeconds: Int      // Total session time — formatted as "m:ss"
)
