package com.yogadimas.yogadimas_foodmarketbwa.ui.home.presenter

import io.reactivex.disposables.CompositeDisposable

class HomePresenter (private val view: HomeContract.View) : HomeContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun getHome() {
        // view.showLoading()
        // val disposable = HttpClient.getApiService().home()
        //     .subscribeOn(Schedulers.io())
        //     .observeOn(AndroidSchedulers.mainThread())
        //     .subscribe(
        //         {
        //             view.dismissLoading()
        //
        //             if (it.meta?.status.equals("success",true)){
        //                 it.data?.let { it1 -> view.onHomeSuccess(it1) }
        //             } else {
        //                 it.meta?.message?.let { it1 -> view.onHomeFailed(it1) }
        //             }
        //         },
        //         {
        //             view.dismissLoading()
        //             view.onHomeFailed(it.message.toString())
        //         }
        //     )
        // disposable?.let { mCompositeDisposable.add(it) }
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        // mCompositeDisposable.clear()
    }

}