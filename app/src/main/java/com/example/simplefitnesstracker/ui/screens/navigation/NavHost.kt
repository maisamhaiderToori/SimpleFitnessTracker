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
import com.example.simplefitnesstracker.ui.screens.authenticationscreen.SignUpInScreen
import com.example.simplefitnesstracker.ui.screens.exercisedetailscreen.ExerciseDetailScreen
import com.example.simplefitnesstracker.ui.screens.exercisedetailscreen.ExerciseDetailScreenViewModel
import com.example.simplefitnesstracker.ui.screens.homescreen.HomeScreen
import com.example.simplefitnesstracker.ui.screens.navigation.Graph.NAV_GRAPH
import com.example.simplefitnesstracker.ui.screens.navigation.Screens.ExerciseDetail
import com.example.simplefitnesstracker.ui.screens.navigation.Screens.Home
import com.example.simplefitnesstracker.ui.screens.navigation.Screens.SignUpIn

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navHostVM: MainNavHostViewModel = hiltViewModel()
    val exerciseDetailScreenViewModel: ExerciseDetailScreenViewModel = hiltViewModel()

    val currentUser = navHostVM.auth.currentUser
    val context = LocalContext.current
    val uiState = navHostVM.state.collectAsState()

//    val categoriesVM: CategoriesViewModel = hiltViewModel()
    Column(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = SignUpIn.route,
            route = NAV_GRAPH,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {

            composable(route = SignUpIn.route) {
                if (currentUser != null) {
                    navController.popBackStack()
                    navController.navigate(Home.route)
                } else {
                    SignUpInScreen {
                        navController.popBackStack()
                        navController.navigate(Home.route)
                    }
                }

            }

            composable(route = Home.route) {
                HomeScreen {
                    exerciseDetailScreenViewModel.apply {
                        setId(it.id)
                        setName(it.name)
                        setDescription(it.description)
                        setStatus(it.status)
                        setDuration(it.duration)
                        setDate(it.date)
                    }
                    navController.navigate(ExerciseDetail.route)
                }
            }


            composable(route = ExerciseDetail.route) {
                ExerciseDetailScreen(exerciseDetailScreenViewModel,
                    deleteWorkoutPlan = {
                        exerciseDetailScreenViewModel.deleteWorkoutPlan(
                            it
                        ) {
                            navController.popBackStack()
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }


        }
    }

}

sealed class Screens(val route: String) {
    object SignUpIn : Screens("SignUp")
    object Home : Screens("Home")
    object ExerciseDetail : Screens("ExerciseDetail")
}

object Graph {
    const val NAV_GRAPH = "NAV_GRAPH"
}