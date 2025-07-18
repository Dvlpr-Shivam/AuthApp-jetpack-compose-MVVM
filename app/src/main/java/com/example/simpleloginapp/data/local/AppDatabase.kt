package com.example.simpleloginapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BusinessProfile::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun businessDao(): BusinessDao
}
