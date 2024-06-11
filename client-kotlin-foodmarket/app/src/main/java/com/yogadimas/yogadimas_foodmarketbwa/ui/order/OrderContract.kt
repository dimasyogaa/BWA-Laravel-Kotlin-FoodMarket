package com.yogadimas.yogadimas_foodmarketbwa.ui.order

import com.yogadimas.yogadimas_foodmarketbwa.base.BasePresenter
import com.yogadimas.yogadimas_foodmarketbwa.base.BaseView
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.TransactionResponse

interface OrderContract {

    interface View: BaseView {
        fun onTransactionSuccess(transactionResponse: TransactionResponse)
        fun onTransactionFailed(message:String)

    }

    interface Presenter : OrderContract, BasePresenter {
        fun getTransaction()
    }
}