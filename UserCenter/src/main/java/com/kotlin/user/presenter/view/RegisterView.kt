package com.kotlin.user.presenter.view

import com.kotlin.base.presenter.view.BaseView

interface RegisterView:BaseView {
    fun onRegisterResult(result:Boolean)
}