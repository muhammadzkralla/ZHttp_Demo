package com.zkrallah.zhttpdemo.domain.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.Response
import com.zkrallah.zhttpdemo.domain.model.AuthResponse
import com.zkrallah.zhttpdemo.domain.model.NewTitle
import com.zkrallah.zhttpdemo.domain.model.ShopItem

interface MainRepo {

    fun login(
        userName: String,
        password: String,
        onComplete: (success: Response<AuthResponse>?, failure: Exception?) -> Unit
    )

    fun fetchProducts(
        onComplete: (success: Response<List<ShopItem>>?, failure: Exception?) -> Unit
    )

    fun addProduct(
        product: ShopItem,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    )

    fun deleteProduct(
        id: Int,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    )

    fun updateOrAdd(
        id: Int,
        product: ShopItem,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    )

    fun update(
        id: Int,
        parameter: JsonObject,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    )

    fun update(
        id: Int,
        parameter: NewTitle,
        onComplete: (success: Response<ShopItem>?, failure: Exception?) -> Unit
    )

    fun uploadImagePart(
        parts: List<MultipartBody>,
        onComplete: (success: Response<String>?, failure: Exception?) -> Unit
    )
}