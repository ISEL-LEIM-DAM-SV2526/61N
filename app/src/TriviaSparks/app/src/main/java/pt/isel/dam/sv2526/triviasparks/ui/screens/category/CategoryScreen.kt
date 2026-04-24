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
import pt.isel.dam.sv2526.triviasparks.ui.component.AppSearchBar
import pt.isel.dam.sv2526.triviasparks.ui.component.DifficultyChip
import pt.isel.dam.sv2526.triviasparks.ui.component.ListItemCard
import pt.isel.dam.sv2526.triviasparks.ui.model.Category
import pt.isel.dam.sv2526.triviasparks.ui.model.DifficultyOption
import pt.isel.dam.sv2526.triviasparks.ui.preview.sampleCategories
import pt.isel.dam.sv2526.triviasparks.ui.theme.ButtonShape
import pt.isel.dam.sv2526.triviasparks.ui.theme.ComponentSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.IconSize
import pt.isel.dam.sv2526.triviasparks.ui.theme.Spacing
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme
import pt.isel.dam.sv2526.triviasparks.ui.theme.Violet800
import pt.isel.dam.sv2526.triviasparks.ui.theme.triviasparks

/**
 * Quiz Setup screen — the player selects a difficulty and a category before starting.
 *
 * Layout pattern: [Scaffold] → [Column] → [LazyColumn] (`weight(1f)`) + [StartQuizButton].
 * The button is pinned outside the scroll so it is always visible regardless of list length.
 *
 * ── Week 3: State ────────────────────────────────────────────────────────────
 * This screen owns its UI state using Compose primitives:
 * - `selectedDifficulty` — current difficulty ("Easy", "Medium", "Hard")
 * - `searchQuery` — text entered in the search bar
 *
 * State is stored with `remember { mutableStateOf(...) }` and drives recomposition.
 * When the user interacts with the UI:
 * - Selecting a difficulty updates `selectedDifficulty` via a callback
 * - Typing in the search bar updates `searchQuery`
 *
 * The visible category list is derived from state using [derivedStateOf]:
 * - `filteredCategories` recomputes only when `searchQuery` or `categories` change
 * - avoids unnecessary recalculations during unrelated recompositions
 *
 * This is a local (screen-level) state approach appropriate for Week 3.
 * State hoisting and navigation will be introduced in later weeks.
 *
 * ── What is static this week and when it becomes live ─────────────────────────
 *
 * | Element | Static value | Becomes live |
 * |---|---|---|
 * | `categories` | `sampleCategories` mock | Week 6 — Open Trivia Database API |
 * | `onClose` | empty lambda | Week 4 — `NavController.popBackStack()` |
 * | `onCategoryClick` | empty lambda | Week 4 — navigate to `QuizDetailScreen` |
 * | `onStartQuiz` | empty lambda | Week 4 — navigate to `QuizScreen` with args |
 *
 * Figma design:
 * https://www.figma.com/design/JLQCo8SrXd27RnUmIhQ4CS/Trivia-Sparks-Game?node-id=35-1773&t=Tqzagesq6ztbVvp0-1
 *
 * Wiki — Week 2 CategoryScreen section:
 * https://github.com/ISEL-LEIM-DAM-SV2526/61N/wiki/02-%E2%80%90-Jetpack-Compose-%E2%80%90-Compose-Fundamentals#categoryscreen
 *
 * @param categories          List of categories to display.
 * @param onClose             Called when the user taps the X button.
 *                            TODO(Week 4): `NavController.popBackStack()`.
 * @param onCategoryClick     Called when the user taps a category row. Receives the category ID.
 *                            TODO(Week 4): navigate to `QuizDetailScreen`.
 * @param onStartQuiz         Called when the user taps Start Quiz.
 *                            TODO(Week 4): navigate to `QuizScreen` with selected category + difficulty.
 */
@Composable
fun CategoryScreen(
    categories: List<Category>     = sampleCategories,  // TODO(Week 6): Open Trivia Database API
    onClose: () -> Unit            = {},                 // TODO(Week 4): NavController.popBackStack()
    onCategoryClick: (Int) -> Unit = {},                 // TODO(Week 4): navigate to QuizDetailScreen
    onStartQuiz: () -> Unit        = {}                  // TODO(Week 4): navigate to QuizScreen with args
) {

    var selectedDifficulty by remember { mutableStateOf("Easy") }
    var searchQuery        by remember { mutableStateOf("") }

    // derivedStateOf — only recomputes when searchQuery changes,
    // not on every recomposition triggered by other causes.
    val filteredCategories by remember(searchQuery, categories) {
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
            // Scrollable content — weight(1f) takes all space above the sticky button
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
                        selectedDifficulty = selectedDifficulty,
                        onDifficultySelected = { selectedDifficulty = it },
                        modifier           = Modifier.padding(
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

                // ── Search bar ─────────────────────────────────────────────
                item {
                    AppSearchBar(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = "Search Placeholder",
                        modifier = Modifier.padding(
                            horizontal = Spacing.screenEdge,
                            vertical   = Spacing.sm
                        )
                    )
                }

                item { Spacer(modifier = Modifier.height(Spacing.sm)) }

                // ── Category rows ──────────────────────────────────────────
                // TODO(Week 6): replace sampleCategories with API data from ViewModel
                items(
                    items = filteredCategories,
                    key   = { it.id }   // stable key — always required on items()
                ) { category ->
                    ListItemCard(
                        title    = category.name,
                        subtitle = "${category.questionCount} Questions", // question count as subtitle
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

            // ── Start Quiz — pinned below the scroll ───────────────────────
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
 * Custom top bar — X close button left, "Quiz Setup" title right.
 *
 * Uses a plain [Row] instead of [androidx.compose.material3.TopAppBar].
 * An X button is used instead of a back arrow because this screen has
 * modal semantics — the user is configuring a session, not navigating a linear flow.
 *
 * The [IconButton] uses [ComponentSize.close] (28.5dp) for the touch target.
 * The icon itself uses [IconSize.xxs] (13dp) to keep the X visually small
 * relative to the title — matching the Figma design.
 *
 * @param onClose   Called when the user taps the X button.
 *                  TODO(Week 4): `NavController.popBackStack()`.
 * @param modifier  Applied to the outermost [Row] element.
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
            modifier = Modifier.size(ComponentSize.close)  // 28.5dp touch target
        ) {
            Icon(
                painter            = painterResource(R.drawable.ic_close),
                contentDescription = "Close",
                tint               = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier           = Modifier.size(IconSize.xxs)  // 13dp — small X
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
 * "DIFFICULTY" label and three [DifficultyChip]s in a full-width [Row].
 *
 * Only one chip is active at a time, determined by [selectedDifficulty].
 * Each chip gets [Modifier.weight(1f)] so all three share equal width.
 *
 * The [DifficultyOption] list is built from extended Dreamscape tokens —
 * each option carries both the base colour (icon + border) and the `onLightColour`
 * variant (label text — WCAG AA on white surfaces).
 *
 * In Week 3, the selected difficulty is controlled by the parent composable.
 * Tapping a chip invokes [onDifficultySelected], allowing the parent to update
 * its state and trigger recomposition.
 *
 * @param selectedDifficulty   Label of the currently active chip — "Easy", "Medium", or "Hard".
 * @param onDifficultySelected Callback invoked when a chip is tapped, providing the selected label.
 * @param modifier             Applied to the outermost [Column] element.
 */
@Composable
private fun DifficultySection(
    selectedDifficulty: String,
    onDifficultySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val ext = MaterialTheme.triviasparks

    val difficultyOptions = listOf(
        DifficultyOption(
            label         = "Easy",
            iconRes       = R.drawable.ic_star_fill,
            colour        = ext.easy,
            onLightColour = ext.easyOnLight
        ),
        DifficultyOption(
            label         = "Medium",
            iconRes       = R.drawable.ic_light,
            colour        = ext.medium,
            onLightColour = ext.mediumOnLight   // #B8922A — never white on yellow
        ),
        DifficultyOption(
            label         = "Hard",
            iconRes       = R.drawable.ic_flame,
            colour        = ext.hard,
            onLightColour = ext.hardOnLight
        )
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
 * Full-width CTA pinned to the bottom of the screen.
 *
 * Uses [Violet800] (`#3C3489`) instead of `colorScheme.primary` (`#8B7FE8`).
 * The deeper violet creates stronger contrast against the `background` token —
 * signalling this is the final committed action on the screen.
 * @param onClick   Called when the user taps the button.
 *                  TODO(Week 4): navigate to `QuizScreen` with selected category ID and difficulty.
 * @param modifier  Applied to the outermost [Button] element.
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
            .height(ComponentSize.buttonHeightLarge),  // 56dp
        shape  = ButtonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = Violet800,      // deeper than primary — strong final CTA
            contentColor   = Color.White
        )
    ) {
        Icon(
            painter            = painterResource(R.drawable.ic_play),
            contentDescription = null,
            modifier           = Modifier.size(IconSize.sm)   // 20dp
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
    TriviaSparksTheme(darkTheme = false) {
        CategoryScreen()
    }
}

@Preview(
    showBackground = true,
    name           = "CategoryScreen — dark, Easy selected",
    uiMode         = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CategoryScreenDarkPreview() {
    TriviaSparksTheme(darkTheme = true) {
        CategoryScreen()
    }
}

@Preview(showBackground = true, name = "CategoryScreen — Medium selected")
@Composable
private fun CategoryScreenMediumSelectedPreview() {
    TriviaSparksTheme(darkTheme = false) {
        CategoryScreen()
    }
}