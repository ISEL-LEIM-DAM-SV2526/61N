package pt.isel.dam.sv2526.triviasparks.ui.navigation

/**
 * All navigation route constants for Trivia Sparks.
 *
 * Routes are plain strings. Arguments are embedded using `{placeholder}` syntax
 * and extracted with [androidx.navigation.NavBackStackEntry.arguments] at the destination.
 *
 * **Rule:** never write a route string directly in a composable or `NavHost`.
 * Always use these constants or the builder functions below.
 *
 * **Route overview:**
 * ```
 * bottom nav tabs  → home, leaderboard, history, profile
 * quiz flow        → category → quiz_detail/{categoryId} → quiz/{categoryId}/{difficulty}
 *                             → results/{score}/{correct}/{wrong}/{time}
 * auth flow        → login, register
 * ```
 *
 * Wiki — navigation routes:
 * https://github.com/your-username/trivia-sparks/wiki/Week-3-4#routes
 */
object Routes {

    // ── Bottom navigation tabs ────────────────────────────────────────────
    const val HOME        = "home"
    const val LEADERBOARD = "leaderboard"
    const val HISTORY     = "history"
    const val PROFILE     = "profile"

    // ── Quiz flow ─────────────────────────────────────────────────────────
    const val CATEGORY    = "category"

    const val QUIZ_DETAIL = "quiz_detail/{categoryId}"
    const val QUIZ        = "quiz/{categoryId}/{difficulty}"

    const val QUIZS        = "quiz"
    const val RESULTS     = "results/{categoryId}/{difficulty}/{score}/{correct}/{wrong}/{time}"

    // ── Auth flow ─────────────────────────────────────────────────────────
    // TODO(Week 8): auth graph added when Firebase Auth is introduced
    const val LOGIN    = "login"
    const val REGISTER = "register"

    // ── Route builder functions ───────────────────────────────────────────
    // Use these to construct argument-embedded route strings at the call site.
    // Example: navController.navigate(Routes.quizDetail(categoryId = 17))

    /**
     * Builds the route for [QUIZ_DETAIL] with the given category ID embedded.
     * @param categoryId Open Trivia Database category ID (9–32).
     */
    fun quizDetail(categoryId: Int): String = "quiz_detail/$categoryId"

    /**
     * Builds the route for [QUIZ] with category ID and difficulty embedded.
     * @param categoryId Open Trivia Database category ID (9–32).
     * @param difficulty "easy" | "medium" | "hard" — always lowercase.
     */
    fun quiz(categoryId: Int, difficulty: String): String =
        "quiz/$categoryId/$difficulty"

    /**
     * Builds the route for [RESULTS] with all session data embedded.
     * All values are passed as route args so the results screen is fully
     * self-contained and can be deep-linked without ViewModel state.
     *
     * @param categoryId  Category ID — used by Try Again to restart the same quiz.
     * @param difficulty  Difficulty string — used by Try Again.
     * @param score       Final score value.
     * @param correct     Number of correct answers.
     * @param wrong       Number of wrong answers.
     * @param time        Session duration in seconds.
     */
    fun results(
        categoryId: Int,
        difficulty: String,
        score: Int,
        correct: Int,
        wrong: Int,
        time: Int
    ): String = "results/$categoryId/$difficulty/$score/$correct/$wrong/$time"
}