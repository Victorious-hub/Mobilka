package com.example.tabata.domain.usecase


import com.example.tabata.data.entities.WorkoutSequence
import com.example.tabata.domain.repository.SequenceRepository
import javax.inject.Inject

class SequenceUseCase @Inject constructor (private val repository: SequenceRepository) {

    suspend fun getAllSequences(): List<WorkoutSequence> {
        return repository.getAllSequences()
    }

    suspend fun getSequenceById(id: String): WorkoutSequence? {
        return repository.getSequenceById(id)
    }

    suspend fun insertSequence(sequence: WorkoutSequence): Long {
        return repository.insertSequence(sequence)
    }

    suspend fun updateSequence(sequence: WorkoutSequence) {
        repository.updateSequence(sequence)
    }

    suspend fun deleteSequence(sequence: WorkoutSequence) {
        repository.deleteSequence(sequence)
    }

    suspend fun deleteAllSequences() {
        repository.deleteAllSequences()
    }
}
