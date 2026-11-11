package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.atec.playgamesnow_mobile.view.components.CheckoutCard
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.view.components.RoundedTextField
import pt.atec.playgamesnow_mobile.viewmodel.auth.LoginViewModel

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginViewModel.token) {
        if (loginViewModel.token != null) {
            onLoginClick()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        Box(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {

            CheckoutCard {

                Column(
                    modifier = Modifier.fillMaxWidth(),

                ) {

                    Text(
                        text = "Welcome Back!",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Sign in to access your account and purchases.",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(15.dp))

                    RoundedTextField(
                        value = loginViewModel.email,
                        onValueChange = { loginViewModel.updateEmail(it) },
                        label = "Email",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = KeyboardType.Email
                    )

                    Spacer(Modifier.height(14.dp))

                    OutlinedTextField(
                        value = loginViewModel.password,
                        onValueChange = { loginViewModel.updatePassword(it) },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(30.dp),
                        visualTransformation = if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    passwordVisible = !passwordVisible
                                }
                            ) {
                                Icon(
                                    imageVector = if (passwordVisible) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },
                                    contentDescription = if (passwordVisible) {
                                        "Hide password"
                                    } else {
                                        "Show password"
                                    }
                                )
                            }
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color(0xFF0071F8),
                            unfocusedBorderColor = Color(0xFFE0E0E0)
                        )
                    )

                    loginViewModel.error?.let { error ->

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = { loginViewModel.login() },
                        enabled = !loginViewModel.loading &&
                                loginViewModel.email.isNotBlank() &&
                                loginViewModel.password.isNotBlank(),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEE626B)
                        )
                    ) {

                        if (loginViewModel.loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Sign In")
                        }
                    }
                }
            }
        }
    }
}