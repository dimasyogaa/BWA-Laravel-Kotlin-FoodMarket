package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signin.presenter

import com.yogadimas.yogadimas_foodmarketbwa.data.networking.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SigninPresenter(private val view: SigninContract.View) : SigninContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun submitLogin(email: String, password: String) {
        view.showLoading()
        val disposable = HttpClient.getApiService().login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success", true)) {
                        it.data?.let { data -> view.onLoginSuccess(data) }
                    } else {
                        it.meta?.message?.let { message -> view.onLoginFailed(message) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onLoginFailed(it.message.toString())
                }
            )
        disposable?.let { mCompositeDisposable.add(it) }
    }

    override fun subscribe() {
        // Implementasi dari fungsi ini
    }

    override fun unSubscribe() {
        mCompositeDisposable.clear()
    }
}