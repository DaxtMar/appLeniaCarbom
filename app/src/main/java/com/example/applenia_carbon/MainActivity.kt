package com.example.applenia_carbon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.applenia_carbon.auth.AuthViewModel
import com.example.applenia_carbon.auth.authScreen
import com.example.applenia_carbon.menu.homeScreen
import com.example.applenia_carbon.routes.AppRoutes
import com.example.applenia_carbon.screens.RegistroScreen
import com.example.applenia_carbon.screens.WelcomeScreen
import com.example.applenia_carbon.ui.theme.Applenia_carbonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Applenia_carbonTheme {
                val navigation = rememberNavController()
                NavHost(navController = navigation,
                    startDestination = AppRoutes.loginScreen.path,
                    builder = {
                        composable(AppRoutes.welcomeScreen.path) {
                            WelcomeScreen(navigation)
                        }
                        composable(AppRoutes.loginScreen.path) {
                            authScreen(AuthViewModel(), navigation)
                        }
                        composable(
                            AppRoutes.homeScreen.path,
                            arguments = listOf(navArgument("id")
                            { type = NavType.IntType })
                        ) { params ->
                            homeScreen(params.arguments?.getInt("id") ?: 0)
                        }
                        composable(AppRoutes.registroScreen.path) {
                            RegistroScreen(navigation)
                        }
                    }
                )

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Applenia_carbonTheme {

    }
}