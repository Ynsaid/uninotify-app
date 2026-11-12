package com.example.univpulse.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.univpulse.R
import com.example.univpulse.ui.theme.Primary
import com.example.univpulse.ui.theme.SubTitle
import com.example.univpulse.ui.theme.SubTitle2
import kotlinx.coroutines.delay

@Composable
fun OnBoarding1(navController: NavController) {
    val imgsList = listOf(
        R.drawable.students,
        R.drawable.two_student,
        R.drawable.calender
    )
    val titleList = listOf(
        "Important Alerts",
        "Your Academic Companion",
        "Stay Informed Instantly"
    )
    val descList = listOf(
        "Receive immediate updates about class cancellations, schedule changes, or urgent announcements.",
        "Stay connected with your academic journey anytime, anywhere with personalized tools.",
        "Get real-time notifications from your professors and university administration."
    )

    var currentIndex by remember { mutableStateOf(0) }


    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentIndex = (currentIndex + 1) % imgsList.size
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black.copy(0.5f))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {
                    fadeIn(animationSpec = tween(2000)) togetherWith
                            fadeOut(animationSpec = tween(2000))
                }
            ) { index ->
                Text(
                    text = titleList[index],
                    fontSize = 26.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {
                    fadeIn(animationSpec = tween(2000)) togetherWith
                            fadeOut(animationSpec = tween(2000))
                }
            ) { index ->
                Text(
                    text = descList[index],
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = SubTitle2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            DotStepper(totalDots = imgsList.size, selectedDot = currentIndex)
        }

        Button(
            onClick = { navController.navigate(Route.Login) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
                .size(60.dp)
                .border(width = 3.dp, color = Color(0xFFBFBFBF), shape = CircleShape),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = White
            )
        ) {
            Text(
                text = ">",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = White
            )
        }
    }
}


@Composable
fun DotStepper(
    totalDots: Int,
    selectedDot: Int
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .width(if (index == selectedDot) 16.dp else 8.dp)
                    .height(8.dp)
                    .background(
                        color = if (index == selectedDot) Primary else SubTitle,
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}
