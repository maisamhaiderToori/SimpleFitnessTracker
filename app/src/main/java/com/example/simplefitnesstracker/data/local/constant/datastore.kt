package com.example.simplefitnesstracker.data.local.constant

import com.example.simplefitnesstracker.models.Exercise
import com.example.simplefitnesstracker.singletons.Constant.PENDING


val DEFAULT_EXERCISES = listOf(
    Exercise(
        name = "Running",
        description = "slow pace",
        duration = "10",
        status = PENDING,
        date = ""
    ),
    Exercise(
        name = "Chest day",
        description = "Do number weight",
        duration = "50",
        status = PENDING,
        date = ""
    ),
    Exercise(
        name = "Shoulder day",
        description = "6 exercises",
        duration = "50",
        status = PENDING,
        date = ""
    ),

    Exercise(
        name = "Back day",
        description = "8 Exercises",
        duration = "50",
        status = PENDING,
        date = ""
    ),
    Exercise(
        name = "Leg day",
        description = "5 Exercises",
        duration = "50",
        status = PENDING,
        date = ""
    ),
    Exercise(
        name = "Cardio day",
        description = "Cardio exercise",
        duration = "50",
        status = PENDING,
        date = ""
    )


)