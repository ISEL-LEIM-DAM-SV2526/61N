package pt.isel.dam.sv2526.triviasparks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.model.QuizResultSummary
import pt.isel.dam.sv2526.triviasparks.ui.screens.category.CategoryScreen
import pt.isel.dam.sv2526.triviasparks.ui.screens.home.HomeScreen
import pt.isel.dam.sv2526.triviasparks.ui.screens.quiz.game.QuizScreen
import pt.isel.dam.sv2526.triviasparks.ui.screens.quiz.detail.QuizDetailScreen
import pt.isel.dam.sv2526.triviasparks.ui.screens.quiz.result.ResultsScreen
import pt.isel.dam.sv2526.triviasparks.ui.screens.placeholder.PlaceholderScreen

/**
 * Root navigation graph for Trivia Sparks.
 *
 * All navigation decisions live here — screen composables only receive lambda
 * callbacks and have no direct knowledge of [NavController].
 *
 * **Current destinations:**
 * ```
 * home                                   ← HomeScreen
 * category                               ← CategoryScreen
 * quiz_detail/{categoryId}               ← QuizDetailScreen
 * quiz/{categoryId}/{difficulty}         ← QuizScreen
 * results/{...}                          ← ResultsScreen
 * leaderboard / history / profile        ← PlaceholderScreen
 * ```
 *
 * Wiki — navigation graph:
 * https://github.com/your-username/trivia-sparks/wiki/Week-3-4#appnavgraph
 *
 * @param navController  The [NavHostController] created in [MainActivity].
 * @param modifier       Applied to the [NavHost].
 */
@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // currentBackStackEntryAsState — reads the current destination reactively.
    // Used to drive selectedRoute in AppBottomBar so the correct tab is highlighted.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute      = navBackStackEntry?.destination?.route ?: Routes.HOME

    NavHost(
        navController    = navController,
        startDestination = Routes.HOME,
        modifier         = modifier
    ) {

        // ── HOME ──────────────────────────────────────────────────────────
        composable(Routes.HOME) {
            HomeScreen(
                onStartQuiz         = { navController.navigate(Routes.CATEGORY) },
                onSeeAllFriends     = { /* TODO(Week 9): friends list */ },
                onSeeAllQuizzes     = { /* TODO(Week 6): quiz browser */ },
                onQuizClick         = { /* TODO(Week 6): quiz detail */ },
                onNotificationClick = { /* TODO(Week 8): notifications */ },
                onNavSelected       = { route -> navController.navigateTab(route) },
                // currentRoute drives the selected tab highlight
                selectedRoute       = currentRoute
            )
        }

        // ── CATEGORY ──────────────────────────────────────────────────────
        composable(Routes.CATEGORY) {
            CategoryScreen(
                onClose         = { navController.popBackStack() },
                onCategoryClick = { categoryId ->
                    navController.navigate(Routes.quizDetail(categoryId))
                },
                onStartQuiz     = { /* TODO: needs categoryId + difficulty from CategoryScreen state */ }
            )
        }

        // ── QUIZ DETAIL ───────────────────────────────────────────────────
        composable(
            route     = Routes.QUIZ_DETAIL,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0

            QuizDetailScreen(
                onBack            = { navController.popBackStack() },
                // onPlaySolo now receives the difficulty the player selected
                onPlaySolo        = { difficulty ->
                    navController.navigate(Routes.quiz(categoryId, difficulty))
                },
                onPlayWithFriends = { /* TODO(Week 11): Multiplayer */ }
            )
        }

        // ── QUIZ ──────────────────────────────────────────────────────────
        composable(
            route     = Routes.QUIZ,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull() ?: 0
            val difficulty = backStackEntry.arguments?.getString("difficulty") ?: "easy"

            QuizScreen(
                onClose       = {
                    navController.popBackStack(Routes.CATEGORY, inclusive = false)
                },
                onNextQuestion = {
                    // TODO(Week 5): ViewModel decides if this is the last question.
                    // When it is the last question, call:
                    // navController.navigate(Routes.results(categoryId, difficulty, score, correct, wrong, time))
                }
            )
        }

        // ── RESULTS ───────────────────────────────────────────────────────
        composable(
            route     = Routes.RESULTS,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType },
                navArgument("difficulty") { type = NavType.StringType },
                navArgument("score")      { type = NavType.IntType },
                navArgument("correct")    { type = NavType.IntType },
                navArgument("wrong")      { type = NavType.IntType },
                navArgument("time")       { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val args       = backStackEntry.arguments
            val categoryId = args?.getInt("categoryId") ?: 0
            val difficulty = args?.getString("difficulty") ?: "easy"
            val score      = args?.getInt("score") ?: 0
            val correct    = args?.getInt("correct") ?: 0
            val wrong      = args?.getInt("wrong") ?: 0
            val time       = args?.getInt("time") ?: 0

            ResultsScreen(
                summary = QuizResultSummary(
                    totalScore       = score,
                    scorePercentage  = if (correct + wrong > 0)
                        (correct * 100) / (correct + wrong) else 0,
                    correctCount     = correct,
                    wrongCount       = wrong,
                    timeTakenSeconds = time
                ),
                onClose    = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = false }
                    }
                },
                onTryAgain = {
                    navController.popBackStack()
                    navController.navigate(Routes.quiz(categoryId, difficulty))
                },
                onShare    = { /* TODO(Week 8): ShareCompat intent */ }
            )
        }

        // ── BOTTOM NAV PLACEHOLDERS ───────────────────────────────────────

        composable(Routes.LEADERBOARD) {
            PlaceholderScreen(
                title         = "Leaderboard",
                description   = "See how you rank against players worldwide.",
                comingIn      = "Week 9",
                iconRes       = R.drawable.ic_trophy,
                selectedRoute = Routes.LEADERBOARD,
                onNavSelected = { route -> navController.navigateTab(route) }
            )
        }
        composable(Routes.HISTORY) {
            PlaceholderScreen(
                title         = "History",
                description   = "Review all your past quizzes and scores.",
                comingIn      = "Week 7",
                iconRes       = R.drawable.ic_clock,
                selectedRoute = Routes.HISTORY,
                onNavSelected = { route -> navController.navigateTab(route) }
            )
        }
        composable(Routes.PROFILE) {
            PlaceholderScreen(
                title         = "Profile",
                description   = "Manage your account, stats and preferences.",
                comingIn      = "Week 8",
                iconRes       = R.drawable.ic_flame,
                selectedRoute = Routes.PROFILE,
                onNavSelected = { route -> navController.navigateTab(route) }
            )
        }
        composable(Routes.QUIZS) {
            PlaceholderScreen(
                title         = "Quiz List",
                description   = "Show all the quizz played",
                comingIn      = "Week 8",
                iconRes       = R.drawable.ic_questions,
                selectedRoute = Routes.QUIZS,
                onNavSelected = { route -> navController.navigateTab(route) }
            )
        }
    }
}