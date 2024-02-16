package com.zkrallah.zhttpdemo.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.Response
import com.zkrallah.zhttp.ZListener
import com.zkrallah.zhttpdemo.domain.model.AuthResponse
import com.zkrallah.zhttpdemo.domain.model.NewTitle
import com.zkrallah.zhttpdemo.domain.model.ShopItem
import com.zkrallah.zhttpdemo.domain.repo.MainRepo
import com.zkrallah.zhttpdemo.domain.repo.MainRepoCoroutine
import com.zkrallah.zhttpdemo.domain.repo.MainRepoSync
import com.zkrallah.zhttpdemo.util.Helper
import com.zkrallah.zhttpdemo.util.Helper.fromJson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepo: MainRepo,
    private val mainRepoSync: MainRepoSync,
    private val mainRepoCoroutine: MainRepoCoroutine
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

    private val gson = Gson()

    fun loginCoroutine(userName: String, password: String) {
        viewModelScope.launch {
            val response = mainRepoCoroutine.login(userName, password)
            Log.d(TAG, "loginCoroutine: $response")
            _auth.emit(response?.body)
        }
    }

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

    fun loginSync(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepoSync.login(userName, password)?.let { response ->
                // The synchronized functions don't deserialize the response
                val authResponse = gson.fromJson(response.body, AuthResponse::class.java)
                _auth.emit(authResponse)
            }
        }
    }

    fun fetchProductsCoroutine() {
        viewModelScope.launch {
            val response = mainRepoCoroutine.fetchProducts()
            Log.d(TAG, "fetchProductsCoroutine: $response")
            _products.emit(response?.body)
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

    fun fetchProductsSync() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepoSync.fetchProducts()?.let { response ->
                // Gson can't deserialize lists without type tokens so,
                // I made an extension function in the Helper class
                val products: List<ShopItem>? = gson.fromJson(response.body)
                _products.emit(products)
            }
        }
    }

    fun addProductCoroutine(product: ShopItem) {
        viewModelScope.launch {
            val response = mainRepoCoroutine.addProduct(product)
            Log.d(TAG, "addProductCoroutine: $response")
            _addedProduct.emit(response?.body)
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

    fun addProductSync(product: ShopItem) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepoSync.addProduct(product)?.let { response ->
                val item = gson.fromJson(response.body, ShopItem::class.java)
                _addedProduct.emit(item)
            }
        }
    }

    fun deleteProductCoroutine(id: Int) {
        viewModelScope.launch {
            val response = mainRepoCoroutine.deleteProduct(id)
            Log.d(TAG, "deleteProductCoroutine: $response")
            _deletedProduct.emit(response?.body)
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

    fun deleteProductSync(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepoSync.deleteProduct(id)?.let { response ->
                val item = gson.fromJson(response.body, ShopItem::class.java)
                _deletedProduct.emit(item)
            }
        }
    }

    fun updateOrAddCoroutine(id: Int, product: ShopItem) {
        viewModelScope.launch {
            val response = mainRepoCoroutine.updateOrAdd(id, product)
            Log.d(TAG, "updateOrAddCoroutine: $response")
            _puttedProduct.emit(response?.body)
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

    fun updateOrAddSync(id: Int, product: ShopItem) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepoSync.updateOrAdd(id, product)?.let { response ->
                val item = gson.fromJson(response.body, ShopItem::class.java)
                _puttedProduct.emit(item)
            }
        }
    }

    fun updateCoroutine(id: Int, parameter: JsonObject) {
        viewModelScope.launch {
            val response = mainRepoCoroutine.update(id, parameter)
            Log.d(TAG, "updateCoroutine: $response")
            _patchedProduct.emit(response?.body)
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

    fun updateSync(id: Int, parameter: JsonObject) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepoSync.update(id, parameter)?.let { response ->
                val item = gson.fromJson(response.body, ShopItem::class.java)
                _patchedProduct.emit(item)
            }
        }
    }

    fun updateCoroutine(id: Int, parameter: NewTitle) {
        viewModelScope.launch {
            val response = mainRepoCoroutine.update(id, parameter)
            Log.d(TAG, "updateCoroutine: $response")
            _patchedProduct.emit(response?.body)
        }
    }

    fun update(id: Int, newTitle: NewTitle) {
        viewModelScope.launch {
            mainRepo.update(id, newTitle, object : ZListener<ShopItem> {
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

    fun updateSync(id: Int, parameter: NewTitle) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepoSync.update(id, parameter)?.let { response ->
                val item = gson.fromJson(response.body, ShopItem::class.java)
                _patchedProduct.emit(item)
            }
        }
    }

    fun uploadImagePartCoroutine(parts: List<MultipartBody>) {
        viewModelScope.launch {
            val response = mainRepoCoroutine.uploadImagePart(parts)
            Log.d(TAG, "uploadImagePartCoroutine: $response")
            _uploadMessage.emit(response?.body)
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

    fun uploadImagePartSync(parts: List<MultipartBody>) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepoSync.uploadImagePart(parts)?.let { response ->
                // Response body is already a string, no need to deserialize
                _uploadMessage.emit(response.body)
            }
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}