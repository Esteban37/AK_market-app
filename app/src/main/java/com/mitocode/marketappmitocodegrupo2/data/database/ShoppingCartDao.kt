package com.mitocode.marketappmitocodegrupo2.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(dbShoppingCart: DbShoppingCart)

    @Update
    suspend fun update(dbShoppingCart: DbShoppingCart)

    @Delete
    suspend fun delete(dbShoppingCart: DbShoppingCart)

    @Delete
    suspend fun deleteAll(dbShoppingCartList: List<DbShoppingCart>)

    @Query("SELECT *FROM TableShoppingCart WHERE id=:id order by id desc")
    suspend fun findShoppingCartById(id:String) : List<DbShoppingCart>

    @Query("SELECT * FROM TableShoppingCart")
    fun getAll() : Flow<List<DbShoppingCart>>

    @Query("SELECT COUNT(id) FROM TableShoppingCart")
    suspend fun counter() : Int

}