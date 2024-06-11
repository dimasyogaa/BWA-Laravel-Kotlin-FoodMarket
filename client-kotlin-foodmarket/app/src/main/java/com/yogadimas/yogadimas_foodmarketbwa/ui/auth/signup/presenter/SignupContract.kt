package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signup.presenter

import android.net.Uri
import com.yogadimas.yogadimas_foodmarketbwa.base.BasePresenter
import com.yogadimas.yogadimas_foodmarketbwa.base.BaseView
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.request.RegisterRequest
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.auth.AuthResponse

interface SignupContract {

    interface View: BaseView {
        fun onRegisterSuccess(authResponse: AuthResponse, view:android.view.View)
        fun onRegisterPhotoSuccess(data: Any, view:android.view.View)
        fun onRegisterFailed(message:String)

    }

    interface Presenter : SignupContract, BasePresenter {
        fun submitRegister(registerRequest: RegisterRequest, view:android.view.View)
        fun submitPhotoRegister(filePath:Uri, view:android.view.View)
    }
}