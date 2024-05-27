@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.DocEase.ui.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.DocEase.R
import com.example.DocEase.model.PatientList
import com.example.DocEase.model.enums.PatientStatus
import com.example.DocEase.model.models.Patients
import com.example.DocEase.ui.screen.navigation.DocBottomNavBar
import com.example.DocEase.ui.screen.navigation.NavigationDestination
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
    navigateToProfile: (Int)->Unit,
    navigateToSchedules: ()->Unit,
    profileId: Int,
    navigateToPatient: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = { FloatingActionButton() },
        bottomBar = { DocBottomNavBar(navigateToProfile, navigateToSchedules, {}, profileId) }
    ) {
        PatientsScreen(navigateToPatient)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PatientsScreen(navigateToPatient: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            itemsIndexed(PatientList.patientList) { index, patient ->
                PatientCard(patient = patient, index = index, navigateToPatient)
            }
        }
    }
}

@Composable
fun PatientCard(patient: Patients, index: Int, onClick: (Int) -> Unit) {
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
                painter = painterResource(id = getPatientImage(patient.gender, index)),
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
                        Text(text = patient.name + " " + patient.surname, fontSize = 18.sp)

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
                        PatientStatus.CRITICAL -> Color.Yellow
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Enter Details")

                Spacer(modifier = Modifier.height(8.dp))


                var patientName by remember { mutableStateOf("") }
                var patientSurName by remember { mutableStateOf("") }
                var DOB by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var phoneNumber by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }


                //design values
                val shape = RoundedCornerShape(10.dp)

                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        value = patientName,
                        onValueChange = {
                            patientName = it
                        },
                        shape = shape,
                        label = { Text("Name") },
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        shape = shape,
                        value = patientSurName,
                        onValueChange = {
                            patientSurName = it
                        },
                        label = { Text("Surname") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    shape = shape,
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                    },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    shape = shape,
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                )

                var openCalendar by remember { mutableStateOf(false) }
                val state = rememberDatePickerState()

                Spacer(modifier = Modifier.height(8.dp))

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
                                    SimpleDateFormat("yyyy-MM-dd").format(state.selectedDateMillis)
                                val selectedDate = LocalDate.parse(dateString)
                                DOB =
                                    "${selectedDate.year}-${selectedDate.month}-${selectedDate.dayOfMonth}"
                            }

                            ) {
                                Text(text = "Confirm")
                            }
                        }) {
                        DatePicker(
                            state = state
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    shape = shape,
                    value = description,
                    onValueChange = {
                        description = it
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
                        onDismiss()
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}


fun getPatientImage(gender: String, index: Int): Int {
    val newIndex = index % 4
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
    PatientsScreen {}
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PatientsScreenNavigationPreview(){
    PatientsScreenNavigation({}, {}, 3, {})
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PatientDialogPreview() {
    PatientDialog {}
}