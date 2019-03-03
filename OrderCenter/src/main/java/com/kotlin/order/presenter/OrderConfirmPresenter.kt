package com.kotlin.order.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.presenter.view.OrderConfirmView
import com.kotlin.order.service.OrderService
import javax.inject.Inject

/*
    订单确认页 Presenter
 */
class OrderConfirmPresenter @Inject constructor() : BasePresenter<OrderConfirmView>() {

    @Inject
    lateinit var orderService: OrderService

    /*
        根据ID获取订单
     */
    fun getOrderById(orderId: Int) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        orderService.getOrderById(orderId).execute(lifecycleProvider, object : BaseSubscriber<Order>(mView) {
            override fun onNext(t: Order) {
                mView.onGetOrderByIdResult(t)
            }
        })

    }

    /*
        提交订单
     */
    fun submitOrder(order: Order) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        orderService.submitOrder(order).execute(lifecycleProvider, object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onSubmitOrderResult(t)
            }
        })

    }


}
