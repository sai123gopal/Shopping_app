package com.saigopal.shoppingapp.repo

import com.saigopal.shoppingapp.daos.ShoppingDao
import com.saigopal.shoppingapp.models.CartItem
import com.saigopal.shoppingapp.models.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Calendar

class DatabaseRepo(private val dao: ShoppingDao) {

    suspend fun addItemToCart(item:Item){
        withContext(Dispatchers.IO) {
            val cartItem = dao.getCartItemById(item.id)
            if(cartItem == null){
                val newCartItem = CartItem(itemId = item.id, categoryId = item.categoryId, quantity = 1, createdAt = Calendar.getInstance().time.toString())
                newCartItem.itemId = item.id
                dao.insertCartItem(newCartItem)
            }else{
                increaseQuantity(cartItem)
            }
        }

    }

    suspend fun removeItemFromFavorite(item:Item){
        withContext(Dispatchers.IO) {
            item.isFavorite = false
            dao.updateItem(item)
        }
    }
    suspend fun addItemFromFavorite(item:Item){
        withContext(Dispatchers.IO) {
            item.isFavorite = true
            dao.updateItem(item)
        }
    }
    suspend fun increaseQuantity(cartItem:CartItem){
        withContext(Dispatchers.IO) {
            val quantity:Int = cartItem.quantity+1
            cartItem.quantity = quantity
            dao.updateCartItem(cartItem)
        }
    }

    suspend fun decreaseQuantity(cartItem:CartItem){
        withContext(Dispatchers.IO) {
            if(cartItem.quantity == 1){
                dao.deleteCartItem(cartItem)
            } else{
                val quantity:Int = cartItem.quantity-1
                cartItem.quantity = quantity
                dao.updateCartItem(cartItem)
            }
        }
    }

    suspend fun getUpdatedTotalCost():Double{
        return dao.getTotalCartPrice()
    }
    suspend fun clearCart(){
        dao.deleteAllCartItems()
    }

}