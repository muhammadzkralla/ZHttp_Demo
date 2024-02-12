package com.zkrallah.zhttpdemo.domain.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.HttpResponse
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttpdemo.domain.model.NewTitle
import com.zkrallah.zhttpdemo.domain.model.ShopItem

interface MainRepoSync {
    fun login(userName: String, password: String): HttpResponse?

    fun fetchProducts(): HttpResponse?

    fun addProduct(product: ShopItem): HttpResponse?

    fun deleteProduct(id: Int): HttpResponse?

    fun updateOrAdd(id: Int, product: ShopItem): HttpResponse?

    fun update(id: Int, parameter: JsonObject): HttpResponse?

    fun update(id: Int, parameter: NewTitle): HttpResponse?

    fun uploadImagePart(parts: List<MultipartBody>): HttpResponse?
}