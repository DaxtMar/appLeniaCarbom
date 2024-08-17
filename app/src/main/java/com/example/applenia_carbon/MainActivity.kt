package com.example.applenia_carbon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.applenia_carbon.auth.viewmodel.AuthViewModel
import com.example.applenia_carbon.auth.view.authScreen
import com.example.applenia_carbon.home.viewmodel.HomeViewModel
import com.example.applenia_carbon.home.viewmodel.PedidoViewModel
import com.example.applenia_carbon.screens.homeScreen
import com.example.applenia_carbon.routes.AppRoutes
import com.example.applenia_carbon.screens.RegistroScreen
import com.example.applenia_carbon.screens.WelcomeScreen
import com.example.applenia_carbon.ui.theme.Applenia_carbonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val pedidoViewModel: PedidoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Applenia_carbonTheme {
                val navigation = rememberNavController()
                //val authViewModel: AuthViewModel = viewModel() // Obtener AuthViewModel
                NavHost(navController = navigation,
                    startDestination = AppRoutes.welcomeScreen.path,
                    builder = {
                        composable(AppRoutes.welcomeScreen.path) {
                            WelcomeScreen(navigation)
                        }
                        composable(AppRoutes.loginScreen.path) {
                            authScreen(/*AuthViewModel()*/authViewModel, navigation)
                        }
                        composable(
                            AppRoutes.homeScreen.path,
                            arguments = listOf(navArgument("id")
                            { type = NavType.IntType })
                        ) { params ->
                            homeScreen(authViewModel,homeViewModel,pedidoViewModel)
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