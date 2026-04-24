package pt.isel.dam.sv2526.triviasparks.ui.model

import androidx.compose.ui.graphics.Color

/**
 * Represents a quiz entry displayed in the Latest Quizzes vertical list.
 *
 * This is a temporary UI model living in the screen package.
 * In Week 6 it will be replaced by the domain Quiz model from the Open Trivia API.
 *
 * Wiki — data models reference:
 * https://github.com/your-username/trivia-sparks/wiki/App-Trivia-Sparks#data-models
 */
data class QuizItem(
    val id: String,         // Quiz id, e.g "1919191"
    val title: String,      // Quiz title, e.g. "Quantum Physics"
    val subtitle: String,   // Formatted as "{count} Questions • {difficulty}"
    val iconRes: Int,       // Category icon drawable resource ID
    val iconTint: Color     // Category accent colour — see Dreamscape token table
)