package pt.isel.dam.sv2526.triviasparks.ui.preview
import pt.isel.dam.sv2526.triviasparks.ui.model.AnswerOption
import androidx.compose.ui.graphics.Color
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.model.Category
import pt.isel.dam.sv2526.triviasparks.ui.model.Friend
import pt.isel.dam.sv2526.triviasparks.ui.model.BottomNavItem
import pt.isel.dam.sv2526.triviasparks.ui.model.QuestionReview
import pt.isel.dam.sv2526.triviasparks.ui.model.QuizDetail
import pt.isel.dam.sv2526.triviasparks.ui.model.QuizItem
import pt.isel.dam.sv2526.triviasparks.ui.model.QuizResultSummary


// ─────────────────────────────────────────────────────────────────────────────
// STATIC SAMPLE DATA
// Used exclusively by @Preview functions and during Week 2 development.
// Never referenced in production code paths.
// Replaced by ViewModel + Repository in Week 5.
// ─────────────────────────────────────────────────────────────────────────────

val sampleFriends = listOf(
    Friend("João Afonso", "1.2k", R.drawable.ic_avatar5),
    Friend("Maria Marques",  "890",  R.drawable.ic_avatar3),
    Friend("Joana Fonseca",  "2.1k", R.drawable.ic_avatar6),
    Friend("Bruna Gonçalves",  "2.1k", R.drawable.ic_avatar2),
    Friend("Pedro Miguel",  "10", R.drawable.ic_avatar4)
)


// ─────────────────────────────────────────────────────────────────────────────
// STATIC SAMPLE DATA
// Used exclusively by @Preview functions and during Week 2 development.
// Replaced by ViewModel + Repository in Week 5.
// ─────────────────────────────────────────────────────────────────────────────

val sampleCategories = listOf(
    Category(17, "Science", 240, R.drawable.ic_science, Color(0xFF4A90D9)),
    Category(21, "Sports",     185, R.drawable.ic_sports,     Color(0xFFFF8C69)),
    Category(23, "History",    120, R.drawable.ic_history,    Color(0xFFFFD166)),
    Category(28, "Technology", 310, R.drawable.ic_computer, Color(0xFF8B7FE8)),
    Category(25, "Art",         95, R.drawable.ic_art,        Color(0xFFFF6B8A))
)

// ─────────────────────────────────────────────────────────────────────────────
// STATIC SAMPLE DATA
// Used exclusively by @Preview functions and during Week 2 development.
// Replaced by ViewModel + Repository in Week 5.
// ─────────────────────────────────────────────────────────────────────────────

val sampleQuizItems = listOf(
    QuizItem("id1", "Science", "15 Questions • Advanced", R.drawable.ic_science, Color(0xFF4A90D9)),
    QuizItem("id2", "Sports", "15 Questions • Hard", R.drawable.ic_sports, Color(0xFFFF8C69)),
    QuizItem("id3", "Art", "15 Questions • Easy", R.drawable.ic_art, Color(0xFFFF6B8A)),
    QuizItem("id4", "Tv", "15 Questions • Advanced", R.drawable.ic_tv,Color(0xFFFF9F43)),
    QuizItem("id5", "Book", "15 Questions • Easy", R.drawable.ic_book,Color(0xFFFFD166))
)


val bottomNavItems = listOf(
    BottomNavItem(
        route = "home",
        label = "HOME",
        iconSelectedRes = R.drawable.ic_home_seleted,
        iconUnselectedRes = R.drawable.ic_home_seleted
    ),
    BottomNavItem(
        route             = "quiz",
        label             = "QUIZ",
        iconSelectedRes   = R.drawable.ic_quiz_unselected,
        iconUnselectedRes = R.drawable.ic_quiz_unselected
    ),
    BottomNavItem(
        route             = "leaderboards",
        label             = "LEADERBOARDS",
        iconSelectedRes   = R.drawable.ic_leadboard_unselected,
        iconUnselectedRes = R.drawable.ic_leadboard_unselected
    ),
    BottomNavItem(
        route = "profile",
        label = "PROFILE",
        iconSelectedRes = R.drawable.ic_profile_unselected,
        iconUnselectedRes = R.drawable.ic_profile_unselected
    )
)

val sampleQuizDetail = QuizDetail(
    id = 1,
    title = "Quantum Physics Fun",
    category = "SCIENCE & NATURE",
    questionCount = 15,
    xpReward = 250,
    description = "Dive into the fascinating world of quantum mechanics! " +
            "Explore particles, waves, and the mysteries of the " +
            "subatomic world in this playful challenge.",
    difficulty = "Easy"
)


val sampleAnswers = listOf(
    AnswerOption("A", "Orion Nebula"),
    AnswerOption("B", "Crab Nebula"),
    AnswerOption("C", "Eagle Nebula"),
    AnswerOption("D", "Helix Nebula")
)


val sampleSummary = QuizResultSummary(
    totalScore       = 2450,
    scorePercentage  = 85,
    correctCount     = 17,
    wrongCount       = 3,
    timeTakenSeconds = 84   // 1:24
)

val sampleReview = listOf(
    QuestionReview(1, "What is the capital city of France?",
        "Paris", "Paris", isCorrect = true),
    QuestionReview(2, "Which planet is known as the Red Planet?",
        "Venus", "Mars", isCorrect = false),
    QuestionReview(3, "Who painted the Mona Lisa?",
        "Leonardo da Vinci", "Leonardo da Vinci", isCorrect = true)
)
