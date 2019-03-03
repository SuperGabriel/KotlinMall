package com.kotlin.goods.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.presenter.view.CartListView
import com.kotlin.goods.service.CartService
import javax.inject.Inject

/**
 * Create by Pidan
 */
class CartListPresenter @Inject constructor() : BasePresenter<CartListView>() {
    @Inject
    lateinit var cartService: CartService

    fun getCartList() {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        cartService.getCartList()
            .execute(lifecycleProvider, object : BaseSubscriber<MutableList<CartGoods>?>(mView) {
                override fun onNext(t: MutableList<CartGoods>?) {
                    mView.onGetCartListResult(t)
                }
            })
    }

    fun deleteCartList(list: List<Int>) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        cartService.deleteCartList(list)
            .execute(lifecycleProvider, object : BaseSubscriber<Boolean>(mView) {
                override fun onNext(t: Boolean) {
                    mView.onDeleteCartListResult(t)
                }
            })
    }

    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        cartService.submitCart(list, totalPrice)
            .execute(lifecycleProvider, object : BaseSubscriber<Int>(mView) {
                override fun onNext(t: Int) {
                    mView.onSubmitCartResult(t)
                }
            })
    }
}