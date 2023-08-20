package com.mitocode.marketappmitocodegrupo2.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "TableCategory")
data class DbCategory(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name="uuid")
    val uuid:String,
    @ColumnInfo(name="name")
    val name:String,
    @ColumnInfo(name="cover")
    val cover:String,
    @ColumnInfo(name="status")
    val status:Boolean  //T = Transferido , P= Pendiente
)

@Entity(tableName = "TableShoppingCart")
data class DbShoppingCart(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id:Int,
    @NotNull
    @ColumnInfo(name="categoryCode")
    val categoryCode:String,
    @NotNull
    @ColumnInfo(name="productCode")
    val productCode:String,
    @NotNull
    @ColumnInfo(name="productDescription")
    val productDescription:String,
    /*@NotNull
    @ColumnInfo(name="userCode")
    val userCode:String,*/
    @NotNull
    @ColumnInfo(name="amount")
    val amount: Int,
    @NotNull
    @ColumnInfo(name="price")
    val price: Double,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "total")
    val total: Double
)
