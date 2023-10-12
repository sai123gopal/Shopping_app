package com.saigopal.shoppingapp.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.dataBase.ShoppingDatabase
import com.saigopal.shoppingapp.models.CartItem
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.models.Item
import com.saigopal.shoppingapp.models.ShoppingData
import com.saigopal.shoppingapp.repo.DatabaseRepo
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var shoppingDatabase:ShoppingDatabase
    private var databaseRepo: DatabaseRepo
    var mutableCategoryData : MutableLiveData<List<Categories>>
    var cartItems:LiveData<List<CartItem>>

    init {
        shoppingDatabase =  ShoppingDatabase.getInstance(application)

        shoppingDatabase =  ShoppingDatabase.getInstance(application)
        databaseRepo = DatabaseRepo(shoppingDatabase.shoppingDao())
        mutableCategoryData = MutableLiveData()
        cartItems = MutableLiveData()

        viewModelScope.launch {
            cartItems = shoppingDatabase.shoppingDao().getCartItemsLiveData()
        }


        val sharedPref = application.getSharedPreferences(application.getString(R.string.shared_preferences),Context.MODE_PRIVATE)
        val dataLoaded = sharedPref.getBoolean("dataLoaded",false)

        if(!dataLoaded){
            val jsonString = application.resources.openRawResource(R.raw.shopping).bufferedReader().use { it.readText() }
            val shoppingData = Gson().fromJson(jsonString, ShoppingData::class.java)

            val categoryList = shoppingData.categories.map { Categories(it.id, it.name) }
            val itemList = shoppingData.categories.flatMap { category ->
                category.items.map { Item(it.id, category.id, it.name, false, it.icon, it.price) }
            }

            viewModelScope.launch {
                updateCategoriesList(categoryList)
                updateItemsListByCategories(itemList)
                getAllCategoriesList()
            }
            sharedPref.edit().putBoolean("dataLoaded",true).apply()

        }else{
            viewModelScope.launch {
                getAllCategoriesList()
            }
        }

    }

    private suspend fun updateCategoriesList(categoryList: List<Categories>){
        shoppingDatabase.shoppingDao().insertCategories(categoryList)
    }

    private suspend fun updateItemsListByCategories(itemList: List<Item>){
        shoppingDatabase.shoppingDao().insertItems(itemList)
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
        mutableCategoryData.postValue(itemList)

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