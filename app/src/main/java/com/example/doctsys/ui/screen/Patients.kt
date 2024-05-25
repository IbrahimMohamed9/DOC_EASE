package com.example.doctsys.ui.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

object PatientsDestination : NavigationDestination {
    override val route = "patients"
    override val title = "Patients"
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PatientScreenNavigation(
    navigateToProfile: (Int)->Unit,
    navigateToSchedules: ()->Unit,
    profileId: Int
) {
    Scaffold(
        bottomBar = { DocBottomNavBar(navigateToProfile, navigateToSchedules, {},profileId) }
    ) {
        PatientsScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PatientsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(ScheduleList.scheduleList) { schedule ->
                PatientCard(schedule = schedule)
            }
        }
    }
}

@Composable
fun PatientCard(schedule: Schedule) {
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
                        Text(
                            text = schedule.description, fontSize = 15.sp,
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

fun getScheduleImagee(disease: Disease): Int {
    return when (disease) {
        Disease.BACKPAIN -> R.drawable.what_to_do_back_pain_1200x628
        Disease.TEETHPAIN -> R.drawable.toothache_scaled
        Disease.ARMPAIN -> R.drawable.shoulder_pain_495x400
        Disease.LEGPAIN -> R.drawable.sciatica
        else -> R.drawable.sciatica
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PatientsScreenPreview() {
    PatientsScreen()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PatientScreenNavigationPreview(){
    PatientScreenNavigation({},{},1)
}