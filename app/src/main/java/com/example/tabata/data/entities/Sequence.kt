package com.example.tabata.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sequences")
data class WorkoutSequence(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    val title: String,
    val color: String,

    val warmUpDuration: Int,
    val workoutDuration: Int,
    val restDuration: Int,
    val cooldownDuration: Int,

    val rounds: Int,
    val restBetweenSets: Int
)

