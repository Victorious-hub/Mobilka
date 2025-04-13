package com.example.converter.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.tabata.data.dao.SequenceDao
import com.example.tabata.data.db.AppDatabase
import com.example.tabata.data.repository.SequenceRepositoryImpl
import com.example.tabata.domain.repository.SequenceRepository
import com.example.tabata.domain.usecase.SequenceUseCase
import com.example.tabata.presentation.screens.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.lifecycle.HiltViewModelMap
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.dataStoreFile("settings.preferences_pb")
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "tabata_timer_db" // Your database name
        ).build()
    }

    @Provides
    @Singleton
    fun provideSequenceDao(appDatabase: AppDatabase): SequenceDao {
        return appDatabase.sequenceDao()
    }

    @Provides
    @Singleton
    fun provideSequenceRepository(dao: SequenceDao): SequenceRepository {
        return SequenceRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideUseCase(repository: SequenceRepository): SequenceUseCase {
        return SequenceUseCase(repository)
    }

}