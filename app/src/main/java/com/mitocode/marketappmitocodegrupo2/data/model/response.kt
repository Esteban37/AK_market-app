package com.mitocode.marketappmitocodegrupo2.data.model

import com.google.gson.annotations.SerializedName

data class UserRemote(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("nombres")
    val names: String,
    @SerializedName("apellidos")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("celular")
    val phone: String,
    @SerializedName("genero")
    val gender: String,
    @SerializedName("nroDoc")
    val numberDoc: String,
    @SerializedName("tipo")
    val type: String,
)

data class GenderRemote(
    @SerializedName("genero")
    val code: String,
    @SerializedName("descripcion")
    val description: String
)

data class CategoryRemote(
    @SerializedName("uuid")
    val uuid: String,
    @SerializedName("nombre")
    val name: String,
    @SerializedName("cover")
    val cover: String
)

data class ProductRemote(
    @SerializedName("cantidad")
    val quantity: Int,
    @SerializedName("caracteristicas")
    val feautures: String,
    @SerializedName("codigo")
    val code: String,
    @SerializedName("descripcion")
    val description: String,
    @SerializedName("imagenes")
    val images: List<String>,
    @SerializedName("precio")
    val price: Double,
    @SerializedName("stock")
    val stock: Int,
    @SerializedName("uuid")
    val uuid: String
)
