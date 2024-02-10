package com.zkrallah.zhttpdemo.presentation.main

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zkrallah.zhttpdemo.domain.model.Item
import java.text.SimpleDateFormat
import java.util.Locale

private const val TAG = "MainScreens"

@Composable
fun NetworkSettingsForm() {
    var baseUrl by remember { mutableStateOf("") }
    var connectionTimeout by remember { mutableStateOf("") }
    var readTimeout by remember { mutableStateOf("") }
    var defaultHeaders by remember { mutableStateOf("") }
    var bufferSize by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = baseUrl,
            onValueChange = { baseUrl = it },
            label = { Text("Base URL") },
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
            value = connectionTimeout,
            onValueChange = { connectionTimeout = it },
            label = { Text("Connection Timeout (ms)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
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
            value = readTimeout,
            onValueChange = { readTimeout = it },
            label = { Text("Read Timeout (ms)") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
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
            value = defaultHeaders,
            onValueChange = { defaultHeaders = it },
            label = { Text("Default Headers (comma separated)") },
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
            value = bufferSize,
            onValueChange = { bufferSize = it },
            label = { Text("Buffer Size") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
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
        Button(
            onClick = {
                val headersList = defaultHeaders.split(",").map {
                    val keyValue = it.split("=")
                    Pair(keyValue[0], keyValue[1])
                }
            },
            modifier = Modifier.align(alignment = androidx.compose.ui.Alignment.CenterHorizontally)
        ) {
            Text(text = "Build")
        }
    }
}

@Preview("NetworkSettingsFormPreview")
@Composable
fun NetworkSettingsFormPreview() {
    NetworkSettingsForm()
}

@Composable
fun MainScreen(items: List<Item>?) {
    var dynamicComposable by remember { mutableStateOf<(@Composable () -> Unit)?> (null) }
    var isDialogShown by remember { mutableStateOf(false) }

    Items(items = items) { item ->
        isDialogShown = true
        dynamicComposable = {
            CustomDialog(
                title = "Title",
                message = "This is a custom dialog for item: ${item.name}",
                onDismiss = { isDialogShown = false },
                confirmText = "OK",
                cancelText = "Cancel"
            )
        }
    }
    if (isDialogShown) AnyComposable(dynamicComposable)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(item: Item, onClick: (item: Item) -> Unit) {
    Box {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            onClick = {
                onClick(item)
            }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "ID: ${item.id ?: "N/A"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Name: ${item.name ?: "N/A"}")
                Spacer(modifier = Modifier.height(8.dp))
                item.data?.let { data ->
                    Text(
                        text = "Data: ${data.entries.joinToString(", ") { "${it.key}: ${it.value}" }}"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Text(
                    text = "Created At: ${formatDate(item.createdAt)}"
                )
            }
        }

    }
}

@Composable
fun formatDate(dateString: String?): String {
    return dateString?.let {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = sdf.parse(it)
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        date?.let {
            outputFormat.format(date)
        } ?: ""
    } ?: "N/A"
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Items(items: List<Item>?, onClick: (item: Item) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        stickyHeader {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Items")
            }
        }
        items?.let {
            itemsIndexed(items) { _, item ->
                ItemCard(item, onClick)
            }
        }
    }
}

@Composable
fun AnyComposable(
    anyComposable: (@Composable () -> Unit)? = null
) {
    anyComposable?.let {
        anyComposable()
    }
}