package com.zkrallah.zhttpdemo.domain.model

data class Item(
    val id: String? = null,
    val name: String? = null,
    val data: Map<String, Any>? = null,
    val createdAt: String? = null
)