package com.zkrallah.zhttpdemo.data.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.client.ZHttpClient
import com.zkrallah.zhttp.model.MultipartBody
import com.zkrallah.zhttp.model.Response
import com.zkrallah.zhttpdemo.di.ImgurClient
import com.zkrallah.zhttpdemo.di.StoreClient
import com.zkrallah.zhttpdemo.domain.model.AuthResponse
import com.zkrallah.zhttpdemo.domain.model.NewTitle
import com.zkrallah.zhttpdemo.domain.model.ShopItem
import com.zkrallah.zhttpdemo.domain.model.User
import com.zkrallah.zhttpdemo.domain.repo.MainRepoCoroutine
import javax.inject.Inject

class MainRepoCoroutineImpl @Inject constructor(
    @StoreClient private val storeClient: ZHttpClient,
    @ImgurClient private val imgurClient: ZHttpClient
) : MainRepoCoroutine {
    override suspend fun login(userName: String, password: String): Response<AuthResponse>? {
        val body = User(userName, password)
        return storeClient.post<AuthResponse>("auth/login", body)
    }

    override suspend fun fetchProducts(): Response<List<ShopItem>>? {
        return storeClient.get<List<ShopItem>>("products")
    }

    override suspend fun addProduct(product: ShopItem): Response<ShopItem>? {
        return storeClient.post<ShopItem>("products", product)
    }

    override suspend fun deleteProduct(id: Int): Response<ShopItem>? {
        return storeClient.delete<ShopItem>("products/$id")
    }

    override suspend fun updateOrAdd(id: Int, product: ShopItem): Response<ShopItem>? {
        return storeClient.put<ShopItem>("products/$id", product)
    }

    override suspend fun update(id: Int, parameter: JsonObject): Response<ShopItem>? {
        return storeClient.patch<ShopItem>("products/$id", parameter)
    }

    override suspend fun update(id: Int, parameter: NewTitle): Response<ShopItem>? {
        return storeClient.patch<ShopItem>("products/$id", parameter)
    }

    override suspend fun uploadImagePart(parts: List<MultipartBody>): Response<String>? {
        return imgurClient.multiPart("3/image", parts)
    }
}