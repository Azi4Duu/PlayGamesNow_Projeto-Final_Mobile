package pt.atec.playgamesnow_mobile.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.atec.playgamesnow_mobile.R

@Composable
fun HomeHeader() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .shadow(elevation = 10.dp, shape = RoundedCornerShape(bottomStart = 35.dp,
            bottomEnd = 35.dp))
        .clip(RoundedCornerShape(
            bottomStart = 35.dp,
            bottomEnd = 35.dp
        ))
        .height(80.dp),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.pageheading),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "PlayGamesNow",
            modifier = Modifier.height(48.dp),
            contentScale = ContentScale.Fit
        )
    }
}