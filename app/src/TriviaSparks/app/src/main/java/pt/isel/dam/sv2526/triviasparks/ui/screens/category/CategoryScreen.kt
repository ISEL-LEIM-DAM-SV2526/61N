package pt.isel.dam.sv2526.triviasparks.ui.screens.category

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import pt.isel.dam.sv2526.triviasparks.R
import pt.isel.dam.sv2526.triviasparks.ui.model.Category
import pt.isel.dam.sv2526.triviasparks.ui.model.DifficultyOption
import pt.isel.dam.sv2526.triviasparks.ui.preview.sampleCategories
import pt.isel.dam.sv2526.triviasparks.ui.component.AppSearchBar
import pt.isel.dam.sv2526.triviasparks.ui.component.DifficultyChip
import pt.isel.dam.sv2526.triviasparks.ui.component.ListItemCard
import pt.isel.dam.sv2526.triviasparks.ui.theme.ButtonShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme
import pt.isel.dam.sv2526.triviasparks.ui.theme.Violet800
import pt.isel.dam.sv2526.triviasparks.ui.theme.triviasparks

/**
 * Quiz Setup screen — player selects a difficulty and a category before starting.
 *
 * **Week 3 — state now live:**
 *
 * | State | Type | What it drives |
 * |---|---|---|
 * | `selectedDifficulty` | `mutableStateOf("Easy")` | Which chip is highlighted |
 * | `searchQuery` | `mutableStateOf("")` | Search bar text |
 * | `filteredCategories` | `derivedStateOf` | Visible category list |
 *
 * **`derivedStateOf` — why not just filter inside the composable?**
 * Without `derivedStateOf`, the filter runs on every recomposition regardless
 * of cause. With it, the filtered list only recomputes when `searchQuery`
 * or `categories` actually changes. For a large category list this matters.
 *
 * **What is still static and when it becomes live:**
 *
 * | Element | Current state | Becomes live |
 * |---|---|---|
 * | `categories` | `sampleCategories` mock | Week 6 — Open Trivia Database API |
 * | `onClose` | empty lambda | Week 4 — `NavController.popBackStack()` |
 * | `onStartQuiz` | empty lambda | Week 4 — navigate to `QuizScreen` |
 * | `onCategoryClick` | empty lambda | Week 4 — navigate to `QuizDetailScreen` |
 *
 * Figma design:
 * https://www.figma.com/file/your-figma-link/Trivia-Sparks?node-id=category-screen
 *
 * Wiki — Week 3 CategoryScreen state:
 * https://github.com/your-username/trivia-sparks/wiki/Week-3-4#categoryscreen--difficulty-chips--live-search
 *
 * @param categories        List of available categories.
 *                          TODO(Week 6): loaded from Open Trivia Database API.
 * @param onClose           Called when the user taps the X button.
 *                          TODO(Week 4): `NavController.popBackStack()`.
 * @param onCategoryClick   Called when the user taps a category row.
 *                          TODO(Week 4): navigate to `QuizDetailScreen`.
 * @param onStartQuiz       Called when the user taps Start Quiz.
 *                          TODO(Week 4): navigate to `QuizScreen` with selected args.
 */
@Composable
fun CategoryScreen(
    categories: List<Category>     = sampleCategories,  // TODO(Week 6): API data
    onClose: () -> Unit            = {},                 // TODO(Week 4): popBackStack()
    onCategoryClick: (Int) -> Unit = {},                 // TODO(Week 4): navigate to QuizDetail
    onStartQuiz: () -> Unit        = {}                  // TODO(Week 4): navigate to QuizScreen
) {
    // ── Week 3 state ───────────────────────────────────────────────────────

    // Difficulty chip selection — hoisted here so DifficultySection is stateless.
    var selectedDifficulty by remember { mutableStateOf("Easy") }

    // Search bar text — updated on every keystroke.
    var searchQuery by remember { mutableStateOf("") }

    // Derived state — the filtered list only recomputes when searchQuery changes.
    // Without derivedStateOf, the filter would run on every recomposition
    // regardless of what caused it.
    val filteredCategories by remember {
        derivedStateOf {
            if (searchQuery.isBlank()) categories
            else categories.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier       = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = Spacing.lg)
            ) {

                // ── Top bar ────────────────────────────────────────────────
                item {
                    CategoryTopBar(onClose = onClose)
                }

                // ── Difficulty selector ────────────────────────────────────
                item {
                    DifficultySection(
                        selectedDifficulty   = selectedDifficulty,
                        onDifficultySelected = { selectedDifficulty = it },
                        modifier             = Modifier.padding(
                            horizontal = Spacing.screenEdge,
                            vertical   = Spacing.xl
                        )
                    )
                }

                // ── "CATEGORIES" section label ─────────────────────────────
                item {
                    Text(
                        text     = "CATEGORIES",
                        style    = MaterialTheme.typography.labelSmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(
                            horizontal = Spacing.screenEdge,
                            vertical   = Spacing.sm
                        )
                    )
                }

                // ── Search bar — now live ──────────────────────────────────
                item {
                    AppSearchBar(
                        value         = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder   = "Search categories...",
                        modifier      = Modifier.padding(
                            horizontal = Spacing.screenEdge,
                            vertical   = Spacing.sm
                        )
                    )
                }

                item { Spacer(modifier = Modifier.height(Spacing.sm)) }

                // ── Category rows — filteredCategories ─────────────────────
                // Uses filteredCategories instead of categories — the list
                // updates automatically as searchQuery changes
                items(
                    items = filteredCategories,
                    key   = { it.id }
                ) { category ->
                    ListItemCard(
                        title    = category.name,
                        subtitle = "${category.questionCount} Questions",
                        iconRes  = category.iconRes,
                        iconTint = category.iconTint,
                        onClick  = { onCategoryClick(category.id) },
                        modifier = Modifier.padding(
                            horizontal = Spacing.screenEdge,
                            vertical   = Spacing.xs
                        )
                    )
                }
            }

            // ── Start Quiz — pinned below scroll ───────────────────────────
            StartQuizButton(
                onClick  = onStartQuiz,
                modifier = Modifier.padding(
                    horizontal = Spacing.screenEdge,
                    vertical   = Spacing.xl
                )
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// TOP BAR
// ─────────────────────────────────────────────────────────────────────────────

/**
 * X close button left + "Quiz Setup" title right.
 *
 * @param onClose   Called when the user taps the X.
 *                  TODO(Week 4): `NavController.popBackStack()`.
 * @param modifier  Applied to the outermost [Row].
 */
@Composable
private fun CategoryTopBar(
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier          = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.screenEdge)
            .padding(top = Spacing.xl, bottom = Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick  = onClose,
            modifier = Modifier.size(ComponentSize.close)
        ) {
            Icon(
                painter            = painterResource(R.drawable.ic_close),
                contentDescription = "Close",
                tint               = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier           = Modifier.size(IconSize.xxs)
            )
        }
        Spacer(modifier = Modifier.width(Spacing.sm))
        Text(
            text       = "Quiz Setup",
            style      = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color      = MaterialTheme.colorScheme.primary
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// DIFFICULTY SECTION
// ─────────────────────────────────────────────────────────────────────────────

/**
 * "DIFFICULTY" label and three [DifficultyChip]s.
 *
 * **State hoisting pattern:**
 * `selectedDifficulty` and `onDifficultySelected` are passed in — this composable
 * is stateless. It renders the correct chip as selected and reports taps upward.
 * [CategoryScreen] owns the state; [DifficultySection] just displays it.
 *
 * @param selectedDifficulty    Currently active chip label — "Easy", "Medium", or "Hard".
 * @param onDifficultySelected  Called with the new label when a chip is tapped.
 * @param modifier              Applied to the outermost [Column].
 */
@Composable
private fun DifficultySection(
    selectedDifficulty: String,
    onDifficultySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val ext = MaterialTheme.triviasparks

    val difficultyOptions = listOf(
        DifficultyOption("Easy",   R.drawable.ic_star_fill, ext.easy,   ext.easyOnLight),
        DifficultyOption("Medium", R.drawable.ic_light,     ext.medium, ext.mediumOnLight),
        DifficultyOption("Hard",   R.drawable.ic_flame,     ext.hard,   ext.hardOnLight)
    )

    Column(
        modifier            = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text  = "DIFFICULTY",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            difficultyOptions.forEach { option ->
                DifficultyChip(
                    option     = option,
                    isSelected = option.label == selectedDifficulty,
                    onClick    = { onDifficultySelected(option.label) },
                    modifier   = Modifier.weight(1f)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// START QUIZ BUTTON
// ─────────────────────────────────────────────────────────────────────────────

/**
 * Full-width CTA pinned to the bottom.
 *
 * @param onClick   Called when tapped.
 *                  TODO(Week 4): navigate to `QuizScreen` with selected category + difficulty.
 * @param modifier  Applied to the outermost [Button].
 */
@Composable
private fun StartQuizButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick  = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(ComponentSize.buttonHeightLarge),
        shape  = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Violet800,
            contentColor   = Color.White
        )
    ) {
        Icon(
            painter            = painterResource(R.drawable.ic_play),
            contentDescription = null,
            modifier           = Modifier.size(IconSize.sm)
        )
        Spacer(modifier = Modifier.width(Spacing.sm))
        Text(
            text       = "Start Quiz",
            style      = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// PREVIEWS
// ─────────────────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "CategoryScreen — light, Easy selected")
@Composable
private fun CategoryScreenLightPreview() {
    TriviaSparksTheme(darkTheme = false) { CategoryScreen() }
}

@Preview(showBackground = true, name = "CategoryScreen — dark, Easy selected",
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CategoryScreenDarkPreview() {
    TriviaSparksTheme(darkTheme = true) { CategoryScreen() }
}

@Preview(showBackground = true, name = "CategoryScreen — Medium selected")
@Composable
private fun CategoryScreenMediumPreview() {
    TriviaSparksTheme(darkTheme = false) { CategoryScreen() }
}