package com.zkrallah.zhttpdemo.presentation.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.zkrallah.zhttpdemo.domain.model.ShopItem
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
fun MainScreen(items: List<ShopItem>?, mainViewModel: MainViewModel) {
    var dynamicComposable by remember { mutableStateOf<(@Composable () -> Unit)?> (null) }
    var isDialogShown by remember { mutableStateOf(false) }

    ItemList(items = items) { item ->
        isDialogShown = true
        dynamicComposable = {
            CustomDialog(
                item,
                onDismiss = { isDialogShown = false },
                mainViewModel
            )
        }
    }
    if (isDialogShown) AnyComposable(dynamicComposable)
}

@Composable
fun ItemCard(shopItem: ShopItem, onClick: (item: ShopItem) -> Unit) {
    // Use the provided item information to create a card
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp).clickable {
                onClick(shopItem)
            }
    ) {
        // Display the image using the provided resource ID
        CoilImage(
            data = shopItem.image.orEmpty(),
            contentDescription = null, // Provide content description as per your needs
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display other details of the item
        Text(text = shopItem.title.orEmpty(), fontWeight = FontWeight.Bold)
        Text(text = "Price: ${shopItem.price.orEmpty()}")
        Text(text = "Category: ${shopItem.category.orEmpty()}")
        Text(text = "Description: ${shopItem.description.orEmpty()}")
    }
}

@Composable
fun ItemList(items: List<ShopItem>?, onClick: (shopItem: ShopItem) -> Unit) {
    // Create a LazyColumn with ItemCard for each item in the list
    LazyColumn {
        items?.let {
            itemsIndexed(items) { _, item ->
                ItemCard(item, onClick)
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CoilImage(
    data: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    // Use Coil's rememberImagePainter to load and display the image
    val painter = rememberImagePainter(
        data = data,
        builder = {
            // You can apply transformations here if needed
        }
    )
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
fun AnyComposable(
    anyComposable: (@Composable () -> Unit)? = null
) {
    anyComposable?.let {
        anyComposable()
    }
}