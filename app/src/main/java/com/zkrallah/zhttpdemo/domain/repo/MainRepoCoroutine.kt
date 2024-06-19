package com.zkrallah.zhttpdemo.domain.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.model.MultipartBody
import com.zkrallah.zhttp.model.Response
import com.zkrallah.zhttpdemo.domain.model.AuthResponse
import com.zkrallah.zhttpdemo.domain.model.NewTitle
import com.zkrallah.zhttpdemo.domain.model.ShopItem

interface MainRepoCoroutine {
    suspend fun login(userName: String, password: String): Response<AuthResponse>?

    suspend fun fetchProducts(): Response<List<ShopItem>>?

    suspend fun addProduct(product: ShopItem): Response<ShopItem>?

    suspend fun deleteProduct(id: Int): Response<ShopItem>?

    suspend fun updateOrAdd(id: Int, product: ShopItem): Response<ShopItem>?

    suspend fun update(id: Int, parameter: JsonObject): Response<ShopItem>?

    suspend fun update(id: Int, parameter: NewTitle): Response<ShopItem>?

    suspend fun uploadImagePart(parts: List<MultipartBody>): Response<String>?
}