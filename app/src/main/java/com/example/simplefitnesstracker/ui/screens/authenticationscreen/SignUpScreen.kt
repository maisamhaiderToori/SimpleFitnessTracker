package com.example.simplefitnesstracker.ui.screens.authenticationscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplefitnesstracker.R


@Composable
fun SignUpScreen(
    authVM: SignUpViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val signUpState = authVM.signUpState.collectAsState()

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        val state = authVM.state.collectAsState()
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp),
            text = stringResource(
                if (state.value.userExitedButLoggedOut) R.string.login else R.string.sign_up
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
                .padding(top = 20.dp, start = 10.dp, bottom = 10.dp),
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
            keyboardActions = KeyboardActions(onDone = {
                Log.i("AuthenticationScreen", "inputEmail: KeyboardActions")
            }),
            maxLines = 1

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
                if (passwordState.value.length in 0..20) {
                    passwordState.value = it
                } else {
                    passwordState.value = passwordState.value.substring(0, 19)
                }
            },
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
            keyboardActions = KeyboardActions(onDone = {
                Log.i("AuthenticationScreen", "passwordState: KeyboardActions")
            }),
            maxLines = 1,

            )

        Button(
            onClick = {
                authVM.createUserWithEmailAndPassword(
                    emailState.value,
                    passwordState.value
                )
            }, modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 32.dp)
                .height(60.dp)
                .fillMaxWidth(1f)
        ) {
            Text(text = stringResource(if (state.value.userExitedButLoggedOut) R.string.login else R.string.sign_up))
        }
        // Observing the sign-up result and updating UI
        when (val state = signUpState.value) {
            is SignUpState.Success -> {
                Text(text = "User signed up successfully: ${state.user?.email}")
            }

            is SignUpState.Failure -> {
                Toast.makeText(context, "Authentication failed: ${state.error}", Toast.LENGTH_SHORT)
                    .show()
                authVM.changeSignUpState(SignUpState.Idle)
            }

            is SignUpState.Loading -> {
                Text(text = "Signing up...")
            }

            is SignUpState.Idle -> {
                // No action yet
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    SignUpScreen()
}