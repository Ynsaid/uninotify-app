import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.univpulse.R
import com.example.univpulse.ui.theme.Primary
import com.example.univpulse.ui.theme.SubTitle2

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("home", R.drawable.university_icon),
        BottomNavItem("university", R.drawable.teacher_icon),
        BottomNavItem("notifications", R.drawable.notifications_icon),
        BottomNavItem("settings", R.drawable.settings_icon)
    )

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(56.dp)
    ) {
        val currentRoute by navController.currentBackStackEntryAsState()

        items.forEach { item ->
            val selected = currentRoute?.destination?.route == item.route

            NavigationBarItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        if (selected) {
                            Box(
                                modifier = Modifier
                                    .height(4.dp)
                                    .width(40.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            bottomEnd = 4.dp,
                                            bottomStart = 4.dp
                                        )
                                    )
                                    .background(color = Primary)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        } else {
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.route,
                            tint = if (selected) Primary else SubTitle2,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                alwaysShowLabel = false
            )
        }
    }
}

data class BottomNavItem(val route: String, val icon: Int)
