package com.example.spotiflow.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.spotiflow.R


@Composable
fun UserProfileIcon(
    modifier: Modifier = Modifier,
    prifileImage: String?,
    onNavigateToProfile: () -> Unit
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onNavigateToProfile
        ) {
            if (!prifileImage.isNullOrBlank()) {
                AsyncImage(
                    model = prifileImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape.copy(CornerSize(100.dp)))
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.generic_avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape.copy(CornerSize(100.dp)))
                )
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun UserProfileIconPreview() {
    UserProfileIcon(
        prifileImage = null,
        onNavigateToProfile = {}
    )
}