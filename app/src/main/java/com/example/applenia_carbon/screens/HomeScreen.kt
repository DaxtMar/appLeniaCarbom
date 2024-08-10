package com.example.applenia_carbon.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.applenia_carbon.core.utils.MenuItem
import com.example.applenia_carbon.routes.AppRoutes
import com.example.applenia_carbon.screens.viewmodel.CartViewModel

@Composable
fun homeScreen(id: Int) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val cartViewModel: CartViewModel = viewModel()
    Scaffold(

        topBar = { MyToolBar() },
        bottomBar = {
            EjemploBottomBar(
                currentDestination = currentDestination,
                navController = navController
            )
        },
        content = { initialpadding ->
            Box(
                modifier = Modifier
                    .padding(initialpadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                NavHost(
                    navController = navController,
                    startDestination = AppRoutes.tiendaScreen.path
                ) {
                    composable(route = AppRoutes.tiendaScreen.path) {
                        tiendaScreen(navController)
                    }
                    composable(route = AppRoutes.categoriaScreen.path) {
                        categoriaScreen()
                    }
                    composable(route = AppRoutes.cuentaScreen.path) {
                        cuentaScreen()
                    }
                    composable(route = AppRoutes.carritoScreen.path) {
                        carritoScreen(cartViewModel = cartViewModel, navController)
                    }

                    composable(
                        route = AppRoutes.detalleScreen.path,
                        arguments = listOf(navArgument("idp")
                        { type = NavType.IntType })
                    ) { params ->
                        DetalleScreen(
                            id = params.arguments?.getInt("idp") ?: 0,
                            navController = navController, cartViewModel = cartViewModel
                        )
                    }

                    composable(route = AppRoutes.pasarelaScreen.path) {
                        pasarelaScreen(cartViewModel = cartViewModel)
                    }
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolBar() {
    TopAppBar(title = { Text(text = "Leña y Carbón") }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Red,
        titleContentColor = Color.White

    ),
        navigationIcon = {

        },
        actions = {

        }
    )
}

@Composable
fun EjemploBottomBar(
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val screens = opcionesMenu()

    BottomAppBar(
        containerColor = Color.Red,
        contentColor = Color.White,
        tonalElevation = 5.dp
    ) {
        screens.forEachIndexed { index, opcion ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route == opcion.path
                } == true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    indicatorColor = Color.White,
                    unselectedIconColor = Color.White
                ),
                onClick = {
                    navController.navigate(opcion.path) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = opcion.icon, contentDescription = "")
                },
                label = { Text(text = opcion.titulo, color = Color.White) })
        }
    }
}

fun opcionesMenu(): List<MenuItem> {
    return listOf(
        MenuItem(Icons.Default.Store, "Tienda", "tiendaScreen"),
        MenuItem(Icons.Default.Category, "Categoria", "categoriaScreen"),
        MenuItem(Icons.Default.AccountBox, "Cuenta", "cuentaScreen"),
        MenuItem(Icons.Default.ShoppingCart, "Carrito", "carritoScreen")
    )
}