package pt.isel.dam.sv2526.triviasparks.ui.model

/**
 * Summary data for the quiz result hero section.
 *
 * Temporary UI model — replaced by the domain [QuizResult] model in Week 5.
 *
 * Wiki — data models reference:
 * https://github.com/your-username/trivia-sparks/wiki/App-Trivia-Sparks#data-models
 */
data class QuizResultSummary(
    val totalScore: Int,           // Raw score, e.g. 2450
    val scorePercentage: Int,      // 0–100 — drives star rating
    val correctCount: Int,         // Number of correct answers
    val wrongCount: Int,           // Number of wrong answers
    val timeTakenSeconds: Int      // Total session time — formatted as "m:ss"
)
