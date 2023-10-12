package com.saigopal.shoppingapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.saigopal.shoppingapp.dataBase.ShoppingDatabase
import com.saigopal.shoppingapp.models.CartData
import com.saigopal.shoppingapp.models.CartItem
import com.saigopal.shoppingapp.repo.DatabaseRepo
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private var shoppingDatabase: ShoppingDatabase
    private var databaseRepo: DatabaseRepo
    var mutableLiveCartList : MutableLiveData<List<CartData>>
    var mutableTotalCost :MutableLiveData<Double>
    var cartItemsList:LiveData<List<CartItem>>


    init {
        shoppingDatabase =  ShoppingDatabase.getInstance(application)
        mutableLiveCartList = MutableLiveData()
        mutableTotalCost = MutableLiveData(0.0)
        databaseRepo = DatabaseRepo(shoppingDatabase.shoppingDao())
        cartItemsList = MutableLiveData()
        cartItemsList = shoppingDatabase.shoppingDao().getCartItemsLiveData()
    }

    suspend fun getCartItems(){
         val cartItemsList:MutableList<CartItem> = mutableListOf()
         val cartDataList:MutableList<CartData> = mutableListOf()

        viewModelScope.launch {
            cartItemsList.addAll(shoppingDatabase.shoppingDao().getCartItems())
            for (cartItem in cartItemsList) {
                val item = shoppingDatabase.shoppingDao().getItemsById(cartItem.itemId)
                cartDataList.add(CartData(cartItem,item))
            }
            mutableLiveCartList.value = cartDataList
            updateCost()
        }
    }

    suspend fun clearCart(){
        databaseRepo.clearCart()
    }

    suspend fun increaseCartItemQuantity(cartItem:CartItem){
        databaseRepo.increaseQuantity(cartItem)
        updateCost()
    }
    suspend fun decreaseCartItemQuantity(cartItem:CartItem){
        databaseRepo.decreaseQuantity(cartItem)
        updateCost()
    }

    private suspend fun updateCost(){
        mutableTotalCost.postValue(databaseRepo.getUpdatedTotalCost())
    }
}