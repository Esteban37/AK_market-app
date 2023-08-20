package com.mitocode.marketappmitocodegrupo2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [DbCategory::class, DbShoppingCart::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    //Definir los daos
    abstract fun categoryDao() : CategoryDao

    abstract fun shoppingCartDao(): ShoppingCartDao

    //var instanceDb :  AppDatabase? = null

    /*fun getInstance(context: Context) : AppDatabase{

        if(instanceDb == null){
            instanceDb = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "BdMarket"
            ).build()
        }
        return instanceDb!!
    }*/

}