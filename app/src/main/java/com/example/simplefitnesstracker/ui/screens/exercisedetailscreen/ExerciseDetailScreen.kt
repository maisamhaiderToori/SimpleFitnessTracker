package com.example.simplefitnesstracker.ui.screens.exercisedetailscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.simplefitnesstracker.models.Exercise
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun ExerciseDetailScreen(
    vm: ExerciseDetailScreenViewModel,
    deleteWorkoutPlan: (exercise: Exercise) -> Unit,
    onBack: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp)
            .padding(16.dp)
    ) {
        Image(
            imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back button",
            modifier = Modifier
                .clickable(onClick = { onBack() })
                .padding(top = 16.dp, end = 16.dp, bottom = 16.dp),
        )
        val state = vm.state.collectAsState()
        TextField(
            value = state.value.name,
            onValueChange = {
                if (it.length <= 30) {
                    vm.setName(it)
                }
            },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            keyboardActions = KeyboardActions(onNext = {}),
            maxLines = 1,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.value.description,
            onValueChange = {
                if (it.length <= 100) {
                    vm.setDescription(description = it)
                }
            },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onNext = {}),
            maxLines = 1,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = state.value.duration,
            onValueChange = {
                if (it.length <= 3) {
                    vm.setDuration(duration = it)
                }
            },
            label = { Text("Minutes") },
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onNext = {}),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        val statusOptions = listOf("PENDING", "DONE", "IN_PROGRESS")

        StatusDropdown(
            statusOptions,
            selectedStatus = when (state.value.status) {
                "PENDING" -> statusOptions[0]
                "DONE" -> statusOptions[1]
                "IN_PROGRESS" -> statusOptions[2]
                else -> statusOptions[0]
            },
            onStatusChange = { newStatus ->
                vm.setStatus(newStatus)
                // Update your UI or data based on the new status
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Date: " + SimpleDateFormat("dd/MM/yyyy").format(
                if (state.value.date.isEmpty())
                    System.currentTimeMillis() else state.value.date
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { vm.onEdit() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        ) {
            Text(text = "Update")
        }

        Button(
            onClick = {
                deleteWorkoutPlan(state.value.toExercise())

            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Delete", color = MaterialTheme.colorScheme.onSecondary)
        }
    }
}

@Composable
fun StatusDropdown(
    statusOptions: List<String>,
    selectedStatus: String,
    onStatusChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clickable { expanded = !expanded }
            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {

                Text(
                    text = selectedStatus,
                    modifier = Modifier.weight(1f)
                )
                Image(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                statusOptions.forEach { status ->
                    DropdownMenuItem(text = { Text(text = status) },
                        onClick = {
                            onStatusChange(status)
                            expanded = false
                        })
                }
            }
        }
    }

}