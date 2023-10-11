package com.saigopal.shoppingapp.models

data class ShoppingData(
    val status: Boolean,
    val message: String,
    val error: String?,
    val categories: List<Categories>
)


