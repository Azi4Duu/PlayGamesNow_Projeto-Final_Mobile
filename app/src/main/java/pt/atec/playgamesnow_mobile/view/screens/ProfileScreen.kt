package pt.atec.playgamesnow_mobile.view.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.atec.playgamesnow_mobile.view.components.CheckoutCard
import pt.atec.playgamesnow_mobile.view.components.HomeHeader
import pt.atec.playgamesnow_mobile.view.components.LoadingIndicator
import pt.atec.playgamesnow_mobile.view.components.ProfileAvatar
import pt.atec.playgamesnow_mobile.view.components.ProfileOption
import pt.atec.playgamesnow_mobile.viewmodel.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    onPurchasesClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onLogoutClick: () -> Unit,
    profileViewModel: ProfileViewModel = viewModel()
) {

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeHeader()

        when {
            profileViewModel.loading -> {
                LoadingIndicator(message = "Loading profile...")
            }

            profileViewModel.error != null -> {
                Text(
                    text = "Error: ${profileViewModel.error}",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }

            profileViewModel.profile != null -> {

                val profile = profileViewModel.profile!!

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    CheckoutCard {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            ProfileAvatar(name = profile.name)

                            Column {
                                Text(
                                    text = "Account Details",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                                Spacer(Modifier.height(8.dp))

                                Text(text = profile.name)

                                Text(
                                    text = profile.email,
                                    color = Color.Gray,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }

                    CheckoutCard {
                        ProfileOption(
                            title = "Edit Profile",
                            subtitle = "Update your personal information",
                            icon = Icons.Default.Edit,
                            onClick = onEditProfileClick
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        ProfileOption(
                            title = "My Purchases",
                            subtitle = "View your orders and game keys",
                            icon = Icons.Default.ShoppingBag,
                            onClick = onPurchasesClick
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        ProfileOption(
                            title = "Change Password",
                            subtitle = "Update your account password",
                            icon = Icons.Default.Lock,
                            onClick = onChangePasswordClick
                        )
                    }

                    CheckoutCard {
                        ProfileOption(
                            title = "Log Out",
                            subtitle = "Sign out of your account",
                            icon = Icons.Default.ExitToApp,
                            onClick = onLogoutClick,
                            color = Color(0xFFEE626B)
                        )
                    }
                }
            }
        }
    }
}