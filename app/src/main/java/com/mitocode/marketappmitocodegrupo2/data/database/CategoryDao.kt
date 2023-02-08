package com.mitocode.marketappmitocodegrupo2.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(categories:List<DbCategory>)

    @Query("SELECT *FROM TableCategory")
    fun getAll() : Flow<List<DbCategory>>

    @Query("SELECT COUNT(uuid) FROM TableCategory")
    suspend fun counter() : Int

    //Examples
    @Update
    suspend fun update(category:DbCategory)

    @Delete
    suspend fun delete(category:DbCategory)

    @Query("SELECT *FROM TableCategory WHERE uuid=:id order by uuid desc")
    suspend fun findCategoryById(id:String) : List<DbCategory>




}