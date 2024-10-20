package com.example.simplefitnesstracker.ui.screens.homescreen

import androidx.lifecycle.ViewModel
import com.example.simplefitnesstracker.data.repositories.FirebaseRepository
import com.example.simplefitnesstracker.models.Exercise
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val TAG = "HomeViewModel"
    private val _workoutPlans = MutableStateFlow<List<Exercise>>(emptyList())
    val workoutPlans get() = _workoutPlans.asStateFlow()


    fun getWorkoutPlans() {
        firebaseRepository.getWorkoutPlans {
            _workoutPlans.value = it
        }
    }
}

