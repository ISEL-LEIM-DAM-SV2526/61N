package pt.isel.dam.sv2526.triviasparks.ui.model
/**
 * A single question entry in the review list.
 *
 * Temporary UI model — replaced by domain model in Week 5.
 *
 * Wiki — data models reference:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/00-%E2%80%90-TriviaSparks-Game#data-models
 */
data class QuestionReview(
    val number: Int,               // 1-based question number
    val text: String,              // The question text
    val userAnswer: String,        // What the player tapped
    val correctAnswer: String,     // The correct answer
    val isCorrect: Boolean         // true when userAnswer == correctAnswer
)
