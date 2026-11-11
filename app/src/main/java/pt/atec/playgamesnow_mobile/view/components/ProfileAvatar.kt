package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileAvatar(
    name: String
) {

    val initials = name
        .trim()
        .split(Regex("\\s+"))
        .filter { it.isNotBlank() }
        .let { parts->
            when(parts.size){
                0 -> "?"
                1 -> parts.first().first().uppercaseChar().toString()
                else -> "${parts.first().first().uppercaseChar()}${parts.last().first().uppercaseChar()}"
            }
        }

    Box(
        modifier = Modifier
            .size(64.dp)
            .background(
                color = Color(0xFF0071F8),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = initials,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}