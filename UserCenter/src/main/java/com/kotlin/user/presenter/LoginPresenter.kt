package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.presenter.view.LoginView
import com.kotlin.user.service.UserService
import com.kotlin.user.utils.UserPrefsUtils
import javax.inject.Inject

/**
 * Create by Pidan
 */
class LoginPresenter @Inject constructor() : BasePresenter<LoginView>() {
    @Inject
    lateinit var userService: UserService

    fun login(mobile: String, psw: String, pushId: String) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        userService.login(mobile, psw, pushId)
            .execute(lifecycleProvider, object : BaseSubscriber<UserInfo>(mView) {
                override fun onNext(t: UserInfo) {
                    mView.onLoginResult(t)
                    UserPrefsUtils.putUserInfo(t)
                }
            })
    }
}