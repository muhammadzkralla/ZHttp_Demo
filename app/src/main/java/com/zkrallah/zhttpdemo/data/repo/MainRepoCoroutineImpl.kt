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
import com.zkrallah.zhttpdemo.domain.repo.MainRepoCoroutine
import com.zkrallah.zhttpdemo.util.Imgur
import javax.inject.Inject

class MainRepoCoroutineImpl @Inject constructor(
    @StoreClient private val storeClient: ZHttpClient,
    @ImgurClient private val imgurClient: ZHttpClient
) : MainRepoCoroutine {
    override suspend fun login(userName: String, password: String): Response<AuthResponse>? {
        val body = User(userName, password)
        return storeClient.post<AuthResponse>("auth/login", body, null, null)
    }

    override suspend fun fetchProducts(): Response<List<ShopItem>>? {
        return storeClient.get<List<ShopItem>>("products", null, null)
    }

    override suspend fun addProduct(product: ShopItem): Response<ShopItem>? {
        return storeClient.post<ShopItem>("products", product, null, null)
    }

    override suspend fun deleteProduct(id: Int): Response<ShopItem>? {
        return storeClient.delete<ShopItem>("products/$id", null, null)
    }

    override suspend fun updateOrAdd(id: Int, product: ShopItem): Response<ShopItem>? {
        return storeClient.put<ShopItem>("products/$id", product, null, null)
    }

    override suspend fun update(id: Int, parameter: JsonObject): Response<ShopItem>? {
        return storeClient.patch<ShopItem>("products/$id", parameter, null, null)
    }

    override suspend fun update(id: Int, parameter: NewTitle): Response<ShopItem>? {
        return storeClient.patch<ShopItem>("products/$id", parameter, null, null)
    }

    override suspend fun uploadImagePart(parts: List<MultipartBody>): Response<String>? {
        val headers = listOf(
            Header("Authorization", Imgur.TOKEN)
        )
        return imgurClient.multiPart("3/image", parts, null, headers)
    }
}