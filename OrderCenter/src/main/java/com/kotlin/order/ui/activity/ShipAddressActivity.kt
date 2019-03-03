package com.kotlin.order.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.activity.BaseMvpActivity
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import com.kotlin.order.R
import com.kotlin.order.R.id.mAddAddressBtn
import com.kotlin.order.R.id.mMultiStateView
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.data.protocol.ShipAddress
import com.kotlin.order.event.SelectAddressEvent
import com.kotlin.order.injection.component.DaggerShipAddressComponent
import com.kotlin.order.injection.module.ShipAddressModule
import com.kotlin.order.presenter.ShipAddressPresenter
import com.kotlin.order.presenter.view.ShipAddressView
import com.kotlin.order.ui.adapter.ShipAddressAdapter
import kotlinx.android.synthetic.main.activity_address.*
import org.jetbrains.anko.startActivity

/**
 * Create by Pidan
 */
class ShipAddressActivity : BaseMvpActivity<ShipAddressPresenter>(), ShipAddressView {

    private lateinit var mAdapter: ShipAddressAdapter

    override fun injectComponent() {
        DaggerShipAddressComponent.builder()
            .activityComponent(mActivityComponent)
            .shipAddressModule(ShipAddressModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        intiView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun intiView() {
        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(this)
        mAddressRv.adapter = mAdapter

        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }

        mAdapter.mOptClickListener = object : ShipAddressAdapter.OnOptClickListener {
            override fun onSetDefault(address: ShipAddress) {
                mPresenter.setDefaultShipAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)
            }

            override fun onDelete(address: ShipAddress) {
                AlertView.Builder().setContext(this@ShipAddressActivity)
                    .setStyle(AlertView.Style.Alert)
                    .setTitle("删除")
                    .setMessage("确定删除该地址？")
                    .setCancelText("取消")
                    .setDestructive("确定")
                    .setOthers(null)
                    .setOnItemClickListener { o, position ->
                        when (position) {
                            0 -> mPresenter.deleteShipAddress(address.id)
                        }
                    }.build()
                    .show()
            }
        }
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress> {
            override fun onItemClick(item: ShipAddress, position: Int) {
                Bus.send(SelectAddressEvent(item))
                finish()
            }
        })
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getShipAddressList()
    }

    override fun onGetShipAddressResult(result: MutableList<ShipAddress>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onSetDefaultResult(result: Boolean) {
        loadData()
    }

    override fun onDeleteResult(result: Boolean) {
        loadData()
    }
}