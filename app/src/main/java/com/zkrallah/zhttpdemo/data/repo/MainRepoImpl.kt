package com.zkrallah.zhttpdemo.data.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.ZHttpClient
import com.zkrallah.zhttp.ZListener
import com.zkrallah.zhttpdemo.di.AuthClient
import com.zkrallah.zhttpdemo.di.ImgurClient
import com.zkrallah.zhttpdemo.di.StoreClient
import com.zkrallah.zhttpdemo.domain.model.AuthResponse
import com.zkrallah.zhttpdemo.domain.model.DeleteResponse
import com.zkrallah.zhttpdemo.domain.model.Item
import com.zkrallah.zhttpdemo.domain.repo.MainRepo
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    @StoreClient private val storeClient: ZHttpClient,
    @ImgurClient private val imgurClient: ZHttpClient,
    @AuthClient private val authClient: ZHttpClient
) : MainRepo{
    override fun login(userName: String, password: String, callback: ZListener<AuthResponse>) {
        val body = JsonObject().apply {
            addProperty("username", userName)
            addProperty("password", password)
        }

        val request = authClient.post("auth/login", body, null, null, callback)
    }

    override fun fetchProducts(callback: ZListener<List<Item>>) {
        val request = storeClient.get("objects", null, null, callback)
    }

    override fun addProduct(product: Item, callback: ZListener<Item>) {
        val request = storeClient.post("objects", product, null, null, callback)
    }

    override fun deleteProduct(id: Int, callback: ZListener<DeleteResponse>) {
        val request = storeClient.delete("objects/$id", null, null, callback)
    }

    override fun updateOrAdd(id: Int, product: Item, callback: ZListener<Item>) {
        val request = storeClient.put("objects/$id", product, null, null, callback)
    }

    override fun update(id: Int, parameter: JsonObject, callback: ZListener<Item>) {
        val request = storeClient.patch("objects/$id", parameter, null, null, callback)
    }

    override fun uploadImagePart(parts: List<MultipartBody>, callback: ZListener<String>) {
        val request = imgurClient.multiPart("3/image", parts, null, null, callback)
    }

    companion object {
        private const val TAG = "MainRepoImpl"
    }
}