package com.example.simpleloginapp.data.repository

import com.example.simpleloginapp.data.local.BusinessDao
import com.example.simpleloginapp.data.local.BusinessProfile
import javax.inject.Inject

class BusinessRepository @Inject constructor(private val dao: BusinessDao) {
    suspend fun insertProfile(profile: BusinessProfile) = dao.insert(profile)
    suspend fun getProfile(email: String, phone: String) = dao.getByEmailOrPhone(email, phone)
}