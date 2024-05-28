@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.DocEase.ui.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.DocEase.R
import com.example.DocEase.model.enums.PatientStatus
import com.example.DocEase.model.enums.Gender
import com.example.DocEase.model.models.Patients
import com.example.DocEase.ui.screen.navigation.DocBottomNavBar
import com.example.DocEase.ui.screen.navigation.NavigationDestination
import com.example.DocEase.ui.viewModel.AppViewModelProvider
import com.example.DocEase.ui.viewModel.screens.PatientsViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate

object PatientsDestination : NavigationDestination {
    override val route = "patients"
    override val title = "Patients"
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientsScreenNavigation(
    navigateToProfile: ()->Unit,
    navigateToSchedules: ()->Unit,
    navigateToPatient: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = { FloatingActionButton() },
        bottomBar = { DocBottomNavBar(navigateToProfile, navigateToSchedules, {}) }
    ) {
        PatientsScreen(navigateToPatient)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PatientsScreen(
    navigateToPatient: (Int) -> Unit,
    viewModel: PatientsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val PatientsUiStates by viewModel.PatientsUiStates.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(PatientsUiStates.patientList) { patient ->
                PatientCard(patient = patient, navigateToPatient)
            }
        }
    }
}

@Composable
fun PatientCard(patient: Patients, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(350.dp)
            .height(110.dp)
            .clickable(onClick = { onClick(patient.patientId) })
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 10.dp)
        ) {
            Image(
                painter = painterResource(
                    id = getPatientImage(
                        patient.gender.value,
                        patient.patientId
                    )
                ),
                contentDescription = "schedule image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(7.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = patient.name + " " + patient.surname,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(text = patient.email, fontSize = 13.sp)

                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = patient.description, fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = patient.DOJ, fontSize = 16.sp)

                    val color = when (patient.status) {
                        PatientStatus.STABLE -> Color.Green
                        PatientStatus.OBSERVATION -> Color.Yellow
                        PatientStatus.CRITICAL -> Color.Red
                    }
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .aspectRatio(1f)
                            .background(color = color, shape = CircleShape)
                    )

                    Text(text = patient.DOB, fontSize = 16.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FloatingActionButton() {
    var showDialog by remember { mutableStateOf(false) }

    SmallFloatingActionButton(
        onClick = { showDialog = true },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }

    if (showDialog) {
        PatientDialog(onDismiss = { showDialog = false })
    }
}

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun PatientDialog(
    onDismiss: () -> Unit,
    viewModel: PatientsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                val coroutineScope = rememberCoroutineScope()
                val uiState = viewModel.patientsUiState
                val detailsState = uiState.patientsDetails

                var name by remember { mutableStateOf("") }
                var surname by remember { mutableStateOf("") }
                var DOB by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var phoneNumber by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }
                var gender by remember { mutableStateOf("") }
                var genderEnum by remember { mutableStateOf(Gender.FEMALE) }
                var status by remember { mutableStateOf("") }
                var statusEnum by remember { mutableStateOf(PatientStatus.STABLE) }
                var appearGender by remember { mutableStateOf(false) }
                var appearStatus by remember { mutableStateOf(false) }
                var openCalendar by remember { mutableStateOf(false) }
                var checkEmail by remember { mutableStateOf(false) }
                val timeState = rememberDatePickerState()

                //design values
                val shape = RoundedCornerShape(10.dp)
                val spacerModifier = Modifier.height(8.dp)

                Text(text = "Enter Patient Details", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        value = name,
                        onValueChange = {
                            name = it
                            viewModel.updateUiState(detailsState.copy(name = it))
                        },
                        shape = shape,
                        label = { Text("Name") },
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        shape = shape,
                        value = surname,
                        onValueChange = {
                            surname = it
                            viewModel.updateUiState(detailsState.copy(surname = it))
                        },
                        label = { Text("Surname") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(modifier = spacerModifier)
                Box(
                    modifier = Modifier
                ) {
                    TextField(
                        modifier = Modifier
                            .border(width = 1.dp, color = Color.Gray, shape = shape)
                            .fillMaxWidth(),
                        value = gender,
                        onValueChange = { },
                        readOnly = true,
                        placeholder = {
                            Text(text = "Gender")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White
                        ),
                        trailingIcon = {
                            Icon(Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = Modifier.clickable { appearGender = true })
                        },
                    )
                    DropdownMenu(
                        expanded = appearGender,
                        onDismissRequest = { appearGender = false },
                        modifier = Modifier.width(280.dp)
                    ) {
                        Gender.entries.map {
                            DropdownMenuItem(text = {
                                Text(text = it.value)
                            }, onClick = {
                                gender = it.value
                                genderEnum = it
                                viewModel.updateUiState(detailsState.copy(gender = it))
                                appearGender = false
                            }, modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                Spacer(modifier = spacerModifier)
                Box(
                    modifier = Modifier
                ) {
                    TextField(
                        modifier = Modifier
                            .border(width = 1.dp, color = Color.Gray, shape = shape)
                            .fillMaxWidth(),
                        value = status,

                        onValueChange = { },
                        readOnly = true,
                        placeholder = {
                            Text(text = "Status")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White
                        ),
                        trailingIcon = {
                            Icon(Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = Modifier.clickable { appearStatus = true })
                        },
                    )
                    DropdownMenu(
                        expanded = appearStatus,
                        onDismissRequest = { appearStatus = false },
                        modifier = Modifier.width(280.dp)
                    ) {
                        PatientStatus.entries.map {
                            DropdownMenuItem(text = {
                                Text(text = it.value)
                            }, onClick = {
                                status = it.value
                                statusEnum = it
                                viewModel.updateUiState(detailsState.copy(status = it))
                                appearStatus = false
                            }, modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                Spacer(modifier = spacerModifier)
                OutlinedTextField(
                    shape = shape,
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                        viewModel.updateUiState(detailsState.copy(phoneNumber = it))
                    },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = spacerModifier)
                OutlinedTextField(
                    shape = shape,
                    value = email,
                    onValueChange = {
                        email = it
                        viewModel.updateUiState(detailsState.copy(email = it))
                    },
                    isError = checkEmail,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = spacerModifier)

                OutlinedTextField(
                    shape = shape,
                    value = DOB,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Date Of Birth") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(
                            onClick = { openCalendar = true }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Select Date")
                        }
                    },

                    )
                if (openCalendar) {
                    DatePickerDialog(
                        onDismissRequest = { openCalendar = false },
                        confirmButton = {
                            Button(onClick = {
                                openCalendar = false
                                val dateString =
                                    SimpleDateFormat("yyyy-MM-dd").format(timeState.selectedDateMillis)
                                val selectedDate = LocalDate.parse(dateString)
                                DOB =
                                    "${selectedDate.dayOfMonth}-${selectedDate.monthValue}-${selectedDate.year}"
                                viewModel.updateUiState(detailsState.copy(DOB = DOB))
                            }

                            ) {
                                Text(text = "Confirm")
                            }
                        }) {
                        DatePicker(
                            state = timeState
                        )
                    }
                }

                Spacer(modifier = spacerModifier)
                OutlinedTextField(
                    shape = shape,
                    value = description,
                    onValueChange = {
                        description = it
                        viewModel.updateUiState(detailsState.copy(description = it))
                    },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        checkEmail = !checkEmail(email)
                        if (!checkEmail) {
                            coroutineScope.launch {
                                if (viewModel.checkPatient()) {
                                    viewModel.addPatient()
                                    onDismiss()
                                } else {
                                    checkEmail = true
                                }
                            }
                        }
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}


fun getPatientImage(gender: String, Id: Int): Int {
    val newIndex = Id % 4
    if (gender == "Male") {
        return when (newIndex) {
            0 -> R.drawable.man_person_people_avatar_icon_230017
            1 -> R.drawable.male3_512
            2 -> R.drawable.beard_hipster_male_svgrepo_com
            3 -> R.drawable.avatar_male_man_svgrepo_com
            else -> R.drawable.man_person_people_avatar_icon_230017
        }
    }
    return when (newIndex) {
        0 -> R.drawable.people_avatar_icon_png
        1 -> R.drawable.pngwing_com
        2 -> R.drawable.artist_avatar_marilyn_svgrepo_com
        3 -> R.drawable.avatar_svgrepo_com
        else -> R.drawable.avatar_female_portrait_svgrepo_com
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PatientsScreenPreview() {
    PatientsScreen({})
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PatientsScreenNavigationPreview(){
    PatientsScreenNavigation({}, {}, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PatientDialogPreview() {
    PatientDialog({})
}