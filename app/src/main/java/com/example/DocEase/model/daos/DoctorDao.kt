package com.example.DocEase.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.DocEase.model.models.Doctors
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(doctor: Doctors)

    @Update
    suspend fun update(doctor: Doctors)

    @Delete
    suspend fun delete(doctor: Doctors)

    @Query("SELECT * FROM doctors WHERE doctorId = :doctorId")
    fun getDoctor(doctorId: Int): Flow<Doctors>

    @Query("SELECT * FROM doctors")
    fun getDoctors(): Flow<List<Doctors>>
}
