package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
    message: String = "Loading..."
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CircularProgressIndicator(
            color = Color(0xFF0071F8),
            strokeWidth = 6.dp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = message,
            color = Color.Gray
        )
    }
}