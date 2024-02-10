package com.zkrallah.zhttpdemo.domain.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.ZListener
import com.zkrallah.zhttpdemo.domain.model.AuthResponse
import com.zkrallah.zhttpdemo.domain.model.ShopItem

interface MainRepo {

    fun login(userName: String, password: String, callback: ZListener<AuthResponse>)

    fun fetchProducts(callback: ZListener<List<ShopItem>>)

    fun addProduct(product: ShopItem, callback: ZListener<ShopItem>)

    fun deleteProduct(id: Int, callback: ZListener<ShopItem>)

    fun updateOrAdd(id: Int, product: ShopItem, callback: ZListener<ShopItem>)

    fun update(id: Int, parameter: JsonObject, callback: ZListener<ShopItem>)

    fun uploadImagePart(parts: List<MultipartBody>, callback: ZListener<String>)
}