package com.zkrallah.zhttpdemo.data.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.Header
import com.zkrallah.zhttp.HttpResponse
import com.zkrallah.zhttp.MultipartBody
import com.zkrallah.zhttp.ZDelete
import com.zkrallah.zhttp.ZGet
import com.zkrallah.zhttp.ZHttpClient
import com.zkrallah.zhttp.ZMultipart
import com.zkrallah.zhttp.ZPatch
import com.zkrallah.zhttp.ZPost
import com.zkrallah.zhttp.ZPut
import com.zkrallah.zhttpdemo.di.ImgurClient
import com.zkrallah.zhttpdemo.di.StoreClient
import com.zkrallah.zhttpdemo.domain.model.NewTitle
import com.zkrallah.zhttpdemo.domain.model.ShopItem
import com.zkrallah.zhttpdemo.domain.repo.MainRepoSync
import com.zkrallah.zhttpdemo.util.Imgur
import javax.inject.Inject

class MainRepoSyncImpl @Inject constructor(
    @StoreClient private val storeClient: ZHttpClient,
    @ImgurClient private val imgurClient: ZHttpClient
) : MainRepoSync {
    override fun login(userName: String, password: String): HttpResponse? {
        val body = JsonObject().apply {
            addProperty("username", userName)
            addProperty("password", password)
        }

        return ZPost(storeClient).doPost("auth/login", null, body, null)
    }

    override fun fetchProducts(): HttpResponse? {
        return ZGet(storeClient).doGet("products", null, null)
    }

    override fun addProduct(product: ShopItem): HttpResponse? {
        return ZPost(storeClient).doPost("products", null, product, null)
    }

    override fun deleteProduct(id: Int): HttpResponse? {
        return ZDelete(storeClient).doDelete("products/$id", null, null)
    }

    override fun updateOrAdd(id: Int, product: ShopItem): HttpResponse? {
        return ZPut(storeClient).doPut("products/$id", null, product, null)
    }

    override fun update(id: Int, parameter: JsonObject): HttpResponse? {
        return ZPatch(storeClient).doPatch("products/$id", null, parameter, null)
    }

    override fun update(id: Int, parameter: NewTitle): HttpResponse? {
        return ZPatch(storeClient).doPatch("products/$id", null, parameter, null)
    }

    override fun uploadImagePart(parts: List<MultipartBody>): HttpResponse? {
        val headers = listOf(
            Header("Authorization", Imgur.TOKEN)
        )
        return ZMultipart(imgurClient).doMultipart("3/image", null, parts, headers)
    }
}