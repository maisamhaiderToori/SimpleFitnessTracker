package com.example.simplefitnesstracker.ui.screens.authenticationscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val auth: FirebaseAuth) : ViewModel() {

    private val _states = MutableStateFlow(AuthenticationStates())

    val state: StateFlow<AuthenticationStates> get() = _states.asStateFlow()

    private val _signUpState = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val signUpState: StateFlow<SignUpState> = _signUpState


    fun setUseExistedButLoggedOutState(userExited: Boolean) {
        _states.update {
            it.copy(userExitedButLoggedOut = userExited)
        }
    }


    fun createUserWithEmailAndPassword(email: String, password: String) {
        _signUpState.value = SignUpState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        _signUpState.value = SignUpState.Success(user)
                    } else {
                        _signUpState.value =
                            SignUpState.Failure(task.exception?.message ?: "Unknown Error")
                    }
                }
            }
    }

    fun changeSignUpState(state: SignUpState) {
        _signUpState.value = state
    }


    data class AuthenticationStates(val userExitedButLoggedOut: Boolean = false)
}

sealed class SignUpState {
    object Idle : SignUpState()
    object Loading : SignUpState()
    data class Success(val user: FirebaseUser?) : SignUpState()
    data class Failure(val error: String) : SignUpState()
}