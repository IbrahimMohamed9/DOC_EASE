package com.example.DocEase.ui.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import com.example.DocEase.model.enums.Disease
import com.example.DocEase.model.models.Schedules
import com.example.DocEase.model.ScheduleList
import com.example.DocEase.ui.screen.navigation.DocBottomNavBar
import com.example.DocEase.ui.screen.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.time.LocalDate

object SchedulesDestination : NavigationDestination {
    override val route = "schedule"
    override val title = "schedules"
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SchedulesScreenNavigation(
    navigateToProfile: (Int) -> Unit,
    navigateToPatients: () -> Unit,
    profileId: Int,
    navigateToSchedule: (Int) -> Unit
) {
    Scaffold(
        floatingActionButton = { FloatingActionButtonFun() },
        content = { SchedulesScreen(navigateToSchedule) },
        bottomBar = {
            DocBottomNavBar(
                navigateToProfile, { }, navigateToPatients, profileId
            )
        },
    )
}


@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulesScreen(navigateToSchedule: (Int) -> Unit) {
    val tomorrow = LocalDate.now().plusDays(1)
    val state = rememberDatePickerState()
    var openCalendar by remember { mutableStateOf(false) }
    var showDate by remember { mutableStateOf(false) }
    var sheduleDate by remember { mutableStateOf("${tomorrow.month} ${tomorrow.dayOfMonth}, ${tomorrow.year} Schedules") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.DateRange,
            contentDescription = "calender Icon",
            modifier = Modifier
                .clickable { openCalendar = true }
                .align(Alignment.Start))
        Spacer(modifier = Modifier.height(10.dp))
        if (openCalendar) {
            DatePickerDialog(onDismissRequest = { openCalendar = false }, confirmButton = {
                Button(onClick = { showDate = true;openCalendar = false }) {
                    Text(text = "Confirm")
                }
            }) {
                DatePicker(
                    state = state
                )
            }
        }

        Text(text = "Today Schedules", fontSize = 20.sp, modifier = Modifier.align(Alignment.Start))
        LazyRow {
            items(ScheduleList.scheduleList) { schedule ->
                ScheduleCard(schedule = schedule, navigateToSchedule)
            }
        }
        Divider(modifier = Modifier.padding(15.dp))
        if (showDate) {
            val dateString = SimpleDateFormat("yyyy-MM-dd").format(state.selectedDateMillis)
            val selectedDate = LocalDate.parse(dateString)
            sheduleDate =
                "${selectedDate.month} ${selectedDate.dayOfMonth}, ${selectedDate.year} Schedules"
        }

        Text(
            text = sheduleDate, fontSize = 20.sp, modifier = Modifier.align(Alignment.Start)
        )


        LazyColumn {
            items(ScheduleList.scheduleList) { schedule ->
                ScheduleCard(schedule = schedule, navigateToSchedule)
            }
        }
    }
}

@Composable
fun ScheduleCard(schedule: Schedules, navigateToSchedule: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(350.dp)
            .height(110.dp)
            .clickable { navigateToSchedule(schedule.scheduleId) }
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(5.dp)
        ) {
            Image(
                painter = painterResource(id = getScheduleImage(schedule.disease)),
                contentDescription = "schedule image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(7.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(text = "schedule patientName", fontSize = 18.sp)
                Text(text = schedule.disease.value, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = schedule.description,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${schedule.price} KM", fontSize = 16.sp)
                    Text(text = schedule.date, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun FloatingActionButtonFun() {
    var showDialog by remember { mutableStateOf(false) }

    SmallFloatingActionButton(
        onClick = { showDialog = true },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }

    if (showDialog) {
        ScheduleDialog(onDismiss = { showDialog = false })
    }
}

@Composable
fun ScheduleDialog(onDismiss: () -> Unit) {
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


//                TODO finish this by using those variable
                var patientId: Int by remember { mutableStateOf(0) }
                var patientIdText by remember { mutableStateOf("") }
                var patientIdError by remember { mutableStateOf(false) }
                var price by remember { mutableStateOf(100) }
                var description by remember { mutableStateOf("") }
                var dropDownItem by remember { mutableStateOf("") }
                var expandedItems by remember { mutableStateOf(false) }

                //design values
                val keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
                val shape = RoundedCornerShape(10.dp)

                Box(
                    modifier = Modifier
                ) {
                    TextField(
                        modifier = Modifier
                            .border(width = 1.dp, color = Color.Gray, shape = shape)
                            .fillMaxWidth(),
                        value = dropDownItem,

                        onValueChange = { },
                        readOnly = true,
                        placeholder = {
                            Text(text = "Disease")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            unfocusedIndicatorColor = Color.White
                        ),
                        trailingIcon = {
                            Icon(Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                modifier = Modifier.clickable { expandedItems = true })
                        },
                    )
                    DropdownMenu(
                        expanded = expandedItems,
                        onDismissRequest = { expandedItems = false },
                        modifier = Modifier.width(280.dp)
                    ) {
                        Disease.entries.map {
                            DropdownMenuItem(text = {
                                Text(text = it.value)
                            }, onClick = {
                                dropDownItem = it.value
                                expandedItems = false
                            }, modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = patientIdText,
                    onValueChange = {
                        patientIdText = it
                        patientId = patientIdText.toIntOrNull() ?: 0
                    },
                    shape = shape,
                    label = { Text("Patient ID") },
                    keyboardOptions = keyboardOptions,
                    isError = patientIdError
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    shape = shape,
                    value = price.toString(),
                    onValueChange = {
                        price = it.toIntOrNull() ?: 0
                    },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = keyboardOptions
                )

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
                        if (patientIdText.toIntOrNull() == null) {
                            patientIdError = true
                        } else {
                            patientIdError = false
                            onDismiss()
                        }
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

fun getScheduleImage(disease: Disease): Int {
    return when (disease) {
        Disease.BACKPAIN -> R.drawable.what_to_do_back_pain_1200x628
        Disease.TEETHPAIN -> R.drawable.toothache_scaled
        Disease.ARMPAIN -> R.drawable.shoulder_pain_495x400
        Disease.LEGPAIN -> R.drawable.sciatica
        Disease.HEADACHE -> R.drawable.headache
        Disease.SORETHROAT -> R.drawable.sore_throat
        Disease.COUGH -> R.drawable.cough
        Disease.STOMACHACHE -> R.drawable.stomachache
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SchedulesScreenPreview() {
    SchedulesScreen {}
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SchedulesScreenNavigationPreview() {
    SchedulesScreenNavigation({}, {}, 1, {})
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ScheduleDialogPreview() {

    ScheduleDialog({ })
}
