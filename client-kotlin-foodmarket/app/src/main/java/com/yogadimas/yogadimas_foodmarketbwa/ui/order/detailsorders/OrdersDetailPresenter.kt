package com.yogadimas.yogadimas_foodmarketbwa.ui.order.detailsorders

import com.yogadimas.yogadimas_foodmarketbwa.data.networking.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OrdersDetailPresenter (private val view: OrdersDetailContract.View) : OrdersDetailContract.Presenter {

    private val mCompositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun getUpdateTransaction(id:String, status:String) {
        view.showLoading()
        val disposable = HttpClient.getApiService().transactionUpdate(id, status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()

                    if (it.meta?.status.equals("success")) {
                        view.onUpdateTransactionSuccess(it.meta?.message.toString())
                    } else {
                        view.onUpdateTransactionFailed(it.meta?.message.toString())
                    }

                },
                {
                    view.dismissLoading()
                    view.onUpdateTransactionFailed(it.message.toString())
                })
        mCompositeDisposable.add(disposable)
    }

    override fun subscribe() {}

    override fun unSubscribe() {
        mCompositeDisposable.clear()
    }
}