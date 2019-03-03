package com.kotlin.pay.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.pay.presenter.view.PayView
import com.kotlin.pay.service.PayService
import javax.inject.Inject

/*
    支付Presenter
 */
class PayPresenter @Inject constructor() : BasePresenter<PayView>() {
    @Inject
    lateinit var service: PayService

    /*
        获取支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        service.getPaySign(orderId, totalPrice).execute(lifecycleProvider, object : BaseSubscriber<String>(mView) {
            override fun onNext(t: String) {
                mView.onGetSignResult(t)
            }
        })

    }

    /*
        订单支付，同步订单状态
     */
    fun payOrder(orderId: Int) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        service.payOrder(orderId).execute(lifecycleProvider, object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onPayOrderResult(t)
            }
        })
    }
}
