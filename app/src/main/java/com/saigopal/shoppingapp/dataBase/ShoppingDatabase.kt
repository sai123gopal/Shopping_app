package com.saigopal.shoppingapp.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.saigopal.shoppingapp.daos.ShoppingDao
import com.saigopal.shoppingapp.models.Categories
import com.saigopal.shoppingapp.models.Items

@Database(entities = [Categories::class, Items::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase :RoomDatabase(){

    abstract fun shoppingDao() :ShoppingDao

    companion object{
        private var instance : ShoppingDatabase? = null
        private const val dataBaseName = "shopping_db"

        @Synchronized
        fun getInstance(ctx: Context):ShoppingDatabase{

            return instance ?: synchronized(this){
                val  valInstance: ShoppingDatabase =
                    Room.databaseBuilder(
                    ctx.applicationContext, ShoppingDatabase::class.java, dataBaseName)
                        .fallbackToDestructiveMigration()
                        .build()

                instance = valInstance

                valInstance
            }
        }


    }

}