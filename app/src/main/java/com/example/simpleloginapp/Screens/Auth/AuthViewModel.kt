package com.example.simpleloginapp.Screens.Auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleloginapp.data.AuthState
import com.example.simpleloginapp.data.local.BusinessProfile
import com.example.simpleloginapp.data.repository.BusinessRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: BusinessRepository
) : ViewModel() {

    private val _signupState = MutableStateFlow<AuthState>(AuthState.Idle)
    val signupState: StateFlow<AuthState> = _signupState

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    fun signup(profile: BusinessProfile) {
        viewModelScope.launch {
            _signupState.value = AuthState.Loading
            try {
                repository.insertProfile(profile)
                _signupState.value = AuthState.Success("Signup successful")
            } catch (e: Exception) {
                _signupState.value = AuthState.Error("Signup failed")
            }
        }
    }

    fun login(emailOrPhone: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            val profile = repository.getProfile(emailOrPhone, emailOrPhone)
            if (profile != null && profile.password == password) {
                _loginState.value = AuthState.Success("Login successful")
            } else {
                _loginState.value = AuthState.Error("Invalid credentials")
            }
        }
    }

    fun loginWithOtp(emailOrPhone: String, otp: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            val profile = repository.getProfile(emailOrPhone, emailOrPhone)
            if (profile != null && otp == "123456") {
                _loginState.value = AuthState.Success("OTP Login successful")
            } else {
                _loginState.value = AuthState.Error("Invalid OTP or user")
            }
        }
    }
}
