package pt.isel.dam.sv2526.triviasparks.domain.model
import androidx.compose.ui.graphics.Color

/**
 * Represents a quiz category displayed in the Category screen list.
 *
 * This is a temporary UI model living in the screen package.
 * In Week 6 it will be replaced by the domain Category model
 * populated from the Open Trivia Database API.
 *
 * Wiki — data models reference:
 * https://github.com/your-username/trivia-sparks/wiki/App-Trivia-Sparks#data-models
 */
data class Category(
    val id: Int,              // Open Trivia Database category ID (9–32)
    val name: String,         // Human-readable name, e.g. "Science"
    val questionCount: Int,   // Total available questions for this category
    val iconRes: Int,         // Drawable resource ID — replace the IDs from your drawables
    val iconTint: Color       // Category accent colour — see Dreamscape category token table
)
