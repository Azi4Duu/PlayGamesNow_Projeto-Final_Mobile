package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(search: String, onSearchChange: (String) -> Unit, onFilterClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 9.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedTextField(
            value = search,
            onValueChange = onSearchChange,
            placeholder = "Search games...",
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(30.dp)
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            onClick = {onFilterClick()},
            modifier = Modifier
                .size(45.dp)
                .shadow(elevation = 3.dp, CircleShape)
                .background(
                    color = Color(0xFFEE626B),
                    shape = CircleShape
                )
        ) {
            Icon(imageVector = Icons.Default.FilterList,
                contentDescription = "Filtros",
                tint = Color.White,
                modifier = Modifier.size(24.dp))
        }
    }
}