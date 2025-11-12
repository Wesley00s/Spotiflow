package com.example.spotiflow.ui.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    enabled: Boolean = true,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = errorMessage ?: "")
            }
        },
        singleLine = singleLine,
        maxLines = maxLines,
        enabled = enabled,
        shape = RoundedCornerShape(10.dp)
    )
}

@Preview
@Composable
fun CustomInputFieldPreview() {
    CustomInputField(
        value = "",
        onValueChange = {},
        label = "Email",
        placeholder = "Enter your name",
        leadingIcon = {
            Text(
                text = "Email",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.6f
                )
            )
        },
        isError = false,
        errorMessage = null,
    )
}