package com.saigopal.shoppingapp.viewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.dataBase.ShoppingDatabase
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.models.Item
import com.saigopal.shoppingapp.models.ShoppingData
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var shoppingDatabase:ShoppingDatabase

    init {
        shoppingDatabase =  ShoppingDatabase.getInstance(application)
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
            }
            sharedPref.edit().putBoolean("dataLoaded",true).apply()
        }


    }

    private suspend fun updateCategoriesList(categoryList: List<Categories>){
        shoppingDatabase.shoppingDao().insertCategories(categoryList)
    }

    private suspend fun updateItemsListByCategories(itemList: List<Item>){
        shoppingDatabase.shoppingDao().insertItems(itemList)
    }




}