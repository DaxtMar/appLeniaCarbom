package com.example.applenia_carbon.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.applenia_carbon.screens.CategoriaScreen
import com.example.applenia_carbon.screens.CuentaScreen
import com.example.applenia_carbon.screens.TiendaScreen
import kotlinx.coroutines.launch

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDestination: String,
    navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit,
    dato: Int
) {

    Scaffold(
        topBar = { MyToolBar() },
        bottomBar = {
            BottomNavigation(
                selectedDestination = selectedDestination,
                navigateTopLevelDestination = navigateTopLevelDestination
            )
        },
        content = { initialpadding ->
            Box(
                modifier = Modifier
                    .padding(initialpadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = Modifier.weight(1f),
                            navController = navController,
                            startDestination = MyAppRoute.Tienda
                        ) {
                            composable(MyAppRoute.Tienda) {
                                TiendaScreen()
                            }
                            composable(MyAppRoute.Categorias) {
                                CategoriaScreen()
                            }
                            composable(MyAppRoute.Cuenta) {
                                CuentaScreen()
                            }
                        }

                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolBar() {
    TopAppBar(title = { Text(text = "Leña y Carbón") }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Gray,
        titleContentColor = Color.White
    ),
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        },
        actions = {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    imageVector = Icons.Filled.Add, contentDescription = "",
//                    tint = Color.White
//                )
//            }
        }
    )
}

@Composable
fun BottomNavigation(
    selectedDestination: String,
    navigateTopLevelDestination: (MyAppTopLevelDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth(), containerColor = Color.Gray) {
        TOP_LEVEL_DESTINATIONS.forEach { destinations ->
            NavigationBarItem(
                selected = selectedDestination == destinations.route,
                onClick = { navigateTopLevelDestination(destinations) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    indicatorColor = Color.White,
                    unselectedIconColor = Color.White
                ),
                icon = {
                    Icon(
                        imageVector = destinations.selectedIcon,
                        contentDescription = stringResource(id = destinations.iconTextId)
                    )
                } ,
                label = { Text(text = destinations.route, color = Color.White) }
            )
        }
    }
}