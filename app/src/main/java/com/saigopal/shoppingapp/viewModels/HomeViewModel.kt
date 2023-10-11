package com.saigopal.shoppingapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.saigopal.shoppingapp.dataBase.ShoppingDatabase
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.models.Item
import com.saigopal.shoppingapp.repo.DatabaseRepo

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var shoppingDatabase: ShoppingDatabase
    private var databaseRepo: DatabaseRepo
    var mutableCategoryData : MutableLiveData<List<Categories>>


    init {
        shoppingDatabase =  ShoppingDatabase.getInstance(application)
        mutableCategoryData = MutableLiveData()
        databaseRepo = DatabaseRepo(shoppingDatabase.shoppingDao())
    }

    suspend fun getAllCategoriesList(){
         val itemList : MutableList<Categories> = mutableListOf()

        shoppingDatabase.shoppingDao().getCategories().forEach{ categories ->
            run {
                val list = getItemsByCategories(categories.id)
                val category = Categories(categories.id,categories.name)
                category.items = list
                itemList.add(category)
            }
        }
        mutableCategoryData.value = itemList

    }

    private suspend fun getItemsByCategories(id:Int):List<Item>{
        return shoppingDatabase.shoppingDao().getItemsByCategory(id)
    }

    suspend fun addToFavorite(item:Item){
     databaseRepo.addItemFromFavorite(item)
    }

    suspend fun removeFromFavorite(item:Item){
        databaseRepo.removeItemFromFavorite(item)
    }

    suspend fun addToCart(item:Item){
        databaseRepo.addItemToCart(item)
    }


}