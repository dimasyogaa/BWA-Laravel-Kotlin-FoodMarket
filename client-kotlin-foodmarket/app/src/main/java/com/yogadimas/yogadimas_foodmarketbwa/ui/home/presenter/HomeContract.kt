package com.yogadimas.yogadimas_foodmarketbwa.ui.home.presenter

import com.yogadimas.yogadimas_foodmarketbwa.base.BasePresenter
import com.yogadimas.yogadimas_foodmarketbwa.base.BaseView
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.HomeResponse


interface HomeContract {

    interface View: BaseView {
        fun onHomeSuccess(homeResponse: HomeResponse)
        fun onHomeFailed(message:String)

    }

    interface Presenter : HomeContract, BasePresenter {
        fun getHome()
    }
}