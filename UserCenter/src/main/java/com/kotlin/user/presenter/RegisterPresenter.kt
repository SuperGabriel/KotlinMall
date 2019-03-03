package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.presenter.view.RegisterView
import com.kotlin.user.service.UserService
import javax.inject.Inject

/**
 * Create by Pidan
 */
class RegisterPresenter @Inject constructor() : BasePresenter<RegisterView>() {
    @Inject
    lateinit var userService: UserService

    fun register(mobile: String, psw: String, verifyCode: String) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        userService.register(mobile, psw, verifyCode)
            .execute(lifecycleProvider, object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    if (t) mView.onRegisterResult("注册成功")
                }
            })
    }
}