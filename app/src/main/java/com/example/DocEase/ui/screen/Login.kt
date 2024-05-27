package com.example.DocEase.ui.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.DocEase.R
import com.example.DocEase.ui.screen.navigation.NavigationDestination
import com.example.DocEase.ui.viewModel.AppViewModelProvider
import com.example.DocEase.ui.viewModel.screens.LoginRegistrationViewModel
import kotlinx.coroutines.launch

object LoginDestination : NavigationDestination {
    override val route = "login"
    override val title = "Login"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreenNavigation(
    navigateToProfilePage: (Int) -> Unit,
    navigateToRegister: () -> Unit
) {
    Scaffold {
        LoginScreen(
            navigateToRegister = navigateToRegister, navigateToProfilePage = navigateToProfilePage
        )
    }
}

@Composable
fun LoginScreen(
    navigateToRegister: () -> Unit,
    navigateToProfilePage: (Int) -> Unit,
    viewModel: LoginRegistrationViewModel = viewModel(
        factory =
        AppViewModelProvider.Factory
    )
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }
    var checkEmail by remember { mutableStateOf(false) }
    var checPassword by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    var uiState = viewModel.doctorsUiState
    var detailsState = uiState.doctorsDetails


    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth()
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.doctor),
            contentDescription = "Doctor image",
            modifier = Modifier.size(width = 100.dp, height = 100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.size(width = 0.dp, height = 20.dp))

        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive,
            color = Color.Blue
        )

        Spacer(modifier = Modifier.size(width = 0.dp, height = 20.dp))

        TextField(
            value = email,
            onValueChange = {
                email = it
                viewModel.updateUiState(detailsState.copy(email = it))
            },
            label = {
                Text(text = "email")
            },
            placeholder = {
                Text(text = "example@exmaple.com")
            },
            isError = checkEmail,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.size(width = 0.dp, height = 20.dp))

        TextField(
            value = password,
            onValueChange = {
                password = it
                viewModel.updateUiState(detailsState.copy(password = it))
            },
            isError = checPassword,
            label = {
                Text(text = "password")
            },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                Icon(
                    painter = if (showPassword) {
                        painterResource(id = R.drawable.baseline_visibility_24)
                    } else {
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    },
                    contentDescription = "Visibility Icon",
                    modifier = Modifier.clickable(onClick = { showPassword = !showPassword })
                )
            },
        )

        Spacer(modifier = Modifier.size(width = 0.dp, height = 5.dp))

        TextButton(
            onClick = { navigateToRegister() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Do not have an account")
        }

        Spacer(modifier = Modifier.size(width = 0.dp, height = 20.dp))

        Button(onClick = {
            coroutineScope.launch {
                if(checkEmail(email)) {
                    if (viewModel.login()) {
                        Log.d("login", viewModel.doctorsUiState.toString())
                        navigateToProfilePage(viewModel.doctorsUiState.doctorsDetails.doctorId)
                    } else {
                        checkEmail = true
                        checPassword = true
                    }
                }
                checkEmail = true
            }
        }) {
            Text(
                text = "Login",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(){
    LoginScreen({}, {})
}