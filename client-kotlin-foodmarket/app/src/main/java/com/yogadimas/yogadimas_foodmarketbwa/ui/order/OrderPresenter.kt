package com.yogadimas.yogadimas_foodmarketbwa.ui.order

import com.yogadimas.yogadimas_foodmarketbwa.data.networking.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrderPresenter (private val view: OrderContract.View) : OrderContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun getTransaction() {
        view.showLoading()
        val disposable = HttpClient.getApiService().transaction()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success",true)){
                        it.data?.let { it1 -> view.onTransactionSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onTransactionFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onTransactionFailed(it.message.toString())
                }
            )
        mCompositeDisposable.add(disposable)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable.clear()
    }

}