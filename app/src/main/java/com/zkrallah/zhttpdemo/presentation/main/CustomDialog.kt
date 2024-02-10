package com.zkrallah.zhttpdemo.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit,
    confirmText: String = "Confirm",
    cancelText: String = "Cancel"
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = title)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = message)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = onDismiss) {
                            Text(text = cancelText)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = onDismiss) {
                            Text(text = confirmText)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialog() {
    CustomDialog(
        title = "Title",
        message = "This is a custom dialog for item: ",
        onDismiss = {  },
        confirmText = "OK",
        cancelText = "Cancel"
    )
}
