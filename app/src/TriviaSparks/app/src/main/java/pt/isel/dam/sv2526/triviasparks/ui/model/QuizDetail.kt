package pt.isel.dam.sv2526.triviasparks.ui.model

/**
 * Represents the full detail of a quiz shown on [QuizDetailScreen].
 *
 * Temporary UI model — lives in the screen package until Week 4/5.
 * In Week 4 it is populated from nav args passed from [CategoryScreen].
 * In Week 6 it is replaced by the domain Quiz model from the Open Trivia API.
 *
 * Wiki — data models reference:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/00-%E2%80%90-TriviaSparks-Game#data-models
 */
data class QuizDetail(
    val id: Int,             // Quiz ID — passed as nav arg in Week 4
    val title: String,       // e.g. "Quantum Physics Fun"
    val category: String,    // Uppercase label, e.g. "SCIENCE & NATURE"
    val questionCount: Int,  // Total questions available
    val xpReward: Int,       // XP awarded on completion
    val description: String, // "About this quiz" body text
    val difficulty: String   // "Easy" | "Medium" | "Hard" — default selected chip
)