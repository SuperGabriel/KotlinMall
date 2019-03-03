package com.kotlin.base.presenter.view

/**
 * Create by Pidan
 */
interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text: String)
}