package com.example.simpleloginapp.util

import android.content.Context

object SessionManager {
    fun saveSession(context: Context, email: String) {
        context.getSharedPreferences("session", Context.MODE_PRIVATE).edit()
            .putString("user", email).apply()
    }

    fun isLoggedIn(context: Context): String? {
        return context.getSharedPreferences("session", Context.MODE_PRIVATE)
            .getString("user", null)
    }

    fun clearSession(context: Context) {
        context.getSharedPreferences("session", Context.MODE_PRIVATE).edit().clear().apply()
    }
}