package com.example.simplefitnesstracker.ui.screens.exercisedetailscreen

import androidx.lifecycle.ViewModel
import com.example.simplefitnesstracker.data.repositories.FirebaseRepository
import com.example.simplefitnesstracker.models.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class ExerciseDetailScreenViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExerciseDetailScreenState())
    val state: StateFlow<ExerciseDetailScreenState> get() = _state.asStateFlow()

    fun onEdit() {
        TODO("Not yet implemented")
    }

    fun deleteWorkoutPlan(exercise: Exercise, onSuccess: () -> Unit) {
        firebaseRepository.deleteWorkoutPlan(exercise, onSuccess)
    }


    fun setId(id: String) {
        _state.update {
            it.copy(id = id)
        }
    }

    fun setName(name: String) {
        _state.update {
            it.copy(name = name)
        }
    }

    fun setDescription(description: String) {
        _state.update {
            it.copy(description = description)
        }
    }

    fun setDuration(duration: String) {
        _state.update {
            it.copy(duration = duration)
        }
    }

    fun setStatus(status: String) {
        _state.update {
            it.copy(status = status)
        }
    }

    fun setDate(date: String) {
        _state.update {
            it.copy(date = date)
        }
    }

    data class ExerciseDetailScreenState(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val duration: String = "",
        val status: String = "",
        val date: String = ""
    ) {
        fun toExercise(): Exercise {
            return Exercise(
                id = id,
                name = name,
                description = description,
                duration = duration,
                status = status,
                date = date
            )
        }
    }
}