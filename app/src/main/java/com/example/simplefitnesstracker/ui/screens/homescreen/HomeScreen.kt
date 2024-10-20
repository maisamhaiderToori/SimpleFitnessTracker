package com.example.simplefitnesstracker.ui.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeVM: HomeViewModel = viewModel()
) {

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Exercises") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary // Using primary color from theme
            )
        )
    }) { paddingValues ->
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(top = 10.dp)
            ) {
                items(10) {
                    ItemExercise(name = "Exercise $it", onItemClicked = {})
                }

            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(end = 10.dp, bottom = 20.dp)

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
fun ItemExercise(name: String, onItemClicked: () -> Unit) {
    Box(modifier = Modifier
            .fillMaxWidth(1f)
            .height(54.dp)
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),

        ) {

            Text(
                text = name,
                modifier = Modifier
                    .padding(start = 10.dp).align(Alignment.CenterStart),
            )
            Image(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Move to exercise",
                alignment = Alignment.CenterEnd
            )

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenScreenPreview() {
    HomeScreen()
}