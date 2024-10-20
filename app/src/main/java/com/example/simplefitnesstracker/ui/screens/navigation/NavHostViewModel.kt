package com.example.simplefitnesstracker.ui.screens.navigation

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainNavHostViewModel @Inject constructor(
    val auth : FirebaseAuth
) : ViewModel() {

    /*States*/
    private val _state: MutableStateFlow<MainNavHostState> =
        MutableStateFlow(MainNavHostState())
    val state: StateFlow<MainNavHostState> get() = _state.asStateFlow()

    /*Events*/
    fun navigateTo(screen: Screens) {
        _state.update {
            it.copy(
                currentScreens = screen
            )
        }
    }

}

data class MainNavHostState(
    val currentScreens: Screens = Screens.SignUp,
)