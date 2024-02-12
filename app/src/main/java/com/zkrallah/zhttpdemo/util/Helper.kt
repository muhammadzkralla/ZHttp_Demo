package com.zkrallah.zhttpdemo.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Helper {
    inline fun <reified T> Gson.fromJson(json: String?): T? {
        json?.let {
            val type = object : TypeToken<T>() {}.type
            return this.fromJson(json, type)
        } ?: return null
    }
}