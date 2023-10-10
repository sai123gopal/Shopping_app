package com.saigopal.shoppingapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.saigopal.shoppingapp.R
import com.saigopal.shoppingapp.dataBase.ShoppingDatabase
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.models.Items
import com.saigopal.shoppingapp.models.ShoppingData
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var shoppingDatabase:ShoppingDatabase

    init {
        shoppingDatabase =  ShoppingDatabase.getInstance(application)
        val jsonString = application.resources.openRawResource(R.raw.shopping).bufferedReader().use { it.readText() }
        val shoppingData = Gson().fromJson(jsonString, ShoppingData::class.java)

        val categoryList = shoppingData.categories.map { Categories(it.id, it.name) }
        val itemList = shoppingData.categories.flatMap { category ->
            category.items.map { Items(it.id, category.id, it.name, it.icon, it.price) }
        }

        viewModelScope.launch {
            updateCategoriesList(categoryList)
            updateItemsListByCategories(itemList)
        }
    }

    private suspend fun updateCategoriesList(categoryList: List<Categories>){
        shoppingDatabase.shoppingDao().insertCategories(categoryList)
    }

    private suspend fun updateItemsListByCategories(itemsList: List<Items>){
        shoppingDatabase.shoppingDao().insertItems(itemsList)
    }




}