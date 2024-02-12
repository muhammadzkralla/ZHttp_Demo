package com.zkrallah.zhttpdemo.domain.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.HttpResponse
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttpdemo.domain.model.NewTitle
import com.zkrallah.zhttpdemo.domain.model.ShopItem

interface MainRepoSync {
    suspend fun login(userName: String, password: String): HttpResponse?

    suspend fun fetchProducts(): HttpResponse?

    suspend fun addProduct(product: ShopItem): HttpResponse?

    suspend fun deleteProduct(id: Int): HttpResponse?

    suspend fun updateOrAdd(id: Int, product: ShopItem): HttpResponse?

    suspend fun update(id: Int, parameter: JsonObject): HttpResponse?

    suspend fun update(id: Int, parameter: NewTitle): HttpResponse?

    suspend fun uploadImagePart(parts: List<MultipartBody>): HttpResponse?
}