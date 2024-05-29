package com.example.DocEase.ui.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.DocEase.R
import com.example.DocEase.model.enums.PatientStatus
import com.example.DocEase.ui.screen.navigation.DocBottomNavBar
import com.example.DocEase.ui.screen.navigation.NavigationDestination
import com.example.DocEase.ui.screen.navigation.TopAppBar
import com.example.DocEase.ui.viewModel.AppViewModelProvider
import com.example.DocEase.ui.viewModel.screens.PatientViewModel
import kotlinx.coroutines.launch


object PatientDestination : NavigationDestination {
    override val route = "patient"
    override val title = "Patient"
    const val patientIdArg = "patientID"
    val routeWithArgs = "$route/{$patientIdArg}"
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientScreenNavigation(
    navigateToPatients: () -> Unit,
    navigateToSchedules: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(PatientDestination.title, navigateBack) },
        content = { PatientScreen() },
        bottomBar = {
            DocBottomNavBar(
                navigateToProfile,
                navigateToSchedules,
                navigateToPatients
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PatientScreen(
    viewModel: PatientViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val uiState = viewModel.patientsUiState
    val detailsState = uiState.patientsDetails


    val name = detailsState.name
    val surname = detailsState.surname
    val DOJ = detailsState.DOJ
    val email = detailsState.email
    val DOB = detailsState.DOB
    val phoneNumber = detailsState.phoneNumber

    Log.d("details", detailsState.toString())
    var description by remember { mutableStateOf(detailsState.description) }
    var expandedItems by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val id = detailsState.patientId

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 65.dp, bottom = 45.dp)
            .fillMaxHeight()
            .wrapContentSize()
            .verticalScroll(rememberScrollState())
    ) {
        PatientImage(detailsState.gender.value, detailsState.patientId)

        Spacer(modifier = Modifier.size(height = 10.dp, width = 0.dp))
        Field(title = "ID", content = id.toString())

        Spacer(modifier = Modifier.height(10.dp))
        Field(title = "Name", content = "$name $surname")

        Spacer(modifier = Modifier.height(10.dp))
        Field(title = "Date Of Birth", content = DOB)

        Spacer(modifier = Modifier.height(10.dp))
        Field(title = "Date Of Join", content = DOJ)

        Spacer(modifier = Modifier.height(10.dp))
        Box {
            TextField(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth(0.75f),
                value = detailsState.status.value,

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
                        modifier = Modifier.clickable { expandedItems = true })
                },
            )
            DropdownMenu(
                expanded = expandedItems,
                onDismissRequest = { expandedItems = false },
                modifier = Modifier.width(280.dp)
            ) {
                PatientStatus.entries.map {
                    DropdownMenuItem(text = {
                        Text(text = it.value)
                    }, onClick = {
                        expandedItems = false
                        viewModel.updateUiState(detailsState.copy(status = it))
                        coroutineScope.launch {
                            viewModel.updatePatient()
                        }
                    }, modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Column {
            FieldLabel("Description")
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.75f),
                shape = RoundedCornerShape(10.dp),
                value = description,
                onValueChange = {
                    description = it
                    viewModel.updateUiState(detailsState.copy(description = it))
                },
                label = { Text("Description") },
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
            .padding(bottom = 10.dp)
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

@Composable
fun PatientImage(gender: String, ID: Int) {
    val colorsBrush = remember {
        Brush.sweepGradient(
            listOf(
                Color(0xFFDFE988),
                Color(0xFFDEA6E7),
                Color(0xFFE57373),
                Color(0xFFFFB74D),
                Color(0xFFFFF176),
                Color(0xFFAED581),
                Color(0xFF4DD0E1),
                Color(0xFFE9B225)
            )
        )
    }
    val imageSize = 145.dp
    val contentDescription = "Profile Image"
    val contentScale = ContentScale.Crop

    Card {
        Box(
            modifier = Modifier
                .size(imageSize)
                .background(Color.White),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = painterResource(id = getPatientImage(gender, ID)),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier
                    .size(imageSize)
                    .padding(top = 10.dp)
                    .border(
                        BorderStroke(5.dp, colorsBrush), CircleShape
                    )
                    .clip(CircleShape)
            )

            Icon(
                painter = painterResource(R.drawable.baseline_camera_alt_24),
                contentDescription = "Camera",
                modifier = Modifier
                    .size(35.dp)
                    .padding(bottom = 10.dp, end = 10.dp)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PatientScreenPreview() {
    PatientScreen()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PatientScreenNavigationPreview() {
    PatientScreenNavigation({}, {}, {}, {})
}