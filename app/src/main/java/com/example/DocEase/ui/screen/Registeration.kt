package com.example.DocEase.ui.screen

import android.annotation.SuppressLint
import android.os.Build
import android.util.Patterns.EMAIL_ADDRESS
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
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
import com.example.DocEase.model.enums.MedicalSpecialization
import com.example.DocEase.model.enums.Gender
import com.example.DocEase.ui.screen.navigation.NavigationDestination
import com.example.DocEase.ui.viewModel.AppViewModelProvider
import com.example.DocEase.ui.viewModel.screens.LoginRegistrationViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId

object RegistrationDestination : NavigationDestination {
    override val route = "register"
    override val title = "Register"
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegistrationScreenNavigation(
    navigateToLogin: () -> Unit,
    navigateToProfilePage: (Int) -> Unit
) {
    Scaffold {
        RegistrationScreen(
            navigateToLogin = navigateToLogin, navigateToProfilePage = navigateToProfilePage
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navigateToLogin: () -> Unit,
    navigateToProfilePage: (Int) -> Unit,
    viewModel: LoginRegistrationViewModel = viewModel(
        factory =
        AppViewModelProvider.Factory
    )
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    val date = LocalDate.now().minusYears(29)
    val milliseconds = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    val state = rememberDatePickerState(milliseconds)
    var DOB by remember { mutableStateOf("") }
    var medicSpeci by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }
    var showPasswordRepeat by remember { mutableStateOf(false) }
    var checkPassword by remember { mutableStateOf(true) }
    var checkEmail by remember { mutableStateOf(false) }
    var expandedItems by remember { mutableStateOf(false) }
    var expandedMedi by remember { mutableStateOf(false) }
    var openCalendar by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.doctorsUiState
    val detailsState = uiState.doctorsDetails

    val spacerModifier = Modifier.height(10.dp)
    viewModel.updateUiState(detailsState.copy(DOB = "${date.dayOfMonth}-${date.monthValue}-${date.year}"))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth()
            .padding(vertical = 30.dp, horizontal = 8.dp)
            .verticalScroll(
                rememberScrollState()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.doctor),
            contentDescription = "",
            modifier = Modifier
                .size(width = 100.dp, height = 100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = spacerModifier)

        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 30.sp,
            fontFamily = FontFamily.Cursive,
            color = Color.Blue
        )

        Spacer(modifier = spacerModifier)

        Row {
        TextField(
            modifier = Modifier.fillMaxSize(0.5f),
            value = name,
            onValueChange = {
                name = it
                viewModel.updateUiState(detailsState.copy(name = it))
            },
            enabled = true,
            label = {
                Text(text = "name")
            },
            placeholder = {
                Text(text = "name")
            },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )

        )

            Spacer(modifier = Modifier.width(8.dp))

        TextField(
            value = surname,
            onValueChange = {
                surname = it
                viewModel.updateUiState(detailsState.copy(surname = it))
            },
            enabled = true,
            label = {
                Text(text = "surname")
            },
            placeholder = {
                Text(text = "surname")
            },
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        }

        Spacer(modifier = spacerModifier)
        TextField(
            value = email,
            modifier = Modifier.fillMaxSize(),
            onValueChange = {
                email = it
                viewModel.updateUiState(detailsState.copy(email = it))
            },
            enabled = true,
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

        Spacer(modifier = spacerModifier)
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                viewModel.updateUiState(detailsState.copy(phoneNumber = it))
            },
            label = {
                Text(text = "Phone Number")
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = spacerModifier)
        Box {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = gender,

                onValueChange = { },
                readOnly = true,
                placeholder = {
                    Text(text = "Gender")
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expandedItems = true })
                },
            )
            DropdownMenu(
                expanded = expandedItems,
                onDismissRequest = { expandedItems = false },
                modifier = Modifier.width(280.dp)
            ) {
                Gender.entries.map {
                    DropdownMenuItem(text = {
                        Text(text = it.value)
                    }, onClick = {
                        gender = it.value
                        expandedItems = false
                        viewModel.updateUiState(detailsState.copy(gender = it))

                    }, modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = spacerModifier)
        Box {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = DOB,

                onValueChange = { },
                readOnly = true,
                placeholder = {
                    Text(text = "Date Of Birth")
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable { openCalendar = true })
                },
            )
            if (openCalendar) {
                DatePickerDialog(
                    onDismissRequest = { openCalendar = false },
                    confirmButton = {
                        Button(onClick = {
                            openCalendar = false
                            val dateString =
                                SimpleDateFormat("yyyy-MM-dd").format(state.selectedDateMillis)
                            val selectedDate = LocalDate.parse(dateString)
                            DOB =
                                "${selectedDate.dayOfMonth}-${selectedDate.monthValue}-${selectedDate.year}"
                            viewModel.updateUiState(detailsState.copy(DOB = DOB))
                        }) {
                            Text(text = "Confirm")
                        }
                    }) {
                    DatePicker(
                        state = state
                    )
                }
            }
        }

        Spacer(modifier = spacerModifier)
        Box {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = medicSpeci,

                onValueChange = { },
                readOnly = true,
                placeholder = {
                    Text(text = "Medical Specialization")
                },
                trailingIcon = {
                    Icon(Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expandedMedi = true })
                },
            )
            DropdownMenu(
                expanded = expandedMedi,
                onDismissRequest = { expandedMedi = false },
                modifier = Modifier.width(280.dp)
            ) {
                MedicalSpecialization.entries.map {
                    DropdownMenuItem(text = {
                        Text(text = it.value)
                    }, onClick = {
                        medicSpeci = it.value
                        expandedMedi = false
                        viewModel.updateUiState(detailsState.copy(medicalSpecialization = it))
                    }, modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = spacerModifier)
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = password,
            onValueChange = {
                password = it
                viewModel.updateUiState(detailsState.copy(password = it))
            },
            label = {
                Text(text = "password")
            },
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            isError = !checkPassword,
            trailingIcon = {
                Icon(
                    painter = if (showPassword) {
                        painterResource(id = R.drawable.baseline_visibility_24)
                    } else {
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    },
                    contentDescription = "",
                    modifier = Modifier.clickable(onClick = { showPassword = !showPassword })
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = spacerModifier)

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = passwordRepeat,
            onValueChange = { passwordRepeat = it },
            label = {
                Text(text = "Repeat Password")
            },
            visualTransformation = if (showPasswordRepeat) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                Icon(
                    painter = if (showPasswordRepeat) {
                        painterResource(id = R.drawable.baseline_visibility_24)
                    } else {
                        painterResource(id = R.drawable.baseline_visibility_off_24)
                    },
                    contentDescription = "",
                    modifier = Modifier.clickable(onClick = {
                        showPasswordRepeat = !showPasswordRepeat
                    })
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(5.dp))

        TextButton(
            onClick = {
                navigateToLogin()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Have an account?")
        }

        Spacer(modifier = spacerModifier)
        Button(onClick = {
            checkPassword = password == passwordRepeat
            checkEmail = !checkEmail(email)
            if (!(checkPassword && checkEmail)) {
                coroutineScope.launch {
                    if (viewModel.register()) {
                        viewModel.getDoctorByEmail().first()
                            ?.let { navigateToProfilePage(it.doctorId) }
                    } else {
                        checkEmail = true
                    }
                }
            }
        }) {
            Text(
                text = "Registration",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 0.dp)
            )
        }
    }
}

fun checkEmail(email: String): Boolean {
    return EMAIL_ADDRESS.matcher(email).matches()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen({},{})
}