import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main(rootNavController: NavHostController) {
    val innerNavController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(innerNavController) }
    ) { paddingValues ->
        NavHost(
            navController = innerNavController,
            startDestination = "home",
            modifier = Modifier
                .fillMaxSize()

                // to fix the problem of hidden element by navbar should add padding in bottom with calculate it with
                // calculateBottomPadding
                // when we use just paddingValues we got in top and bottom
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            composable("home") { Home() }
            composable("university") { MyUniversity() }
            composable("notifications") { Notifications() }
            composable("settings") {
                Settings(
                    navController = innerNavController,
                    rootNavController = rootNavController
                )
            }
        }
    }
}
