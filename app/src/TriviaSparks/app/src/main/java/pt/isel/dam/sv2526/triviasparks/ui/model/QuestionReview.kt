package pt.isel.dam.sv2526.triviasparks.ui.model
/**
 * A single question entry in the review list.
 *
 * Temporary UI model — replaced by domain model in Week 5.
 *
 * Wiki — data models reference:
 * https://github.com/your-username/trivia-sparks/wiki/App-Trivia-Sparks#data-models
 */
data class QuestionReview(
    val number: Int,               // 1-based question number
    val text: String,              // The question text
    val userAnswer: String,        // What the player tapped
    val correctAnswer: String,     // The correct answer
    val isCorrect: Boolean         // true when userAnswer == correctAnswer
)
