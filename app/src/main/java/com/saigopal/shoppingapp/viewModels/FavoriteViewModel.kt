package com.saigopal.shoppingapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.saigopal.shoppingapp.dataBase.ShoppingDatabase
import com.saigopal.shoppingapp.models.Item
import com.saigopal.shoppingapp.repo.DatabaseRepo

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var shoppingDatabase: ShoppingDatabase
    private var databaseRepo:DatabaseRepo
    var mutableLiveItemList : MutableLiveData<List<Item>>

    init {
        shoppingDatabase =  ShoppingDatabase.getInstance(application)
        mutableLiveItemList = MutableLiveData()
        databaseRepo = DatabaseRepo(shoppingDatabase.shoppingDao())
    }

    suspend fun getFavoriteItems(){
        mutableLiveItemList.value = shoppingDatabase.shoppingDao().getItemsByFavorite()
    }

    suspend fun removeItemFromFavorite(item:Item){
        databaseRepo.removeItemFromFavorite(item)
    }

    suspend fun addToCart(item:Item){
        databaseRepo.addItemToCart(item)
    }

}