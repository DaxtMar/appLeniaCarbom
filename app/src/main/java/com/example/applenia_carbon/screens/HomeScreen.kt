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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.applenia_carbon.Models.Usuario
import com.example.applenia_carbon.core.utils.MenuItem
import com.example.applenia_carbon.home.viewmodel.HomeViewModel
import com.example.applenia_carbon.routes.AppRoutes
import com.example.applenia_carbon.routes.opcionesApp
import com.example.applenia_carbon.screens.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@Composable
fun homeScreen(id: Int,homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val cartViewModel: CartViewModel = viewModel()

    // Estado para almacenar el usuario
    val user = remember { mutableStateOf<Usuario?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Cargar los datos del usuario
    LaunchedEffect(id) {
        coroutineScope.launch {
            // Aquí deberías implementar la lógica para recuperar el usuario basado en el id
            // Simulación de la recuperación del usuario
            user.value = Usuario(id, "Nombre", "email@example.com", "password", "Dirección", "Teléfono")
        }
    }

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
                    composable(route = AppRoutes.tiendaScreen.path) { backStackEntry ->
                        val categoriaIndex =
                            backStackEntry.arguments?.getString("categoriaIndex")?.toIntOrNull()
                        tiendaScreen(navController, categoriaIndex,homeViewModel)
                    }
                    composable(route = AppRoutes.categoriaScreen.path) {
                        categoriaScreen(navController,homeViewModel)
                    }
                    composable(route = AppRoutes.cuentaScreen.path) {
                        cuentaScreen(user = user.value)
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
                            navController = navController, cartViewModel = cartViewModel,
                            homeViewModel=homeViewModel
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
    var opcionSeleccionada by remember {
        mutableStateOf(0)
    }
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
                //selected = opcionSeleccionada == index,
                //selected = opcionesApp().get(index).nombre == opcion.titulo,
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
                    //opcionSeleccionada = index
                   /* when (opcion.titulo) {
                        "Tienda" -> navController.navigate(AppRoutes.tiendaScreen.path)
                        "Categoria" -> navController.navigate(AppRoutes.categoriaScreen.path)
                        "Cuenta" -> navController.navigate(AppRoutes.cuentaScreen.path)
                        "Carrito" -> navController.navigate(AppRoutes.carritoScreen.path)
                    }*/
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