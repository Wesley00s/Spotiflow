package com.example.spotiflow.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotiflow.R

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary,
    text: String,
    icon: Int? = null,
    isLoading: Boolean = false,
) {
    Button(
        modifier = modifier,
        shape = MaterialTheme.shapes.extraLarge,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                .padding(all = if (icon != null) 8.dp else 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if(isLoading) {
                CircularProgressIndicator()
            } else {
                if (icon != null) {
                    Image(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(id = icon), contentDescription = "Icon button"
                    )
                    Text(text = text, modifier = Modifier.padding(start = 8.dp))
                }

            }
        }
    }
}

@Preview
@Composable
fun CustomButtonPreview() {
    CustomButton(
        onClick = {},
        text = "Custom Button",
        modifier = Modifier,
        icon = R.drawable.ic_spotify,
        isLoading = false,
    )
}