package com.zkrallah.zhttpdemo.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import com.zkrallah.zhttpdemo.domain.model.ShopItem
import com.zkrallah.zhttpdemo.ui.theme.ZHttpDemoTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val itemsState = mutableStateOf<List<ShopItem>?>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZHttpDemoTheme {
                MainScreen(itemsState.value, mainViewModel)
            }
        }

        mainViewModel.fetchProducts()
        setObservers()
    }

    private fun setObservers() {
        fetchObserver()
        deleteObserver()
    }

    private fun deleteObserver() {
        lifecycleScope.launch {
            mainViewModel.deletedProduct.collectLatest { deleteResponse ->
                deleteResponse?.let {
                    Toast.makeText(this@MainActivity, deleteResponse.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun fetchObserver() {
        lifecycleScope.launch {
            mainViewModel.products.collectLatest { products ->
                products?.let {
                    itemsState.value = it
                }
            }
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}