package com.saigopal.shoppingapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Items(
    @PrimaryKey(false)
    @ColumnInfo(name = "id")
    var id:Int,

    @ColumnInfo(name = "categoryId")
    var categoryId:Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "icon")
    var icon:String,

    @ColumnInfo(name = "price")
    var price:Double
)
