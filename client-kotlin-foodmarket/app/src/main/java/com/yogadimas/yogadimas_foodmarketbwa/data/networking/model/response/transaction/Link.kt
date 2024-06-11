package com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Link(
    @Expose
    @SerializedName("active")
    val active: Boolean,
    @Expose
    @SerializedName("label")
    val label: Any,
    @Expose
    @SerializedName("url")
    val url: Any
)