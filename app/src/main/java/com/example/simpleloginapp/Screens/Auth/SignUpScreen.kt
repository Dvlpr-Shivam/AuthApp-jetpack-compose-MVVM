package com.example.simpleloginapp.Screens.Auth

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpleloginapp.data.AuthState
import com.example.simpleloginapp.data.local.BusinessProfile
import com.example.simpleloginapp.ui.theme.BorderColor
import com.example.simpleloginapp.ui.theme.BrandColor
import com.example.simpleloginapp.ui.theme.Tertirary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SignupScreen(onSignupSuccess: () -> Unit){
    Surface(
        modifier = Modifier.fillMaxSize().padding(20.dp).statusBarsPadding().navigationBarsPadding(),
        color = Color.White
    ) {
        RegistrationFormScreen(onSignupSuccess = { onSignupSuccess()})
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationFormScreen(onSignupSuccess: () -> Unit) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val context = LocalContext.current

    val signupState by viewModel.signupState.collectAsState()
    val datePickerState = rememberDatePickerState()
    val openDatePicker = remember { mutableStateOf(false) }

    // Business Details State
    var businessName by remember { mutableStateOf("") }
    var businessEmail by remember { mutableStateOf("") }
    var businessAddress by remember { mutableStateOf("") }
    var businessPhone by remember { mutableStateOf("") }
    var customerCount by remember { mutableFloatStateOf(0f) }

    // Contact Person State
    var fullName by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var designation by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }

    // Authentication
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        HeadingTextComponent(heading = "Sign Up")
        Spacer(modifier = Modifier.height(30.dp))


        Text("Business Details", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = businessName,
            onValueChange = { businessName = it },
            label = { Text("Business Name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrandColor,
                unfocusedBorderColor = BorderColor,
                focusedTextColor = Color.Black,
//            textColor = Color.Black,
                focusedLeadingIconColor = BrandColor,
                unfocusedLeadingIconColor = Tertirary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = businessEmail,
            onValueChange = { businessEmail = it },
            label = { Text("Business Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrandColor,
                unfocusedBorderColor = BorderColor,
                focusedTextColor = Color.Black,
//            textColor = Color.Black,
                focusedLeadingIconColor = BrandColor,
                unfocusedLeadingIconColor = Tertirary
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = businessAddress,
            onValueChange = { businessAddress = it },
            label = { Text("Business Address") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrandColor,
                unfocusedBorderColor = BorderColor,
                focusedTextColor = Color.Black,
//            textColor = Color.Black,
                focusedLeadingIconColor = BrandColor,
                unfocusedLeadingIconColor = Tertirary
            ),
            maxLines = 4
        )

        OutlinedTextField(
            value = businessPhone,
            onValueChange = { if (it.length <= 10) businessPhone = it },
            label = { Text("Business Phone (10-digit)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrandColor,
                unfocusedBorderColor = BorderColor,
                focusedTextColor = Color.Black,
//            textColor = Color.Black,
                focusedLeadingIconColor = BrandColor,
                unfocusedLeadingIconColor = Tertirary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { /* Launch Google Place Picker here */ },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Pick Location (Google Places API)")
        }

        Text("Customer Count: ${customerCount.toInt()}", modifier = Modifier.padding(top = 8.dp))
        Slider(
            value = customerCount,
            onValueChange = { customerCount = it },
            valueRange = 0f..10000f,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text("Contact Person Details", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrandColor,
                unfocusedBorderColor = BorderColor,
                focusedTextColor = Color.Black,
//            textColor = Color.Black,
                focusedLeadingIconColor = BrandColor,
                unfocusedLeadingIconColor = Tertirary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contactEmail,
            onValueChange = { contactEmail = it },
            label = { Text("Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrandColor,
                unfocusedBorderColor = BorderColor,
                focusedTextColor = Color.Black,
//            textColor = Color.Black,
                focusedLeadingIconColor = BrandColor,
                unfocusedLeadingIconColor = Tertirary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contactPhone,
            onValueChange = { if (it.length <= 10) contactPhone = it },
            label = { Text("Phone Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrandColor,
                unfocusedBorderColor = BorderColor,
                focusedTextColor = Color.Black,
//            textColor = Color.Black,
                focusedLeadingIconColor = BrandColor,
                unfocusedLeadingIconColor = Tertirary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = designation,
            onValueChange = { designation = it },
            label = { Text("Designation") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrandColor,
                unfocusedBorderColor = BorderColor,
                focusedTextColor = Color.Black,
//            textColor = Color.Black,
                focusedLeadingIconColor = BrandColor,
                unfocusedLeadingIconColor = Tertirary
            ),
            modifier = Modifier.fillMaxWidth()
        )


        Column (
            modifier = Modifier
                .fillMaxWidth()
                .clickable { openDatePicker.value = true } // Make box clickable
        ) {
          /*  OutlinedTextField(
                value = dob,
                onValueChange = {},
                label = { Text("Date of Birth") },
                modifier = Modifier.fillMaxWidth(), // This is now wrapped
                readOnly = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = BrandColor,
                    unfocusedBorderColor = BorderColor,
                    focusedTextColor = Color.Black,
                    focusedLeadingIconColor = BrandColor,
                    unfocusedLeadingIconColor = Tertirary
                )
            )*/
            Text("Date of Birth")
            Text(dob,Modifier.fillMaxWidth().padding(15.dp).border(2.dp,BorderColor,RoundedCornerShape(15.dp)))

        }

        if (openDatePicker.value) {
            DatePickerDialog(
                onDismissRequest = { openDatePicker.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        val selectedDate = datePickerState.selectedDateMillis?.let {
                            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it))
                        } ?: ""
                        dob = selectedDate
                        openDatePicker.value = false
                    }) {
                        Text("OK")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Authentication", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Set Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BrandColor,
                unfocusedBorderColor = BorderColor,
                focusedTextColor = Color.Black,
//            textColor = Color.Black,
                focusedLeadingIconColor = BrandColor,
                unfocusedLeadingIconColor = Tertirary
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        MyButton(labelVal = "Continue", click = {
            if (validateForm(
                    businessEmail,
                    businessPhone,
                    contactEmail,
                    contactPhone,
                    password
                )
            ) {
                viewModel.signup(
                    BusinessProfile(
                        businessName = businessName,
                        businessEmail = businessEmail,
                        businessAddress = businessAddress,
                        businessPhone = businessPhone,
                        location = "",
                        customerCount = customerCount.toInt(),
                        contactName = fullName,
                        contactEmail = contactEmail,
                        contactPhone = contactPhone,
                        designation = designation,
                        dob = dob,
                        password = password
                    )
                )
            }
            else{
                Toast.makeText(context,"${validateForm(
                    businessEmail,
                    businessPhone,
                    contactEmail,
                    contactPhone,
                    password
                )}, validate the fields!!",Toast.LENGTH_SHORT).show()
            }
        })

        if (signupState is AuthState.Error) {
            Text((signupState as AuthState.Error).error, color = MaterialTheme.colorScheme.error)
        }
        if (signupState is AuthState.Success) {
            LaunchedEffect(Unit) {
                onSignupSuccess()
            }
        }
    }
}

fun validateForm(
    businessEmail: String,
    businessPhone: String,
    contactEmail: String,
    contactPhone: String,
    password: String
): Boolean {
    val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    val phonePattern = Regex("^\\d{10}$")
    val passwordPattern = Regex("^(?=.*[0-9])(?=.*[!@#\$%^&*]).{8,}$")

    return emailPattern.matcher(businessEmail).matches() &&
            phonePattern.matches(businessPhone) &&
            emailPattern.matcher(contactEmail).matches() &&
            phonePattern.matches(contactPhone) &&
            passwordPattern.matches(password)
}
