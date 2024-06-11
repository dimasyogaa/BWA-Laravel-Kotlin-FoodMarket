package com.yogadimas.yogadimas_foodmarketbwa.ui.detail.payment.presenter

import com.yogadimas.yogadimas_foodmarketbwa.base.BasePresenter
import com.yogadimas.yogadimas_foodmarketbwa.base.BaseView
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.checkout.CheckoutResponse

interface PaymentContract {

    interface View: BaseView {
        fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: android.view.View)
        fun onCheckoutFailed(message:String)

    }

    interface Presenter : PaymentContract, BasePresenter {
        fun getCheckout(foodId:String, userId:String, quantity:String, total:String, view: android.view.View)
    }
}