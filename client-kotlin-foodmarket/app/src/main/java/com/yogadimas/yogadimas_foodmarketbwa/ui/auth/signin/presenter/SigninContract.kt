package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signin.presenter

import com.yogadimas.yogadimas_foodmarketbwa.base.BasePresenter
import com.yogadimas.yogadimas_foodmarketbwa.base.BaseView
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth.AuthResponse

interface SigninContract {

    interface View: BaseView {
        fun onLoginSuccess(authResponse: AuthResponse)
        fun onLoginFailed(message:String)

    }

    interface Presenter : SigninContract, BasePresenter {
        fun submitLogin(email:String, password:String)
    }
}