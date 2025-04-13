package com.example.tabata.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tabata.data.dao.SequenceDao
import com.example.tabata.data.entities.WorkoutSequence

@Database(entities = [WorkoutSequence::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sequenceDao(): SequenceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tabata_timer_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
