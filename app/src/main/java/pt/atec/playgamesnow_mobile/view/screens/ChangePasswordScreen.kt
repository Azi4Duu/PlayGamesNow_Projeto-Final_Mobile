package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.atec.playgamesnow_mobile.view.components.CheckoutCard
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.view.components.RoundedTextField
import pt.atec.playgamesnow_mobile.viewmodel.profile.ChangePasswordViewModel

@Composable
fun ChangePasswordScreen(
    onPasswordUpdated: () -> Unit,
    changePasswordViewModel: ChangePasswordViewModel = viewModel()
) {

    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Change Password",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            CheckoutCard {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFF0071F8),
                        modifier = Modifier.size(64.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Update Your Password",
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Enter your current password and choose a new one.",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = changePasswordViewModel.currentPassword,
                    onValueChange = {
                        changePasswordViewModel.updateCurrentPassword(it)
                    },
                    label = { Text("Current Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(30.dp),
                    visualTransformation = if (currentPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                currentPasswordVisible = !currentPasswordVisible
                            }
                        ) {
                            Icon(
                                imageVector = if (currentPasswordVisible) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (currentPasswordVisible) {
                                    "Hide current password"
                                } else {
                                    "Show current password"
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

                Spacer(Modifier.height(14.dp))

                OutlinedTextField(
                    value = changePasswordViewModel.newPassword,
                    onValueChange = {
                        changePasswordViewModel.updateNewPassword(it)
                    },
                    label = { Text("New Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(30.dp),
                    visualTransformation = if (newPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                newPasswordVisible = !newPasswordVisible
                            }
                        ) {
                            Icon(
                                imageVector = if (newPasswordVisible) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (newPasswordVisible) {
                                    "Hide new password"
                                } else {
                                    "Show new password"
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

                Spacer(Modifier.height(14.dp))

                OutlinedTextField(
                    value = changePasswordViewModel.confirmPassword,
                    onValueChange = {
                        changePasswordViewModel.updateConfirmPassword(it)
                    },
                    label = { Text("Confirm New Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(30.dp),
                    visualTransformation = if (confirmPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                confirmPasswordVisible = !confirmPasswordVisible
                            }
                        ) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) {
                                    Icons.Default.VisibilityOff
                                } else {
                                    Icons.Default.Visibility
                                },
                                contentDescription = if (confirmPasswordVisible) {
                                    "Hide password confirmation"
                                } else {
                                    "Show password confirmation"
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

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Your new password must contain at least 8 characters.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                changePasswordViewModel.error?.let { error ->

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                    )
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {
                        changePasswordViewModel.changePassword()
                    },
                    enabled = !changePasswordViewModel.loading &&
                            changePasswordViewModel.currentPassword.isNotBlank() &&
                            changePasswordViewModel.newPassword.isNotBlank() &&
                            changePasswordViewModel.confirmPassword.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEE626B)
                    )
                ) {

                    if (changePasswordViewModel.loading) {

                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )

                    } else {

                        Text("Update Password")
                    }
                }

                if (changePasswordViewModel.updated) {

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32)
                        )

                        Text(
                            text = "Password updated successfully!",
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}