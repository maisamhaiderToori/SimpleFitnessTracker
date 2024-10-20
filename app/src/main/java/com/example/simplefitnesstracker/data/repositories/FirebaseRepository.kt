package com.example.simplefitnesstracker.data.repositories

import android.util.Log
import com.example.simplefitnesstracker.models.Exercise
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

interface FirebaseRepository {
    fun signUp(email: String, password: String)
    fun uploadWorkoutPlan(exercise: Exercise)
    fun getWorkoutPlans(onSuccess: (List<Exercise>) -> Unit)
    fun deleteWorkoutPlan(exercise: Exercise, onSuccess: () -> Unit)
}

class FirebaseRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore, private val auth: FirebaseAuth
) : FirebaseRepository {
    override fun signUp(email: String, password: String) {
        TODO("Not yet implemented")
    }


    override fun uploadWorkoutPlan(exercise: Exercise) {

        firestore.collection("users").document(auth.currentUser!!.uid).collection("workoutPlans")
            .add(exercise).addOnSuccessListener { document ->

                Log.d("Firestore", "Workout plan added with ID: ${document.id}")
            }.addOnFailureListener { e ->
                Log.w("Firestore", "Error adding workout plan", e)
            }
    }

    override fun getWorkoutPlans(onSuccess: (List<Exercise>) -> Unit) {
        val listOf = mutableListOf<Exercise>()
        firestore.collection("users").document(auth.currentUser!!.uid).collection("workoutPlans")
            .get().addOnSuccessListener { document ->
                for (document in document) {
                    Log.d("Firestore", "${document.data}")
                    val map = document.data
                    listOf.add(
                        Exercise(
                            id = document.id,
                            name = map["name"].toString(),
                            description = map["description"].toString(),
                            duration = map["duration"].toString(),
                            status = map["status"].toString(),
                            date = map["date"].toString()
                        )
                    )
                    onSuccess(listOf)
                }
            }
    }

    override fun deleteWorkoutPlan(exercise: Exercise, onSuccess: () -> Unit) {
        firestore.collection("users").document(auth.currentUser!!.uid).collection("workoutPlans")
            .document(exercise.id).delete().addOnSuccessListener {
                Log.d("Firestore", "Workout plan deleted")
                onSuccess()
            }
    }

}