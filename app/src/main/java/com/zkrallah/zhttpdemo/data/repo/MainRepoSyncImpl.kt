package com.zkrallah.zhttpdemo.data.repo

import com.google.gson.JsonObject
import com.zkrallah.zhttp.model.Header
import com.zkrallah.zhttp.model.HttpResponse
import com.zkrallah.zhttp.model.MultipartBody
import com.zkrallah.zhttp.core.ZDelete
import com.zkrallah.zhttp.core.ZGet
import com.zkrallah.zhttp.client.ZHttpClient
import com.zkrallah.zhttp.core.ZMultipart
import com.zkrallah.zhttp.core.ZPatch
import com.zkrallah.zhttp.core.ZPost
import com.zkrallah.zhttp.core.ZPut
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
    override suspend fun login(userName: String, password: String): HttpResponse? {
        val body = JsonObject().apply {
            addProperty("username", userName)
            addProperty("password", password)
        }

        return ZPost(storeClient).doPost("auth/login", body, null, null)
    }

    override suspend fun fetchProducts(): HttpResponse? {
        return ZGet(storeClient).doGet("products", null, null)
    }

    override suspend fun addProduct(product: ShopItem): HttpResponse? {
        return ZPost(storeClient).doPost("products", product, null, null)
    }

    override suspend fun deleteProduct(id: Int): HttpResponse? {
        return ZDelete(storeClient).doDelete("products/$id", null, null)
    }

    override suspend fun updateOrAdd(id: Int, product: ShopItem): HttpResponse? {
        return ZPut(storeClient).doPut("products/$id", product, null, null)
    }

    override suspend fun update(id: Int, parameter: JsonObject): HttpResponse? {
        return ZPatch(storeClient).doPatch("products/$id", parameter, null, null)
    }

    override suspend fun update(id: Int, parameter: NewTitle): HttpResponse? {
        return ZPatch(storeClient).doPatch("products/$id", parameter, null, null)
    }

    override suspend fun uploadImagePart(parts: List<MultipartBody>): HttpResponse? {
        val headers = listOf(
            Header("Authorization", Imgur.TOKEN)
        )
        return ZMultipart(imgurClient).doMultipart("3/image", parts, null, headers)
    }
}