package com.saigopal.shoppingapp.models

data class ShoppingData(
    val status: Boolean,
    val message: String,
    val error: String?,
    val categories: List<CategoryData>
)

data class CategoryData(
    val id: Int,
    val name: String,
    val items: List<ItemData>
)

data class ItemData(
    val id: Int,
    val name: String,
    val icon: String,
    val price: Double
)
