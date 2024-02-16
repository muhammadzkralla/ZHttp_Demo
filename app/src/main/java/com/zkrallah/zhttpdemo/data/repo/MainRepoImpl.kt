package com.zkrallah.zhttpdemo.data.repo

import android.util.Log
import com.google.gson.JsonObject
import com.zkrallah.zhttp.Header
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.ZHttpClient
import com.zkrallah.zhttp.ZListener
import com.zkrallah.zhttpdemo.di.ImgurClient
import com.zkrallah.zhttpdemo.di.StoreClient
import com.zkrallah.zhttpdemo.domain.model.AuthResponse
import com.zkrallah.zhttpdemo.domain.model.NewTitle
import com.zkrallah.zhttpdemo.domain.model.ShopItem
import com.zkrallah.zhttpdemo.domain.model.User
import com.zkrallah.zhttpdemo.domain.repo.MainRepo
import com.zkrallah.zhttpdemo.util.Imgur
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    @StoreClient private val storeClient: ZHttpClient,
    @ImgurClient private val imgurClient: ZHttpClient
) : MainRepo {
    override fun login(userName: String, password: String, callback: ZListener<AuthResponse>) {
        val body = User(userName, password)
        val request = storeClient.post("", body, null, null, callback)
    }

    override fun fetchProducts(callback: ZListener<List<ShopItem>>) {
        val request = storeClient.get("products", null, null, callback)
    }

    override fun addProduct(product: ShopItem, callback: ZListener<ShopItem>) {
        val request = storeClient.post("products", product, null, null, callback)
    }

    override fun deleteProduct(id: Int, callback: ZListener<ShopItem>) {
        val request = storeClient.delete("products/$id", null, null, callback)
    }

    override fun updateOrAdd(id: Int, product: ShopItem, callback: ZListener<ShopItem>) {
        val request = storeClient.put("products/$id", product, null, null, callback)
    }

    override fun update(id: Int, parameter: JsonObject, callback: ZListener<ShopItem>) {
        val request = storeClient.patch("products/$id", parameter, null, null, callback)
    }

    override fun update(id: Int, parameter: NewTitle, callback: ZListener<ShopItem>) {
        val request = storeClient.patch("products/$id", parameter, null, null, callback)
    }

    override fun uploadImagePart(parts: List<MultipartBody>, callback: ZListener<String>) {
        val headers = listOf(
            Header("Authorization", Imgur.TOKEN)
        )
        val request = imgurClient.multiPart("3/image", parts, null, headers, callback)
    }

    companion object {
        private const val TAG = "MainRepoImpl"
    }
}