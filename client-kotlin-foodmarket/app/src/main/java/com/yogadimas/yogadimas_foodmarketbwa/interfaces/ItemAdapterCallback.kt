package com.yogadimas.yogadimas_foodmarketbwa.interfaces

fun interface ItemAdapterCallback<T> {
    fun onClick(data: T)
}