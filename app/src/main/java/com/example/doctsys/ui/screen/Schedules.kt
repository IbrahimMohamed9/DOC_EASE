package com.example.doctsys.ui.screen

import android.annotation.SuppressLint
import android.os.Build
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doctsys.R
import com.example.doctsys.model.Disease
import com.example.doctsys.model.Schedule
import com.example.doctsys.model.ScheduleList
import com.example.doctsys.ui.screen.navigation.DocBottomNavBar
import com.example.doctsys.ui.screen.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.time.LocalDate

object SchedulesDestination : NavigationDestination {
    override val route = "schedule"
    override val title = "schedules"
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ScheduleScreenNavigation(
    navigateToProfile: (Int) -> Unit,
    navigateToPatients: () -> Unit,
    profileId: Int
) {
    Scaffold(
        bottomBar = {
            DocBottomNavBar(
                navigateToProfile,
                { },
                navigateToPatients,
                profileId
            )
        }
    ) {
        SchedulesScreen()
    }
}


@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulesScreen() {
    val yesterday = LocalDate.now().minusDays(1)
    val state = rememberDatePickerState()
    var openCalendar by remember { mutableStateOf(false) }
    var showDate by remember { mutableStateOf(false) }
    var sheduleDate by remember { mutableStateOf("${yesterday.month} ${yesterday.dayOfMonth}, ${yesterday.year} Schedule") }


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
                ScheduleCard(schedule = schedule)
            }
        }
        Divider(modifier = Modifier.padding(15.dp))
        if (showDate) {
            val dateString = SimpleDateFormat("yyyy-MM-dd").format(state.selectedDateMillis)
            val selectedDate = LocalDate.parse(dateString)
            sheduleDate =
                "${selectedDate.month} ${selectedDate.dayOfMonth}, ${selectedDate.year} Schedule"
        }

        Text(
            text = sheduleDate, fontSize = 20.sp, modifier = Modifier.align(Alignment.Start)
        )


        LazyColumn {
            items(ScheduleList.scheduleList) { schedule ->
                ScheduleCard(schedule = schedule)
            }
        }
    }
}

@Composable
fun ScheduleCard(schedule: Schedule) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(350.dp)
            .height(110.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 10.dp)
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
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(text = schedule.patientName, fontSize = 18.sp)

                        Text(text = schedule.disease.value, fontSize = 13.sp)

                        Spacer(modifier = Modifier.height(5.dp))
                        Text(text = schedule.description, fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(text = schedule.date, fontSize = 16.sp)
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
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SchedulesScreenPreview() {
    SchedulesScreen()
}