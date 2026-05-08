package pt.isel.dam.sv2526.triviasparks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import pt.isel.dam.sv2526.triviasparks.ui.navigation.AppNavGraph
import pt.isel.dam.sv2526.triviasparks.ui.theme.TriviaSparksTheme

/**
 * Single activity host for Trivia Sparks.
 *
 * This class has one job — create the [NavHostController] and pass it to
 * [AppNavGraph]. It has no knowledge of individual screens or routes.
 *
 * **Before (Week 2):**
 * ```kotlin
 * setContent {
 *     TriviaSparksTheme {
 *         HomeScreen()   // static, no navigation, no lambdas
 *     }
 * }
 * ```
 *
 * **After (Week 4):**
 * ```kotlin
 * setContent {
 *     TriviaSparksTheme {
 *         AppNavGraph(navController)   // all screens, all lambdas wired
 *     }
 * }
 * ```
 *
 * [rememberNavController] creates the controller and survives recomposition.
 * [AppNavGraph] declares every destination and wires all navigation lambdas.
 *
 * TODO(Week 8): add Firebase Auth state check here to redirect to
 * [Routes.LOGIN] instead of [Routes.HOME] when no user is signed in.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviaSparksTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}