package pt.atec.playgamesnow_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pt.atec.playgamesnow_mobile.ui.theme.PlayGamesNow_mobileTheme
import pt.atec.playgamesnow_mobile.view.navigation.AppNavigation
import pt.atec.playgamesnow_mobile.view.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayGamesNow_mobileTheme() {
                AppNavigation()
            }
        }
    }
}