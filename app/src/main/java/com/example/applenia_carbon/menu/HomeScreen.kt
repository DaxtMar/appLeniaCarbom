package com.example.applenia_carbon.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun homeScreen(id: Int) {
    val navController = rememberNavController()
    val navigateAction = remember(navController) {
        NavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: MyAppRoute.Tienda

    AppContent(
        navController = navController,
        selectedDestination = selectedDestination,
        navigateTopLevelDestination = navigateAction::navigateTo,
        dato = id
    )
}