package com.saigopal.shoppingapp.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.models.Items

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Categories>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<Items>)

    @Query("SELECT * FROM categories")
    suspend fun getCategories():List<Categories>

    @Query("SELECT * FROM items WHERE categoryId = :categoryId")
    suspend fun getItemsByCategory(categoryId: Int): List<Items>
}