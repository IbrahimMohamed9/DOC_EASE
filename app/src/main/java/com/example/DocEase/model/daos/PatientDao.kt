package com.example.DocEase.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.DocEase.model.models.Patients
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(patient: Patients)

    @Update
    suspend fun update(patient: Patients)

    @Delete
    suspend fun delete(patient: Patients)

    @Query("SELECT * FROM patients WHERE patientId = :patientId")
    fun getPatient(patientId: Int): Flow<Patients>

    @Query("SELECT * FROM patients")
    fun getPatients(): Flow<List<Patients>>

    @Query("SELECT * FROM patients WHERE email = :email")
    fun checkPatient(email: String):Flow<Patients>

    @Query("SELECT * FROM patients WHERE patientId = :patientId")
    fun checkPatientID(patientId: Int):Flow<Patients>
}
