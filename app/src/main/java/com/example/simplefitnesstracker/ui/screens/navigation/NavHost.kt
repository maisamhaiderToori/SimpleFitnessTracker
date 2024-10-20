package com.example.simplefitnesstracker.ui.screens.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.simplefitnesstracker.ui.screens.authenticationscreen.SignUpScreen
import com.example.simplefitnesstracker.ui.screens.homescreen.HomeScreen
import com.example.simplefitnesstracker.ui.screens.navigation.Graph.NAV_GRAPH
import com.example.simplefitnesstracker.ui.screens.navigation.Screens.ExerciseDetail
import com.example.simplefitnesstracker.ui.screens.navigation.Screens.Home
import com.example.simplefitnesstracker.ui.screens.navigation.Screens.SignUp

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navHostVM: MainNavHostViewModel = hiltViewModel()
    val currentUser = navHostVM.auth.currentUser
    val context = LocalContext.current
    val uiState = navHostVM.state.collectAsState()
    val startDestination = if (currentUser != null) Home.route else SignUp.route

//    val categoriesVM: CategoriesViewModel = hiltViewModel()
    Column(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            route = NAV_GRAPH,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {

            composable(route = SignUp.route) {
                SignUpScreen()
            }

            composable(route = Home.route) {
                HomeScreen()
            }


            composable(route = ExerciseDetail.route) {
            }


        }
    }
    when (uiState.value) {
        SignUp -> {
            navController.navigate(SignUp.route)
        }

        Home -> {
            navController.navigate(Home.route)
        }

        ExerciseDetail -> {
            navController.navigate(ExerciseDetail.route)
        }

    }


}

sealed class Screens(val route: String) {
    object SignUp : Screens("SignUp")
    object Home : Screens("home")
    object ExerciseDetail : Screens("ExerciseDetail")
}

object Graph {
    const val NAV_GRAPH = "NAV_GRAPH"
}