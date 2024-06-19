package com.zkrallah.zhttpdemo.presentation.dialogs

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.zkrallah.zhttp.model.MultipartBody
import com.zkrallah.zhttpdemo.domain.model.ShopItem
import com.zkrallah.zhttpdemo.presentation.main.MainViewModel
import com.zkrallah.zhttpdemo.util.cacheImageToFile
import com.zkrallah.zhttpdemo.util.getImageFileFromRealPath

@Composable
fun PostDialog(
    onDismiss: () -> Unit,
    mainViewModel: MainViewModel
) {
    var titleState by remember { mutableStateOf("") }
    var priceState by remember { mutableStateOf("") }
    var descriptionState by remember { mutableStateOf("") }
    var categoryState by remember { mutableStateOf("") }
    val context = LocalContext.current
    val getContent =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                val path = cacheImageToFile(context, uri)
                val file = getImageFileFromRealPath(path)
                val imageMultipartBody = MultipartBody(
                    name = "image",
                    filePath = file.toString(),
                    contentType = "image/*"
                )
                val parts = listOf(imageMultipartBody)
                mainViewModel.uploadImagePart(parts)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
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

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = titleState,
                        onValueChange = { titleState = it },
                        label = { Text("Title...") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Gray,
                            focusedIndicatorColor = Color.Red,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = priceState,
                        onValueChange = { priceState = it },
                        label = { Text("Price...") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Decimal
                        ),
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Gray,
                            focusedIndicatorColor = Color.Red,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = descriptionState,
                        onValueChange = { descriptionState = it },
                        label = { Text("Description...") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Gray,
                            focusedIndicatorColor = Color.Red,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = categoryState,
                        onValueChange = { categoryState = it },
                        label = { Text("Category...") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        ),
                        colors = TextFieldDefaults.colors(
                            disabledTextColor = Color.Gray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Gray,
                            focusedIndicatorColor = Color.Red,
                            unfocusedIndicatorColor = Color.Black,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            val item = ShopItem(
                                title = titleState,
                                price = priceState,
                                description = descriptionState,
                                category = categoryState
                            )
                            mainViewModel.addProduct(item)
                            onDismiss()
                        }) {
                            Text(text = "PUSH")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            onDismiss()
                        }) {
                            Text(text = "CANCEL")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    FloatingActionButton(
                        onClick = { getContent.launch("image/*") },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                    ) {
                        // Custom image for the FAB
                        Icon(imageVector = Icons.Rounded.AccountBox, contentDescription = "Img")
                    }
                }
            }
        }
    }
}
