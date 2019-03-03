package com.kotlin.user.presenter

import com.kotlin.base.presenter.BasePresenter
import com.kotlin.user.presenter.view.RegisterView
import com.kotlin.user.service.impl.UserServiceImpl
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

open class RegisterPresenter : BasePresenter<RegisterView>() {
    fun register(mobile: String, verifyCode: String, pwd: String) {
        /*
        * 业务逻辑
        * */
        val userService = UserServiceImpl()
        userService.register(mobile, verifyCode, pwd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Subscriber<Boolean>(){
                override fun onNext(t: Boolean) {
                    mView.onRegisterResult(t)
                }

                override fun onCompleted() {
                }

                override fun onError(e: Throwable?) {
                }

            })
    }
}