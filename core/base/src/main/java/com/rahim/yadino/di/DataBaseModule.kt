package com.rahim.yadino.di

import android.app.Application
import androidx.room.Room
import com.rahim.yadino.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {
    private const val DATABASE_NAME = "main database"
    @Singleton
    @Provides
    fun providesMainDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
