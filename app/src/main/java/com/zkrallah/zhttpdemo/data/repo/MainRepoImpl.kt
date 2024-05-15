package com.zkrallah.zhttpdemo.data.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.Header
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.Response
import com.zkrallah.zhttp.ZHttpClient
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
    override fun login(
        userName: String,
        password: String,
        onComplete: (success: Response<AuthResponse>?, failure: Exception?) -> Unit
    ) {
        val body = User(userName, password)
        storeClient.post<AuthResponse>("", body, null, null, onComplete)
    }

    override fun fetchProducts(
        onComplete: (success: Response<List<ShopItem>>?, failure: Exception?) -> Unit
    ) {
        storeClient.get<List<ShopItem>>("products", null, null, onComplete)
    }

    override fun addProduct(
        product: ShopItem,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    ) {
        storeClient.post<ShopItem>("products", product, null, null, onComplete)
    }

    override fun deleteProduct(
        id: Int,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    ) {
        storeClient.delete<ShopItem>("products/$id", null, null, onComplete)
    }

    override fun updateOrAdd(
        id: Int,
        product: ShopItem,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    ) {
        storeClient.put<ShopItem>("products/$id", product, null, null, onComplete)
    }

    override fun update(
        id: Int,
        parameter: JsonObject,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    ) {
        storeClient.patch<ShopItem>("products/$id", parameter, null, null, onComplete)
    }

    override fun update(
        id: Int,
        parameter: NewTitle,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    ) {
        storeClient.patch<ShopItem>("products/$id", parameter, null, null, onComplete)
    }

    override fun uploadImagePart(
        parts: List<MultipartBody>,
        onComplete: (success: Response<String>?, failure: Exception?) -> Unit
    ) {
        val headers = listOf(
            Header("Authorization", Imgur.TOKEN)
        )
        imgurClient.multiPart<String>("3/image", parts, headers, null, onComplete)
    }

    companion object {
        private const val TAG = "MainRepoImpl"
    }
}