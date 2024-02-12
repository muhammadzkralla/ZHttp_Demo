package com.zkrallah.zhttpdemo.presentation.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zkrallah.zhttpdemo.domain.model.ShopItem
import com.zkrallah.zhttpdemo.presentation.main.MainViewModel

@Composable
fun ItemDialog(
    item: ShopItem,
    onDismiss: () -> Unit,
    onPutButtonClicked: () -> Unit,
    onPatchButtonClicked: () -> Unit,
    mainViewModel: MainViewModel
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
                    Text(text = "Item")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "This is a custom dialog for item: ${item.title}")
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            mainViewModel.deleteProductSync(item.id ?: 0)
                            onDismiss()
                        }) {
                            Text(text = "DELETE")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            onPutButtonClicked()
                            onDismiss()
                        }) {
                            Text(text = "PUT")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            onPatchButtonClicked()
                            onDismiss()
                        }) {
                            Text(text = "PATCH")
                        }
                    }
                }
            }
        }
    }
}
