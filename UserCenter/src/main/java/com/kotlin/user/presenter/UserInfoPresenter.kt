package com.kotlin.user.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.user.data.protocol.UserInfo
import com.kotlin.user.presenter.view.UserInfoView
import com.kotlin.user.service.UploadService
import com.kotlin.user.service.UserService
import javax.inject.Inject

/**
 * Create by Pidan
 */
class UserInfoPresenter @Inject constructor() : BasePresenter<UserInfoView>() {
    @Inject
    lateinit var userService: UserService
    @Inject
    lateinit var uploadService: UploadService

    fun editUser(userIcon: String, userName: String, userGender: String, userSign: String) {
        if (!checkNetwork()) return
        mView.showLoading()
        userService.editUser(userIcon, userName, userGender, userSign)
            .execute(lifecycleProvider, object : BaseSubscriber<UserInfo>(mView) {
                override fun onNext(t: UserInfo) {
                    mView.onEditUserResult(t)
                }
            })
    }


    fun getUploadToken() {
        if (!checkNetwork()) return
        mView.showLoading()
        uploadService.getUploadToken().execute(lifecycleProvider, object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetUploadTokenResult(t)
            }
        })
    }
}