package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.presenter.view.ForgetPwdView
import com.kotlin.user.service.UserService
import javax.inject.Inject

/**
 * Create by Pidan
 */
class ForgetPwdPresenter @Inject constructor() : BasePresenter<ForgetPwdView>() {
    @Inject
    lateinit var userService: UserService

    fun forgetPwd(mobile: String, verifyCode: String) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        userService.forgetPwd(mobile, verifyCode)
            .execute(lifecycleProvider, object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    if (t) mView.onForgetPwdResult("验证成功")
                }
            })
    }
}