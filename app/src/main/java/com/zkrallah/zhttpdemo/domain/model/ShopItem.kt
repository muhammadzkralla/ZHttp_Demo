package com.zkrallah.zhttpdemo.domain.model

data class ShopItem(
    val id: Int? = null,
    val price: String? = null,
    val title: String? = null,
    val description: String? = null,
    val category: String? = null,
    val image: String? = null,
    val rating: Rating? = null
)
