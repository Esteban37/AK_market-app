package com.mitocode.marketappmitocodegrupo2.data

import com.mitocode.marketappmitocodegrupo2.data.database.DbCategory
import com.mitocode.marketappmitocodegrupo2.data.model.UserRemote
import com.mitocode.marketappmitocodegrupo2.domain.model.Category
import com.mitocode.marketappmitocodegrupo2.domain.model.User

//Mapper de UserRemote a User
fun UserRemote.toDomainModel() : User = User(uuid,names,lastName,email,phone,gender,numberDoc,type)

//Mapper de DbCategory a Category
fun List<DbCategory>.toDomainModel() : List<Category> = map { it.toDomainModel() }
private fun DbCategory.toDomainModel() : Category = Category(uuid,name,cover)

//Mapper de Category a DbCategory
fun List<Category>.toLocalModel() : List<DbCategory> = map {it.toLocalModel()}
private fun Category.toLocalModel() : DbCategory = DbCategory(uuid,name,cover,true)