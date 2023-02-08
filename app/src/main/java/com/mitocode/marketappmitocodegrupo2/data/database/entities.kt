package com.mitocode.marketappmitocodegrupo2.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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

