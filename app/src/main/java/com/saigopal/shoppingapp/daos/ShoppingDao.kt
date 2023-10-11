package com.saigopal.shoppingapp.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.saigopal.shoppingapp.models.CartItem
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.models.Item

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Categories>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<Item>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(items: CartItem)

    @Query("SELECT * FROM categories WHERE (:categoryIds IS NULL OR id IN (:categoryIds))")
    suspend fun getCategories(categoryIds: List<Int>?):List<Categories>

    @Query("SELECT * FROM cart ORDER BY createdAt desc")
    suspend fun getCartItems():List<CartItem>

    @Query("SELECT * FROM cart")
    fun getCartItemsLiveData(): LiveData<List<CartItem>>

    @Query("SELECT * FROM items WHERE categoryId = :categoryId")
    suspend fun getItemsByCategory(categoryId: Int): List<Item>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemsById(id: Int): Item
    @Query("SELECT * FROM items WHERE isFavorite = 1")
    suspend fun getItemsByFavorite(): List<Item>

    @Query("SELECT * FROM cart WHERE itemId = :cartId")
    fun getCartItemById(cartId: Int): CartItem?
    @Update
    suspend fun updateItem(item:Item)
    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("SELECT SUM(items.price * cart.quantity) FROM items INNER JOIN cart ON items.id = cart.itemId")
    suspend fun  getTotalCartPrice(): Double
}