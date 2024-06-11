package com.yogadimas.yogadimas_foodmarketbwa.ui.auth.signup.presenter

import android.content.Context
import android.net.Uri
import android.view.View
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.HttpClient
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.request.RegisterRequest
import com.yogadimas.yogadimas_foodmarketbwa.utils.uriToFile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class SignupPresenter(private val view: SignupContract.View, private val context: Context) : SignupContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun submitRegister(registerRequest: RegisterRequest, view: View) {
        this.view.showLoading()
        val disposable = HttpClient.getApiService().register(
            registerRequest.name,
            registerRequest.email,
            registerRequest.password,
            registerRequest.password_confirmation,
            registerRequest.address,
            registerRequest.city,
            registerRequest.houseNumber,
            registerRequest.phoneNumber
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    this.view.dismissLoading()
                    if (it.meta?.status.equals("success", true)) {
                        it.data?.let { data -> this.view.onRegisterSuccess(data, view) }
                    } else {
                        this.view.onRegisterFailed(it.meta?.message.toString())
                    }
                },
                {
                    this.view.dismissLoading()
                    this.view.onRegisterFailed(it.message.toString())
                }
            )
        disposable?.let { mCompositeDisposable.add(it) }
    }

    override fun submitPhotoRegister(filePath: Uri, view: View) {
        this.view.showLoading()

        val profileImageFile = uriToFile(filePath, context)

        val profileImageRequestBody = profileImageFile.asRequestBody("image/*".toMediaTypeOrNull())

        val profileImageParams = profileImageRequestBody.let {
            MultipartBody.Part.createFormData("file", profileImageFile.name, it)
        }

        val disposable = profileImageParams.let {
            HttpClient.getApiService().registerPhoto(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {wrapper ->
                        this.view.dismissLoading()
                        if (wrapper.meta?.status.equals("success", true)) {
                            wrapper.data?.let { data -> this.view.onRegisterPhotoSuccess(data,view) }
                        } else {
                            this.view.onRegisterFailed(wrapper.meta?.message.toString())
                        }
                    },
                    {throwable ->
                        this.view.dismissLoading()
                        this.view.onRegisterFailed(throwable.message.toString())
                    }
                )
        }
        disposable?.let { mCompositeDisposable.add(it) }
    }

    override fun subscribe() {}

    override fun unSubscribe() {
        mCompositeDisposable.clear()
    }
}
