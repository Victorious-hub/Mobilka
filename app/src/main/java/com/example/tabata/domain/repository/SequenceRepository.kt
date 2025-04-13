package com.example.tabata.domain.repository
import com.example.tabata.data.entities.WorkoutSequence

interface SequenceRepository {
    suspend fun getAllSequences(): List<WorkoutSequence>
    suspend fun getSequenceById(id: String): WorkoutSequence?
    suspend fun insertSequence(sequence: WorkoutSequence): Long
    suspend fun updateSequence(sequence: WorkoutSequence)
    suspend fun deleteSequence(sequence: WorkoutSequence)
    suspend fun deleteAllSequences()
}