import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.univpulse.R
import com.example.univpulse.ui.theme.Primary
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavController) {
    LaunchedEffect(Unit) {
        while(true){
            delay(2000)
            navController.navigate(Route.OnBoarding1)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Primary),
        contentAlignment = Alignment.Center
    ){
       Text(
          text = "uniPulse",
           fontSize = 60.sp,
           fontFamily = FontFamily(Font(R.font.poppins_bold)),
           color = Color.White
       )
    }
}