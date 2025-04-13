package com.example.tabata.data.dao

import androidx.room.*
import com.example.tabata.data.entities.WorkoutSequence

@Dao
interface SequenceDao {

    @Query("SELECT * FROM sequences")
    suspend fun getAllSequences(): List<WorkoutSequence>

    @Query("SELECT * FROM sequences WHERE id = :id")
    suspend fun getSequenceById(id: kotlin.String): WorkoutSequence?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSequence(sequence: WorkoutSequence): Long

    @Update
    suspend fun updateSequence(sequence: WorkoutSequence)

    @Delete
    suspend fun deleteSequence(sequence: WorkoutSequence)

    @Query("DELETE FROM sequences")
    suspend fun deleteAllSequences()
}
