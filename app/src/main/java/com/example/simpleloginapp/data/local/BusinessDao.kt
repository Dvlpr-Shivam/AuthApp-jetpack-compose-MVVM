package com.example.simpleloginapp.data.local

import androidx.room.*

@Dao
interface BusinessDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: BusinessProfile)

    @Query("SELECT * FROM BusinessProfile WHERE contactEmail = :email OR contactPhone = :phone")
    suspend fun getByEmailOrPhone(email: String, phone: String): BusinessProfile?
}