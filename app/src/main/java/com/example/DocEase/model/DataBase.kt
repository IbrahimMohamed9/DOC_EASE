package com.example.DocEase.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.DocEase.model.daos.DoctorDao
import com.example.DocEase.model.daos.PatientDao
import com.example.DocEase.model.daos.ScheduleDao
import com.example.DocEase.model.models.Doctors
import com.example.DocEase.model.models.Patients
import com.example.DocEase.model.models.Schedules

@Database(entities = [Doctors::class, Patients::class, Schedules::class], version = 1, exportSchema = false)
abstract class StudentDatabase: RoomDatabase() {
    abstract fun doctorDao(): DoctorDao
    abstract fun patientDao(): PatientDao
    abstract fun scheduleDao(): ScheduleDao


    companion object{
        @Volatile
        private var Instance: StudentDatabase? = null


        fun getDatabase(context: Context): StudentDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, StudentDatabase::class.java, "DocsysDatabase")
                    .build().also { Instance = it }
            }
        }


    }
}
