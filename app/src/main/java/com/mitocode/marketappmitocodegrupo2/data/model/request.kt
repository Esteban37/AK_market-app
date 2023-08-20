package com.mitocode.marketappmitocodegrupo2.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email : String,
    val password: String,
    val firebaseToken : String = ""
)

data class CreateAccountRequest(
    val nombres:String,
    val apellidos:String,
    val email:String,
    val password:String,
    val celular:String,
    val genero:String,
    val nroDoc:String,
    val firebaseToken:String=""
)

data class RegisterCategoryRequest(
    val nombre:String,
    val cover:String
)

data class RegisterPurchaseRequest(
    @SerializedName("direccionEnvio")
    val direction: AddressRequest,
    @SerializedName("metodoPago")
    val paymentMethod: PaymentMethodRequest,
    @SerializedName("fechaHora")
    val dateTime: String,
    @SerializedName("productos")
    val products: List<ProductRequest>,
    val total: Double
)

data class AddressRequest (
    @SerializedName("tipo")
    val type: Int,
    @SerializedName("direccion")
    val direction: String,
    @SerializedName("referencia")
    val reference: String,
    @SerializedName("distrito")
    val district: String
)

data class PaymentMethodRequest (
    @SerializedName("tipo")
    val type: Int,
    @SerializedName("monto")
    val amount: Double
)

data class ProductRequest (
    @SerializedName("categoriaId")
    val categoryId: String,
    @SerializedName("productoId")
    val productId: String,
    @SerializedName("cantidad")
    val amount: Int
)
