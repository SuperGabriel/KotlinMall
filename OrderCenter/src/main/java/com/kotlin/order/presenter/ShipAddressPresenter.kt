package com.kotlin.order.presenter

import com.kotlin.base.ext.execute
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.presenter.view.ShipAddressView
import com.kotlin.order.service.ShipAddressService
import javax.inject.Inject

/*
    收货人列表Presenter
 */
class ShipAddressPresenter @Inject constructor() : BasePresenter<ShipAddressView>() {

    @Inject
    lateinit var shipAddressService: ShipAddressService

    /*
        获取收货人列表
     */
    fun getShipAddressList() {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        shipAddressService.getShipAddressList()
            .execute(lifecycleProvider, object : BaseSubscriber<MutableList<ShipAddress>?>(mView) {
                override fun onNext(t: MutableList<ShipAddress>?) {
                    mView.onGetShipAddressResult(t)
                }
            })

    }

    /*
        设置默认收货人信息
     */
    fun setDefaultShipAddress(address: ShipAddress) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        shipAddressService.editShipAddress(address).execute(lifecycleProvider, object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onSetDefaultResult(t)
            }
        })

    }

    /*
        删除收货人信息
     */
    fun deleteShipAddress(id: Int) {
        if (!checkNetwork()) {
            return
        }
        mView.showLoading()
        shipAddressService.deleteShipAddress(id).execute(lifecycleProvider, object : BaseSubscriber<Boolean>(mView) {
            override fun onNext(t: Boolean) {
                mView.onDeleteResult(t)
            }
        })

    }

}
