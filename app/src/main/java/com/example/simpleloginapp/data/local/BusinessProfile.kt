package com.example.simpleloginapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BusinessProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val businessName: String,
    val businessEmail: String,
    val businessAddress: String,
    val businessPhone: String,
    val location: String,
    val customerCount: Int,
    val contactName: String,
    val contactEmail: String,
    val contactPhone: String,
    val designation: String,
    val dob: String,
    val password: String
)