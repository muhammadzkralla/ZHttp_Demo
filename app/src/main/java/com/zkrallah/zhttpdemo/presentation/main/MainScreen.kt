package com.zkrallah.zhttpdemo.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.zkrallah.zhttpdemo.domain.model.ShopItem
import com.zkrallah.zhttpdemo.presentation.dialogs.ItemDialog
import com.zkrallah.zhttpdemo.presentation.dialogs.PatchDialog
import com.zkrallah.zhttpdemo.presentation.dialogs.PostDialog
import com.zkrallah.zhttpdemo.presentation.dialogs.PutDialog

@Composable
fun MainScreen(items: List<ShopItem>?, mainViewModel: MainViewModel) {
    var currentItemDialog by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    var currentItemDialogState by remember { mutableStateOf(false) }
    var currentPostDialog by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    var currentPostDialogState by remember { mutableStateOf(false) }
    var currentPutDialog by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    var currentPutDialogState by remember { mutableStateOf(false) }
    var currentPatchDialog by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    var currentPatchDialogState by remember { mutableStateOf(false) }


    ItemList(items = items) { item ->
        currentItemDialogState = true
        currentItemDialog = {
            ItemDialog(item, onDismiss = { currentItemDialogState = false }, {
                currentPutDialogState = true
                currentPutDialog = {
                    PutDialog(
                        item = item,
                        onDismiss = { currentPutDialogState = false },
                        mainViewModel = mainViewModel
                    )
                }
            }, {
                currentPatchDialogState = true
                currentPatchDialog = {
                    PatchDialog(
                        item = item,
                        onDismiss = { currentPatchDialogState = false },
                        mainViewModel = mainViewModel
                    )
                }
            }, mainViewModel
            )
        }
    }
    Fab {
        currentPostDialogState = true
        currentPostDialog = {
            PostDialog(
                onDismiss = { currentPostDialogState = false }, mainViewModel = mainViewModel
            )
        }
    }
    if (currentItemDialogState) DrawComposable(currentItemDialog)
    if (currentPostDialogState) DrawComposable(currentPostDialog)
    if (currentPutDialogState) DrawComposable(currentPutDialog)
    if (currentPatchDialogState) DrawComposable(currentPatchDialog)
}

@Composable
fun ItemCard(shopItem: ShopItem, onClick: (item: ShopItem) -> Unit) {
    // Use the provided item information to create a card
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .clickable {
            onClick(shopItem)
        }) {
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
    val painter = rememberImagePainter(data = data, builder = {
        // You can apply transformations here if needed
    })
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
fun DrawComposable(
    anyComposable: (@Composable () -> Unit)? = null
) {
    anyComposable?.let {
        anyComposable()
    }
}

@Composable
fun Fab(onFabClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        FloatingActionButton(
            onClick = {
                onFabClicked()
            }, modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            // Custom image for the FAB
            Icon(imageVector = Icons.Rounded.Add, contentDescription = "Cancel")
        }
    }
}