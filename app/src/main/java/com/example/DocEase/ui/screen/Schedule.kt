package com.example.DocEase.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.DocEase.model.enums.Disease
import com.example.DocEase.model.enums.PatientStatus
import com.example.DocEase.ui.screen.navigation.DocBottomNavBar
import com.example.DocEase.ui.screen.navigation.NavigationDestination
import com.example.DocEase.ui.screen.navigation.TopAppBar
import com.example.DocEase.ui.viewModel.AppViewModelProvider
import com.example.DocEase.ui.viewModel.screens.PatientViewModel
import com.example.DocEase.ui.viewModel.screens.ScheduleViewModel
import kotlinx.coroutines.launch


object ScheduleDestination : NavigationDestination {
    override val route = "schedule"
    override val title = "Schedule"
    const val scheduleIdArg = "scheduleID"
    val routeWithArgs = "$route/{$scheduleIdArg}"
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScheduleScreenNavigation(
    navigateToPatients: () -> Unit,
    navigateToSchedules: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateBack: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(ScheduleDestination.title, navigateBack) },
        content = { ScheduleScreen() },
        bottomBar = {
            DocBottomNavBar(
                navigateToProfile,
                navigateToSchedules,
                navigateToPatients
            )
        }
    )
}

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.schedulesUiState
    val scheduleState = uiState.schedulesDetails

    viewModel.getPatient(scheduleState.patientId)
    val patientState = viewModel.patientsUiState.patientsDetails


    val name = patientState.name
    val surname = patientState.surname
    val DOB = patientState.DOB
    val email = patientState.email
    val phoneNumber = patientState.phoneNumber

    //TODO ask prof naida about this bug enum did not display will
    Log.d("details", patientState.toString())

    var patientStatusItem by remember { mutableStateOf(patientState.status.value) }
    var diseaseItem by remember { mutableStateOf(scheduleState.disease.value) }

    var scheduleDescription by remember { mutableStateOf(scheduleState.description) }
    var patientDescription by remember { mutableStateOf(patientState.description) }

    var expandedPatientStatus by remember { mutableStateOf(false) }
    var expandedDisease by remember { mutableStateOf(false) }
    val context = LocalContext.current

    //TODO ask prof naida about this bug fix image border in top
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .wrapContentSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = getScheduleImage(scheduleState.disease)),
            contentDescription = "schedule image",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(7.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(height = 10.dp, width = 0.dp))
        Field(title = "Schedule Date", content = scheduleState.date)

        Spacer(modifier = Modifier.height(10.dp))
        Field(title = "Patient Name", content = "$name $surname")

        Spacer(modifier = Modifier.height(10.dp))
        Field(title = "Date Of Birth", content = DOB)

        Spacer(modifier = Modifier.height(10.dp))
        Box {
            TextField(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(0.75f),
                value = patientStatusItem,

                onValueChange = { },
                readOnly = true,
                placeholder = {
                    Text(text = "Patient Status")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                trailingIcon = {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expandedPatientStatus = true })
                },
            )
            DropdownMenu(
                expanded = expandedPatientStatus,
                onDismissRequest = { expandedPatientStatus = false },
                modifier = Modifier.width(280.dp)
            ) {
                PatientStatus.entries.map {
                    DropdownMenuItem(text = {
                        Text(text = it.value)
                    }, onClick = {
                        patientStatusItem = it.value
                        expandedPatientStatus = false
                        viewModel.updateUiState(patientState.copy(status = it))
                        coroutineScope.launch {
                            viewModel.updatePatient()
                        }
                    }, modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Box {
            TextField(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(0.75f),
                value = diseaseItem,

                onValueChange = { },
                readOnly = true,
                placeholder = {
                    Text(text = "Disease")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                trailingIcon = {
                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expandedDisease = true })
                },
            )
            DropdownMenu(
                expanded = expandedDisease,
                onDismissRequest = { expandedDisease = false },
                modifier = Modifier.width(280.dp)
            ) {
                Disease.entries.map {
                    DropdownMenuItem(text = {
                        Text(text = it.value)
                    }, onClick = {
                        diseaseItem = it.value
                        expandedDisease = false
                        viewModel.updateUiState(scheduleState.copy(disease = it))
                        coroutineScope.launch {
                            viewModel.updateSchedule()
                        }
                    }, modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Column {
            FieldLabel("Schedule Description")
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.75f),
                shape = RoundedCornerShape(10.dp),
                value = scheduleDescription,
                onValueChange = {
                    scheduleDescription = it
                    viewModel.updateUiState(scheduleState.copy(description = it))
                    coroutineScope.launch {
                        viewModel.updateSchedule()
                    }
                },
                label = { Text("Schedule Description") },
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Column {
            FieldLabel("Patient Description")
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.75f),
                shape = RoundedCornerShape(10.dp),
                value = patientDescription,
                onValueChange = {
                    //TODO ask prof Naida about why did not update the db
                    patientDescription = it
                    viewModel.updateUiState(patientState.copy(description = it))
                    coroutineScope.launch {
                        viewModel.updatePatient()
                    }
                },
                label = { Text("Patient Description") },
            )
        }

        Spacer(modifier = Modifier.size(height = 30.dp, width = 0.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .clickable {
                val i = Intent(Intent.ACTION_SEND)
                val emailAddress = arrayOf(email)
                i.putExtra(Intent.EXTRA_EMAIL, emailAddress)
                i.setType("message/rfc822")
                try {
                    context.startActivity(Intent.createChooser(i, "Choose an Email client : "))
                } catch (s: SecurityException) {
                    Toast
                        .makeText(context, "An error occurred", Toast.LENGTH_LONG)
                        .show()
                }
            }
            .align(Alignment.Start)) {
            Icon(
                Icons.Default.MailOutline,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = email)
        }


        Spacer(modifier = Modifier.size(height = 20.dp, width = 0.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .clickable {
                val number = Uri.parse("tel:$phoneNumber")
                val i = Intent(Intent.ACTION_DIAL, number)
                try {
                    context.startActivity(i)
                } catch (s: SecurityException) {
                    Toast
                        .makeText(context, "An error occurred", Toast.LENGTH_LONG)
                        .show()
                }
            }
            .align(Alignment.Start)) {
            Icon(Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.size(width = 20.dp, height = 0.dp))
            Text(text = phoneNumber)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenPreview() {
    ScheduleScreen()
}

@Preview(showBackground = true)
@Composable
fun ScheduleScreenNavigationPreview() {
    ScheduleScreenNavigation({}, {}, {}, {})
}