package com.yogadimas.yogadimas_foodmarketbwa.ui.order.detailsorders

import com.yogadimas.yogadimas_foodmarketbwa.base.BasePresenter
import com.yogadimas.yogadimas_foodmarketbwa.base.BaseView

interface OrdersDetailContract {
    interface View : BaseView {
        fun onUpdateTransactionSuccess(message: String)
        fun onUpdateTransactionFailed(message: String)
    }

    interface Presenter : OrdersDetailContract, BasePresenter {
        fun getUpdateTransaction(id:String, status:String)
    }
}