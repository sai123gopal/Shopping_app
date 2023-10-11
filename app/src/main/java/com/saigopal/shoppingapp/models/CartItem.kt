package com.saigopal.shoppingapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,

    @ColumnInfo(name = "itemId")
    var itemId:Int,

    @ColumnInfo(name = "CategoryId")
    val categoryId:Int,

    @ColumnInfo(name = "Quantity")
    var quantity : Int,

    @ColumnInfo(name = "createdAt", defaultValue = "CURRENT_TIMESTAMP") 
    val createdAt: String
)
