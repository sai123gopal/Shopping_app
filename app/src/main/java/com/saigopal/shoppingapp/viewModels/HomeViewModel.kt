package com.saigopal.shoppingapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.saigopal.shoppingapp.dataBase.ShoppingDatabase
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.models.Items

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var shoppingDatabase: ShoppingDatabase
    var mutableLiveDataCategoriesList : MutableLiveData<List<Categories>>

    init {
        shoppingDatabase =  ShoppingDatabase.getInstance(application)
        mutableLiveDataCategoriesList = MutableLiveData()
    }

    public suspend fun getAllCategoriesList(){
        mutableLiveDataCategoriesList.postValue(shoppingDatabase.shoppingDao().getCategories())
    }

    public suspend fun getItemsByCategories(id:Int):List<Items>{
        return shoppingDatabase.shoppingDao().getItemsByCategory(id)
    }

}