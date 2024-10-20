package com.example.simplefitnesstracker.ui.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplefitnesstracker.R
import com.example.simplefitnesstracker.models.Exercise
import com.example.simplefitnesstracker.singletons.Constant.DONE
import com.example.simplefitnesstracker.singletons.Constant.IN_PROGRESS
import com.example.simplefitnesstracker.singletons.Constant.PENDING


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeVM: HomeViewModel = hiltViewModel(),
    onItemClicked: (Exercise) -> Unit
) {
    homeVM.getWorkoutPlans()
    val workoutPlans = homeVM.workoutPlans.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Exercises") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary // Using primary color from theme
            )
        )
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                items(workoutPlans.value.size) {

                    val exercise = workoutPlans.value[it]

                    ItemExercise(
                        exercise,
                        onItemClicked = onItemClicked
                    )
                }

            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(end = 10.dp)

            ) {
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    }


}

@Composable
fun ItemExercise(exercise: Exercise, onItemClicked: (Exercise) -> Unit) {
    Box(
        modifier = Modifier
            .clickable {
                onItemClicked(exercise)
            }
            .fillMaxWidth(1f)
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.secondary),
    ) {
        Column(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(
                text = exercise.name,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp)
            )
            Text(
                text = when (exercise.status) {
                    PENDING -> stringResource(R.string.pending)
                    DONE -> stringResource(R.string.done)
                    IN_PROGRESS -> stringResource(R.string.in_progress)
                    else -> stringResource(R.string.unknown)

                },
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp),
                fontSize = 10.sp
            )
        }
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
            )
            Image(
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "Move to e♦xercise",
                modifier = Modifier
                    .padding(end = 10.dp),

                )
        }


    }
}

