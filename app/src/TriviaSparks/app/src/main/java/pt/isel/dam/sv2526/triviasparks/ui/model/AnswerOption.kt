package pt.isel.dam.sv2526.triviasparks.ui.screens.model

/**
 * Represents a single answer option displayed on the Quiz screen.
 *
 * This is a temporary UI model. In Week 5 the answer options will be derived
 * from the domain [Question] model (correctAnswer + incorrectAnswers shuffled).
 *
 * Wiki — data models reference:
 * https://github.com/your-username/trivia-sparks/wiki/App-Trivia-Sparks#data-models
 */
data class AnswerOption(
    val letter: String,  // "A" | "B" | "C" | "D" — derived from index in production
    val text: String     // Answer text, HTML-decoded from the API (see toHtmlDecoded())
)