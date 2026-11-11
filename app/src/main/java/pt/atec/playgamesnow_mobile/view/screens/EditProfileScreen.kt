package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.atec.playgamesnow_mobile.view.components.CheckoutCard
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.view.components.LoadingIndicator
import pt.atec.playgamesnow_mobile.view.components.ProfileAvatar
import pt.atec.playgamesnow_mobile.view.components.RoundedTextField
import pt.atec.playgamesnow_mobile.viewmodel.profile.EditProfileViewModel

@Composable
fun EditProfileScreen(
    onProfileUpdated: () -> Unit,
    editProfileViewModel: EditProfileViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        editProfileViewModel.loadProfile()
    }

    LaunchedEffect(editProfileViewModel.updated) {
        if (editProfileViewModel.updated) {
            onProfileUpdated()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        when {

            editProfileViewModel.loading -> {
                LoadingIndicator(message = "Loading profile...")
            }

            else -> {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .imePadding()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = "Edit Profile",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    CheckoutCard {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            ProfileAvatar(
                                name = editProfileViewModel.name
                            )

                            Spacer(Modifier.height(12.dp))

                            Text(
                                text = "Personal Information",
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(
                                text = "Update your account details",
                                color = Color.Gray,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(Modifier.height(24.dp))

                        RoundedTextField(
                            value = editProfileViewModel.name,
                            onValueChange = {
                                editProfileViewModel.updateName(it)
                            },
                            label = "Name",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(14.dp))

                        RoundedTextField(
                            value = editProfileViewModel.username,
                            onValueChange = {
                                editProfileViewModel.updateUsername(it)
                            },
                            label = "Username",
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(14.dp))

                        RoundedTextField(
                            value = editProfileViewModel.email,
                            onValueChange = {
                                editProfileViewModel.updateEmail(it)
                            },
                            label = "Email",
                            modifier = Modifier.fillMaxWidth(),
                            keyboardType = KeyboardType.Email
                        )

                        editProfileViewModel.error?.let { error ->

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
                                editProfileViewModel.saveProfile()
                            },
                            enabled = !editProfileViewModel.saving &&
                                    editProfileViewModel.name.isNotBlank() &&
                                    editProfileViewModel.username.isNotBlank() &&
                                    editProfileViewModel.email.isNotBlank(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEE626B)
                            )
                        ) {

                            if (editProfileViewModel.saving) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp,
                                    color = Color.White
                                )
                            } else {
                                Text("Save Changes")
                            }
                        }
                    }
                }
            }
        }
    }
}