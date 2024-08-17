package com.example.applenia_carbon.auth.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.applenia_carbon.R
import com.example.applenia_carbon.auth.viewmodel.AuthViewModel
import com.example.applenia_carbon.routes.AppRoutes
import kotlinx.coroutines.launch

@Composable
fun authScreen(authViewModel: AuthViewModel, navController: NavController) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingInit ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInit)
        ) {
            cabeceraLogin(Modifier.align(Alignment.TopEnd))
            formularioLogin(
                Modifier.align(Alignment.Center),
                authViewModel, snackbarHostState, navController
            )
            pieLogin(Modifier.align(Alignment.BottomCenter), navController)
        }
        /*Box(
            modifier = Modifier
                .padding(paddingInit)
                .fillMaxSize()
        ) {
            header(Modifier.align(Alignment.Center))
            body(
                modifier = Modifier.align(Alignment.Center),
                authViewModel, snackbarHostState, navController
            )
        }*/
    }
}

@Composable
fun formularioLogin(
    modifier: Modifier, authViewModel: AuthViewModel,
    state: SnackbarHostState, navController: NavController
) {
    val nombre: String by authViewModel.nombre.observeAsState(initial = "")
    val password: String by authViewModel.password.observeAsState(initial = "")
    val botonHabilitado: Boolean by authViewModel.botonLoginHabilitado.observeAsState(initial = false)
    Column(modifier = modifier.padding(start = 5.dp, end = 5.dp)) {
        imagenLogo(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.size(4.dp))
        txtNombre(nombre = nombre) { authViewModel.onLoginValueChanged(it, password) }
        Spacer(modifier = Modifier.size(4.dp))
        txtPassword(password = password) { authViewModel.onLoginValueChanged(nombre, it) }
        Spacer(modifier = Modifier.size(4.dp))
        loginButton(botonHabilitado, authViewModel, state, navController)
    }

}

/*
@Composable
fun header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(imageVector = Icons.Default.Close,
        contentDescription = "",
        modifier = modifier.clickable { activity.finish() })
}*/
/*
@Composable
fun body(
    modifier: Modifier, authViewModel: AuthViewModel,
    state: SnackbarHostState,
    navController: NavController
) {
    val nombre: String by authViewModel.nombre.observeAsState(initial = "")
    val password: String by authViewModel.password.observeAsState(initial = "")

    Column(
        modifier = modifier.padding(start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        imagenLogo(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.size(10.dp))
        txtNombre(nombre) { authViewModel.onLoginValueChanged(it, password) }
        Spacer(Modifier.size(15.dp))
        txtPassword(password) { authViewModel.onLoginValueChanged(nombre, it) }
        Spacer(Modifier.size(15.dp))
        LoginButton(authViewModel, state, navController)
        Spacer(Modifier.size(15.dp))
        Text(
            text = "¿No tienes cuenta? Regístrate",
            modifier = Modifier
                .clickable { navController.navigate(AppRoutes.registroScreen.path) }
                .padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}*/


@Composable
fun imagenLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.lenaycarbon),
        contentDescription = "logo",
        modifier = modifier
    )
}

@Composable
fun txtNombre(nombre: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = nombre,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nombre") },
        maxLines = 1,
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "persona") },
        singleLine = true
    )
}

@Composable
fun txtPassword(password: String, onTextChanged: (String) -> Unit) {
    var visible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Password") },
        maxLines = 1,
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icons.Default.Key, contentDescription = "clave") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val icon = if (visible) {
                Icons.Filled.VisibilityOff
            } else Icons.Filled.Visibility
            IconButton(onClick = { visible = !visible }) {
                Icon(imageVector = icon, contentDescription = "ver password")
            }
        },
        visualTransformation = if (visible) {
            VisualTransformation.None
        } else PasswordVisualTransformation()
    )
}

@Composable
fun cabeceraLogin(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(imageVector = Icons.Default.Close,
        contentDescription = "Cerrar",
        modifier = modifier.clickable { activity.finish() })
}

@Composable
fun pieLogin(modifier: Modifier, navController: NavController) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(
            modifier = Modifier
                .background(Color(0xFF21338D))
                .height(1.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(20.dp))
        registroLogin(navController)
        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
fun registroLogin(navController: NavController) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "¿No tienes cuenta?  ", fontSize = 12.sp, color = Color(0xFF21338D))
        Text(
            text = "  Registrate Aquí", fontSize = 12.sp, color = Color(0xFF21338D),
            modifier = Modifier.clickable { navController.navigate(AppRoutes.registroScreen.path) },
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun loginButton(
    botonHabilitado: Boolean,
    authViewModel: AuthViewModel,
    state: SnackbarHostState,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val loginResponse by authViewModel.loginResponse.observeAsState()
    Button(
        enabled = botonHabilitado,
        onClick = {
            authViewModel.login()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Ingresar")
    }

    loginResponse?.getContentNotChange()?.let { response ->
        if (response.success) {
            navController.navigate(AppRoutes.homeScreen.paramHome(response.id)) {
                popUpTo(AppRoutes.loginScreen.path) { inclusive = true }
            }
        } else {
            scope.launch {
                state.showSnackbar(
                    "Login fallido: ${response.message}",
                    actionLabel = "OK", duration = SnackbarDuration.Short
                )
            }
        }
    }
    /*
    loginResponse.let {item ->
        if (item?.success==true){
            navController.navigate(AppRoutes.homeScreen.paramHome(idp)) {
                popUpTo(AppRoutes.loginScreen.path) { inclusive = true }
            }
        }

    }*/

    //loginResponse?.getContentNotChange()?.let

    // Navegar a homeScreen si loginSuccess es true
    /*
    LaunchedEffect(loginResponse?.success) {
        //if (loginSuccess) {
        //navController.navigate(AppRoutes.homeScreen.path)
        navController.navigate(AppRoutes.homeScreen.paramHome(idp)) {
            popUpTo(AppRoutes.loginScreen.path) { inclusive = true }
        }
        //}
    }*/

    // Mostrar el mensaje de error si existe
//    LaunchedEffect(errorMessage) {
//        if (errorMessage.isNotEmpty()) {
//            scope.launch {
//                state.showSnackbar(
//                    errorMessage,
//                    actionLabel = "OK",
//                    duration = SnackbarDuration.Short
//                )
//            }
//        }
//    }
}


/*
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.applenia_carbon.R
import com.example.applenia_carbon.routes.AppRoutes
import kotlinx.coroutines.launch

@Composable
fun authScreen(authViewModel: AuthViewModel, navController: NavController) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingInit ->
        Box(
            modifier = Modifier
                .padding(paddingInit)
                .fillMaxSize()
        ) {
            header(Modifier.align(Alignment.Center))
            body(
                modifier = Modifier.align(Alignment.Center),
                authViewModel, snackbarHostState, navController
            )
        }
    }
}

@Composable
fun header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(imageVector = Icons.Default.Close,
        contentDescription = "",
        modifier = modifier.clickable { activity.finish() })
}

@Composable
fun body(
    modifier: Modifier, authViewModel: AuthViewModel,
    state: SnackbarHostState,
    navController: NavController
) {
    val nombre: String by authViewModel.nombre.observeAsState(initial = "")
    val password: String by authViewModel.password.observeAsState(initial = "")

    Column(
        modifier = modifier.padding(start = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
    ) {
        imagenLogo(modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.size(10.dp))
        txtNombre(nombre) { authViewModel.onLoginValueChanged(it, password) }
        Spacer(Modifier.size(15.dp))
        txtPassword(password) { authViewModel.onLoginValueChanged(nombre, it) }
        Spacer(Modifier.size(15.dp))
        LoginButton(authViewModel, state, navController)
        Spacer(Modifier.size(15.dp))
        Text(
            text = "¿No tienes cuenta? Regístrate",
            modifier = Modifier
                .clickable { navController.navigate(AppRoutes.registroScreen.path) }
                .padding(16.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun txtNombre(nombre: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        value = nombre, onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Nombre") },
        maxLines = 1,
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "persona") },
        singleLine = true
    )
}

@Composable
fun imagenLogo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.lenaycarbon),
        contentDescription = "logo",
        modifier = modifier
    )
}

@Composable
fun txtPassword(password: String, onTextChanged: (String) -> Unit) {
    var visible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = password, onValueChange = { onTextChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Password") },
        maxLines = 1,
        singleLine = true,
        leadingIcon = { Icon(imageVector = Icons.Default.Key, contentDescription = "clave") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            val icon = if (visible) {
                Icons.Filled.VisibilityOff
            } else Icons.Filled.Visibility
            IconButton(onClick = { visible = !visible }) {
                Icon(imageVector = icon, contentDescription = "ver password")
            }
        },
        visualTransformation = if (visible) {
            VisualTransformation.None
        } else PasswordVisualTransformation()
    )
}

@Composable
fun LoginButton(
    authViewModel: AuthViewModel,
    state: SnackbarHostState,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val loginSuccess by authViewModel.loginSuccess.observeAsState(false)
    val errorMessage by authViewModel.errorMessage.observeAsState("")
    val id by authViewModel.id.observeAsState(0) // Cambiado de userId a id

    Button(
        onClick = {
            authViewModel.login()
        },
        Modifier.fillMaxWidth()
    ) {
        Text(text = "Ingresar")
    }

    // Navegar a homeScreen si loginSuccess es true
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate(AppRoutes.homeScreen.paramHome(id)) {
                popUpTo(AppRoutes.loginScreen.path) { inclusive = true }
            }
        }
    }

    // Mostrar el mensaje de error si existe
    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            scope.launch {
                state.showSnackbar(
                    errorMessage,
                    actionLabel = "OK",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
}*/
