package com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @Expose
    @SerializedName("access_token")
    val access_token: String,
    @Expose
    @SerializedName("token_type")
    val token_type: String,
    @Expose
    @SerializedName("user")
    val user: User
)