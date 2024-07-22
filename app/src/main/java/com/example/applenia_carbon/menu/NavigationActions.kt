package com.example.applenia_carbon.menu

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Store
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.applenia_carbon.R

class NavigationActions(private val navController: NavHostController) {
    fun navigateTo(destination: MyAppTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}

data class MyAppTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val iconTextId: Int
)

val TOP_LEVEL_DESTINATIONS = listOf(
    MyAppTopLevelDestination(
        route = MyAppRoute.Tienda,
        selectedIcon = Icons.Default.Store,
        iconTextId = R.string.tienda
    ),
    MyAppTopLevelDestination(
        route = MyAppRoute.Categorias,
        selectedIcon = Icons.Default.Category,
        iconTextId = R.string.categorias
    ),
    MyAppTopLevelDestination(
        route = MyAppRoute.Cuenta,
        selectedIcon = Icons.Default.AccountBox,
        iconTextId = R.string.cuenta
    ),
)

object MyAppRoute {
    const val Tienda = "Tienda"
    const val Categorias = "Categorias"
    const val Cuenta = "Cuenta"
}