package com.example.simpleloginapp.Screens.Auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.simpleloginapp.util.SessionManager
import kotlinx.coroutines.delay

@Composable
fun OTPScreen(
    contact: String,
    onOtpVerified: () -> Unit
) {
    val context = LocalContext.current

    var otp by remember { mutableStateOf("") }
    var isTimerRunning by remember { mutableStateOf(true) }
    var timeLeft by remember { mutableIntStateOf(30) }

    // Start countdown
    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            isTimerRunning = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text("Enter OTP (123456)", style = MaterialTheme.typography.headlineMedium)
        Text("We've sent an OTP to your mobile/email", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = {
                if (it.length <= 6 && it.all { c -> c.isDigit() }) otp = it
            },
            label = { Text("OTP") },
            placeholder = { Text("Enter 6-digit code") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isTimerRunning) {
            Text("Resend OTP in 00:${timeLeft.toString().padStart(2, '0')}")
        } else {
            TextButton(
                onClick = {
                    isTimerRunning = true
                    timeLeft = 30
//                    onResend()
                }
            ) {
                Text("Resend OTP")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { if (otp.equals("123456",true)) {
                SessionManager.saveSession(context,"123456")

                onOtpVerified()
            }},
            enabled = otp.length == 6,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify OTP")
        }
    }
}