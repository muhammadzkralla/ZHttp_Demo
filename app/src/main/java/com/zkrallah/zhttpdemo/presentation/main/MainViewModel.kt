package com.zkrallah.zhttpdemo.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.Response
import com.zkrallah.zhttp.ZListener
import com.zkrallah.zhttpdemo.domain.model.AuthResponse
import com.zkrallah.zhttpdemo.domain.model.ShopItem
import com.zkrallah.zhttpdemo.domain.repo.MainRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepo: MainRepo
) : ViewModel() {
    private val _auth: MutableStateFlow<AuthResponse?> = MutableStateFlow(null)
    val auth = _auth.asStateFlow()
    private val _products: MutableStateFlow<List<ShopItem>?> = MutableStateFlow(null)
    val products = _products.asStateFlow()
    private val _addedProduct: MutableStateFlow<ShopItem?> = MutableStateFlow(null)
    val addedProduct = _addedProduct.asStateFlow()
    private val _deletedProduct: MutableStateFlow<ShopItem?> = MutableStateFlow(null)
    val deletedProduct = _deletedProduct.asStateFlow()
    private val _puttedProduct: MutableStateFlow<ShopItem?> = MutableStateFlow(null)
    val puttedProduct = _puttedProduct.asStateFlow()
    private val _patchedProduct: MutableStateFlow<ShopItem?> = MutableStateFlow(null)
    val patchedProduct = _patchedProduct.asStateFlow()
    private val _uploadMessage: MutableStateFlow<String?> = MutableStateFlow(null)
    val uploadMessage = _uploadMessage.asStateFlow()

    fun login(userName: String, password: String) {
        viewModelScope.launch {
            mainRepo.login(userName, password, object : ZListener<AuthResponse> {
                override fun onSuccess(response: Response<AuthResponse>?) {
                    Log.d(TAG, "onSuccess: $response")
                    _auth.value = response?.body
                }

                override fun onFailure(error: Exception) {
                    Log.e(TAG, "onFailure: $error", error)
                }

            })
        }
    }

    fun fetchProducts() {
        viewModelScope.launch {
            mainRepo.fetchProducts(object : ZListener<List<ShopItem>> {
                override fun onSuccess(response: Response<List<ShopItem>>?) {
                    Log.d(TAG, "onSuccess: $response")
                    _products.value = response?.body
                }

                override fun onFailure(error: Exception) {
                    Log.e(TAG, "onFailure: $error", error)
                }

            })
        }
    }

    fun addProduct(product: ShopItem) {
        viewModelScope.launch {
            mainRepo.addProduct(product, object : ZListener<ShopItem> {
                override fun onSuccess(response: Response<ShopItem>?) {
                    Log.d(TAG, "onSuccess: $response")
                    _addedProduct.value = response?.body
                }

                override fun onFailure(error: Exception) {
                    Log.e(TAG, "onFailure: $error", error)
                }
            })
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            mainRepo.deleteProduct(id, object : ZListener<ShopItem> {
                override fun onSuccess(response: Response<ShopItem>?) {
                    _deletedProduct.value = response?.body
                }

                override fun onFailure(error: Exception) {
                    Log.e(TAG, "onFailure: $error", error)
                }
            })
        }
    }

    fun updateOrAdd(id: Int, product: ShopItem) {
        viewModelScope.launch {
            mainRepo.updateOrAdd(id, product, object : ZListener<ShopItem> {
                override fun onSuccess(response: Response<ShopItem>?) {
                    Log.d(TAG, "onSuccess: $response")
                    _puttedProduct.value = response?.body
                }

                override fun onFailure(error: Exception) {
                    Log.e(TAG, "onFailure: $error", error)
                }
            })
        }
    }

    fun update(id: Int, parameter: JsonObject) {
        viewModelScope.launch {
            mainRepo.update(id, parameter, object : ZListener<ShopItem> {
                override fun onSuccess(response: Response<ShopItem>?) {
                    Log.d(TAG, "onSuccess: $response")
                    _patchedProduct.value = response?.body
                }

                override fun onFailure(error: Exception) {
                    Log.e(TAG, "onFailure: $error", error)
                }
            })
        }
    }

    fun uploadImagePart(parts: List<MultipartBody>) {
        viewModelScope.launch {
            mainRepo.uploadImagePart(parts, object : ZListener<String> {
                override fun onSuccess(response: Response<String>?) {
                    Log.d(TAG, "onSuccess: $response")
                    _uploadMessage.value = response?.body
                }

                override fun onFailure(error: Exception) {
                    Log.e(TAG, "onFailure: $error", error)
                }
            })
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}