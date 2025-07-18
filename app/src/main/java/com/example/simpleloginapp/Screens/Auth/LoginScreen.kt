package com.example.simpleloginapp.Screens.Auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpleloginapp.R
import com.example.simpleloginapp.data.AuthState
import com.example.simpleloginapp.ui.theme.BgSocial
import com.example.simpleloginapp.ui.theme.BorderColor
import com.example.simpleloginapp.ui.theme.BrandColor
import com.example.simpleloginapp.ui.theme.Primary
import com.example.simpleloginapp.ui.theme.Tertirary
import com.example.simpleloginapp.util.SessionManager
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, redirectSignUp: () -> Unit,onOtpRequested: (String) -> Unit) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val loginState by viewModel.loginState.collectAsState()
    var emailOrPhone by remember { mutableStateOf("") }
    val context = LocalContext.current

    var password by remember {
        mutableStateOf("")
    }
    var isShowPassword by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier.fillMaxSize().padding(20.dp).statusBarsPadding().navigationBarsPadding(),
        color = Color.White
    ) {
        Column(verticalArrangement = Arrangement.Center) {
//            ImageComponent(image = R.drawable.sweet_franky)
            HeadingTextComponent(heading = "Sign In")
            Spacer(modifier = Modifier.height(30.dp))
            Column {
                val typeOfKeyboard: KeyboardType = when ("email ID or mobile") {
                    "email ID" -> KeyboardType.Email
                    "mobile" -> KeyboardType.Phone
                    else -> KeyboardType.Text
                }

                OutlinedTextField(
                    value = emailOrPhone,
                    onValueChange = {
                        emailOrPhone = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = BrandColor,
                        unfocusedBorderColor = BorderColor,
                        focusedTextColor = Color.Black,
//            textColor = Color.Black,
                        focusedLeadingIconColor = BrandColor,
                        unfocusedLeadingIconColor = Tertirary
                    ),
                    shape = MaterialTheme.shapes.small,
                    placeholder = {
                        Text(text = "email ID or Phone", color = Tertirary)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.at_symbol),
                            contentDescription = "at_symbol"
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = typeOfKeyboard,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true
                )
//                MyTextField(labelVal = , )
                Spacer(modifier = Modifier.height(15.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = BrandColor,
                        unfocusedBorderColor = BorderColor,
//            textColor = Color.Black
                    ),
                    shape = MaterialTheme.shapes.small,
                    placeholder = {
                        Text(text = "password", color = Tertirary)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = "at_symbol",
                            tint = Tertirary
                        )
                    },
                    trailingIcon = {
                        val description = if (isShowPassword) "Show Password" else "Hide Password"
                        val iconImage =
                            if (isShowPassword) R.drawable.pheyeclosedfill__1_ else R.drawable.eye_closed
                        IconButton(onClick = {
                            isShowPassword = !isShowPassword
                        }) {
                            Icon(
                                painter = painterResource(id = iconImage),
                                contentDescription = description,
                                tint = Tertirary,
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation()
                )
//                PasswordInputComponent(labelVal = "Password")
//                Spacer(modifier = Modifier.height(15.dp))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    BottomComponent(click={
                        viewModel.login(emailOrPhone, password)
                    }, otpRedirect={
                            onOtpRequested("12345")
                        })
                    Spacer(modifier = Modifier.height(12.dp))
                    BottomLoginTextComponent(
                        initialText = "If you don't have account ",
                        action = "Click here",
                        redirectSignUp={
                            redirectSignUp()
                        }

//                            navController
                    )
                }

            }
            if (loginState is AuthState.Error) {
                Text((loginState as AuthState.Error).error, color = MaterialTheme.colorScheme.error)
            }
            if (loginState is AuthState.Success) {

                LaunchedEffect(Unit) {
                    SessionManager.saveSession(context,emailOrPhone)
                    onLoginSuccess()
                }
            }
        }
    }
}
@Composable
fun HeadingTextComponent(heading: String) {
    Text(
        text = heading,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 45.sp,
        color = Primary,
        fontWeight = FontWeight.Bold
    )
}
@Composable
fun BottomLoginTextComponent(initialText: String, action: String, redirectSignUp: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Tertirary)) {
            append(initialText)
        }
        withStyle(style = SpanStyle(color = BrandColor, fontWeight = FontWeight.Bold)) {
            pushStringAnnotation(tag = action, annotation = action)
            append(action)
        }
    }

    ClickableText(text = annotatedString, onClick = {
        annotatedString.getStringAnnotations(it, it)
            .firstOrNull()?.also { span ->
                Log.d("BottomLoginTextComponent", "${span.item} is Clicked")
                redirectSignUp()
            }
    })
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(labelVal: String, icon: Int) {
    var emailOrPhone by remember { mutableStateOf("") }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputComponent(labelVal: String) {


}
@Composable
fun MyButton(labelVal: String, click: () ->Unit/*, navController: NavHostController*/) {
    Button(
        onClick = {  click()},
        colors = ButtonDefaults.buttonColors(
            containerColor = BrandColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        Text(
            text = labelVal,
            color = Color.White,
            fontSize = 18.sp,
        )
    }
}
@Composable
fun BottomComponent(click: () -> Unit, otpRedirect: () -> Unit) {
    Column {
        MyButton(labelVal = "Continue", click = {
            click()
        })
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                thickness = 1.dp,
                color = Tertirary
            )
            Text(
                text = "OR",
                modifier = Modifier.padding(10.dp),
                color = Tertirary,
                fontSize = 20.sp
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                thickness = 1.dp,
                color = Tertirary
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button(
            onClick = { otpRedirect()},
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = BgSocial
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.google),
//                    contentDescription = "google icon"
//                )
                Text(
                    text = "Login With Otp",
                    fontSize = 18.sp,
                    color = Tertirary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()

                )
            }
        }
    }
}
