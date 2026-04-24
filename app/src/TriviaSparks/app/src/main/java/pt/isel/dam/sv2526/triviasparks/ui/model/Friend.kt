package pt.isel.dam.sv2526.triviasparks.ui.model

/**
 * Represents a friend entry displayed in the Recent Friends horizontal row.
 *
 * This is a temporary UI model living in the screen package.
 * In Week 9 it will be replaced by the domain model populated from Firestore.
 *
 * Wiki — data models reference:
 * https://github.com/your-username/trivia-sparks/wiki/App-Trivia-Sparks#data-models
 */
data class Friend(
    val name: String,       // Display name shown below the avatar
    val score: String,      // Formatted score string, e.g. "1.2k" or "890"
    val avatarRes: Int      // Drawable resource ID — replace with URL in Week 9
)
