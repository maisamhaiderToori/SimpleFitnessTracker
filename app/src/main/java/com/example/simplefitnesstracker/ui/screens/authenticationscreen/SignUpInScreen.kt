package com.example.simplefitnesstracker.ui.screens.authenticationscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplefitnesstracker.R
import com.example.simplefitnesstracker.data.local.constant.DEFAULT_EXERCISES


@Composable
fun SignUpInScreen(
    vM: SignUpInViewModel = hiltViewModel(),
    onSuccessfulAuth: () -> Unit
) {
    val context = LocalContext.current
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val state = vM.state.collectAsState()
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .scrollable(
                state = rememberScrollState(),
                orientation = Orientation.Vertical,
                enabled = true
            ),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp),
            text = stringResource(
                R.string.authentication
            ), fontSize = MaterialTheme.typography.headlineLarge.fontSize
        )

        Image(
            painter = painterResource(id = R.drawable.ic_warrior_pose),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(30.dp)
                .size(150.dp)
        )
        Text(
            modifier = Modifier
                .padding(top = 15.dp, start = 10.dp, bottom = 10.dp),
            text = stringResource(R.string.email), fontSize = TextStyle.Default.fontSize
        )
        /**
         * input email
         * */
        TextField(
            value = emailState.value,
            placeholder = { Text(text = stringResource(R.string.abs_gmail_com)) },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 10.dp),


            onValueChange = {
                emailState.value = it
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            keyboardActions = KeyboardActions(onNext = {
                Log.i("AuthenticationScreen", "inputEmail: KeyboardActions")
            }),
            maxLines = 1,
            singleLine = true

        )
        Text(
            modifier = Modifier
                .padding(top = 20.dp, start = 10.dp, bottom = 10.dp),
            text = stringResource(R.string.password),
            fontSize = TextStyle.Default.fontSize
        )

        /**
         * Password input
         * */
        TextField(
            value = passwordState.value,
            placeholder = { Text(text = "********") },
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(horizontal = 10.dp),

            onValueChange = {
                if (it.length <= 30) {
                    passwordState.value = it
                }
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            keyboardActions = KeyboardActions(onGo = {
                if (passwordState.value.length >= 8) {
                    Log.i("AuthenticationScreen", "Password is valid: ${passwordState.value}")

                } else {

                    Toast.makeText(
                        context,
                        "Password too short. Must be at least 8 characters.",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Handle too short password case, e.g., show error message
                }
            }),
            maxLines = 1,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, bottom = 10.dp),
            text = stringResource(R.string.password_must_be_at_least_8_characters),
            fontSize = 10.sp
        )


        Button(
            onClick = {
                vM.signupUserWithEmailAndPassword(
                    emailState.value,
                    passwordState.value
                )
            }, modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 32.dp)
                .height(60.dp)
                .fillMaxWidth(1f)

        ) {
            Text(text = stringResource(R.string.sign_up))
        }
        Button(
            onClick = {
                vM.signInWithEmailAndPassword(
                    emailState.value,
                    passwordState.value
                )
            }, modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
                .height(60.dp)
                .fillMaxWidth(1f)

        ) {
            Text(text = stringResource(R.string.login))
        }

        // Observing the sign-up result and updating UI
        when (val state = state.value.signUpInState) {
            is SignUpInState.Success -> {
                if (state.isSignUp) {
                    vM.uploadWorkoutPlan(exercises = DEFAULT_EXERCISES)
                }

                vM.changeSignUpState(SignUpInState.Idle)
                onSuccessfulAuth()
            }

            is SignUpInState.Failure -> {
                Toast.makeText(context, "Authentication failed: ${state.error}", Toast.LENGTH_SHORT)
                    .show()
                vM.changeSignUpState(SignUpInState.Idle)
            }

            is SignUpInState.Loading -> {}

            is SignUpInState.Idle -> {
                // No action yet
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    SignUpInScreen() {}
}