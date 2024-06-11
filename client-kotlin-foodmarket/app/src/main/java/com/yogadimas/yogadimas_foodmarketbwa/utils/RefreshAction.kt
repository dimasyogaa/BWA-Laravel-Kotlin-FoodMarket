package com.yogadimas.yogadimas_foodmarketbwa.utils

sealed class RefreshAction {
    data object Popular : RefreshAction()
    data object Recommended : RefreshAction()
}