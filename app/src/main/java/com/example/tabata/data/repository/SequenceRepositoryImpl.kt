package com.example.tabata.data.repository

import com.example.tabata.data.dao.SequenceDao
import com.example.tabata.data.entities.WorkoutSequence
import com.example.tabata.domain.repository.SequenceRepository

class SequenceRepositoryImpl(
    private val dao: SequenceDao
) : SequenceRepository {

    override suspend fun getAllSequences(): List<WorkoutSequence> = dao.getAllSequences()

    override suspend fun getSequenceById(id: String): WorkoutSequence? = dao.getSequenceById(id)

    override suspend fun insertSequence(sequence: WorkoutSequence): Long = dao.insertSequence(sequence)

    override suspend fun updateSequence(sequence: WorkoutSequence) = dao.updateSequence(sequence)

    override suspend fun deleteSequence(sequence: WorkoutSequence) = dao.deleteSequence(sequence)

    override suspend fun deleteAllSequences() = dao.deleteAllSequences()
}
