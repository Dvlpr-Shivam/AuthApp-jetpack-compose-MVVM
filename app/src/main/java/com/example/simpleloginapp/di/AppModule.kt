package com.example.simpleloginapp.di

import android.app.Application
import androidx.room.Room
import com.example.simpleloginapp.data.local.AppDatabase
import com.example.simpleloginapp.data.local.BusinessDao
import com.example.simpleloginapp.data.repository.BusinessRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "signup_db").build()
    }

    @Provides
    fun provideDao(db: AppDatabase): BusinessDao = db.businessDao()

    @Provides
    fun provideRepository(dao: BusinessDao): BusinessRepository = BusinessRepository(dao)
}