package com.zkrallah.zhttpdemo.domain.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.ZListener
import com.zkrallah.zhttpdemo.domain.model.AuthResponse
import com.zkrallah.zhttpdemo.domain.model.DeleteResponse
import com.zkrallah.zhttpdemo.domain.model.Item

interface MainRepo {

    fun login(userName: String, password: String, callback: ZListener<AuthResponse>)

    fun fetchProducts(callback: ZListener<List<Item>>)

    fun addProduct(product: Item, callback: ZListener<Item>)

    fun deleteProduct(id: Int, callback: ZListener<DeleteResponse>)

    fun updateOrAdd(id: Int, product: Item, callback: ZListener<Item>)

    fun update(id: Int, parameter: JsonObject, callback: ZListener<Item>)

    fun uploadImagePart(parts: List<MultipartBody>, callback: ZListener<String>)
}