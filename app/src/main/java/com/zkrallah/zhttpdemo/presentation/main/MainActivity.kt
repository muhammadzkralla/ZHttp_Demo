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

        login()
        fetchProducts()
        setObservers()
    }

    private fun login() = mainViewModel.fetchProducts()

    private fun fetchProducts() = mainViewModel.login("mor_2314", "83r5^_")

    private fun setObservers() {
        loginObserver()
        fetchObserver()
        deleteObserver()
        updateOrAddObserver()
        updateObserver()
    }

    private fun updateObserver() {
        lifecycleScope.launch {
            mainViewModel.patchedProduct.collectLatest { product ->
                product?.let {
                    Toast.makeText(
                        this@MainActivity,
                        "Patched item of id: ${product.id}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun updateOrAddObserver() {
        lifecycleScope.launch {
            mainViewModel.puttedProduct.collectLatest { product ->
                product?.let {
                    Toast.makeText(
                        this@MainActivity,
                        "Updated item of id: ${product.id}",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }
    }

    private fun loginObserver() {
        lifecycleScope.launch {
            mainViewModel.auth.collectLatest { authResponse ->
                authResponse?.let {
                    Toast.makeText(this@MainActivity, authResponse.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
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
}