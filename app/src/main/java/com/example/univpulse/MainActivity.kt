package com.example.univpulse

import Home
import Login
import Main
import Splash
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.univpulse.onboarding.OnBoarding1
import com.example.univpulse.ui.theme.UnivPulseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** must call langCode after setContent to get the last change in language */
        val langCode = LanguagesPreferences.getSavedLanguageCode(this)
        LanguagesPreferences.setLocale(this, langCode)


        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            UnivPulseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   NavHost(
                       navController = navController,
                       startDestination = Route.Main,
                       modifier = Modifier.padding(innerPadding)
                   ){
                       composable(Route.Splash) { Splash(navController) }
                       composable(Route.OnBoarding1) { OnBoarding1(navController) }
                       composable(Route.Login) { Login(navController) }
                       composable(Route.Main) { Main(rootNavController = navController) }

                   }
                }
            }
        }
    }
}

