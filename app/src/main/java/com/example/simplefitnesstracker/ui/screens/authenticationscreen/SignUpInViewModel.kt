package com.example.simplefitnesstracker.ui.screens.authenticationscreen

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplefitnesstracker.R
import com.example.simplefitnesstracker.data.repositories.FirebaseRepository
import com.example.simplefitnesstracker.models.Exercise
import com.example.simplefitnesstracker.ui.screens.authenticationscreen.SignUpInState.Idle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpInViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val auth: FirebaseAuth,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _states = MutableStateFlow(AuthenticationStates())

    val state: StateFlow<AuthenticationStates> get() = _states.asStateFlow()


    fun signupUserWithEmailAndPassword(email: String, password: String) {
        changeSignUpState(SignUpInState.Loading)

        if (!emailAndPasswordValidation(email, password)) {
            changeSignUpState(SignUpInState.Failure(context.getString(R.string.email_and_password_cannot_be_empty)))
            return
        }

        if (!isEmailValid(email)) {
            changeSignUpState(SignUpInState.Failure(context.getString(R.string.please_enter_a_valid_email)))
            return
        }
        if (!isPasswordValid(password)) {
            changeSignUpState(SignUpInState.Failure(context.getString(R.string.password_must_be_at_least_8_characters)))
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        changeSignUpState(
                            SignUpInState.Success(
                                user,
                                context.getString(R.string.signed_up_successfully)
                            )
                        )
                    } else {
                        changeSignUpState(
                            SignUpInState.Failure(
                                task.exception?.message ?: "Unknown Error"
                            )
                        )
                    }
                }
            }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        changeSignUpState(SignUpInState.Loading)

        if (!emailAndPasswordValidation(email, password)) {
            changeSignUpState(SignUpInState.Failure(context.getString(R.string.please_enter_a_valid_credentials)))
            return
        }

        if (!isEmailValid(email)) {
            changeSignUpState(SignUpInState.Failure(context.getString(R.string.please_enter_a_valid_email)))
            return
        }
        if (!isPasswordValid(password)) {
            changeSignUpState(SignUpInState.Failure(context.getString(R.string.please_enter_a_valid_credentials)))
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        changeSignUpState(
                            SignUpInState.Success(
                                auth.currentUser,
                                context.getString(R.string.signed_in_successfully), false
                            )
                        )
                    } else changeSignUpState(SignUpInState.Failure(context.getString(R.string.please_enter_a_valid_credentials)))

                }
            }
    }

    fun emailAndPasswordValidation(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    fun uploadWorkoutPlan(exercises: List<Exercise>) {
        viewModelScope.launch {
            exercises.forEach {
                firebaseRepository.uploadWorkoutPlan(it)
            }
        }
    }


    fun changeSignUpState(state: SignUpInState) {
        _states.update {
            it.copy(signUpInState = state)
        }
    }


    data class AuthenticationStates(
        val signUpInState: SignUpInState = Idle
    )
}

sealed class SignUpInState {
    object Idle : SignUpInState()
    object Loading : SignUpInState()
    data class Success(val user: FirebaseUser?, val message: String, val isSignUp: Boolean = true) :
        SignUpInState()

    data class Failure(val error: String) : SignUpInState()
}