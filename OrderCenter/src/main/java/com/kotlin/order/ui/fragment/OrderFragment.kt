package com.kotlin.order.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.alertview.AlertView
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.router.RouterPath
import com.kennyc.view.MultiStateView
import com.kotlin.order.R
import com.kotlin.order.R.id.mMultiStateView
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.data.protocol.Order
import com.kotlin.order.injection.component.DaggerOrderComponent
import com.kotlin.order.injection.module.OrderModule
import com.kotlin.order.presenter.OrderListPresenter
import com.kotlin.order.presenter.view.OrderListView
import com.kotlin.order.ui.activity.OrderDetailActivity
import com.kotlin.order.ui.adapter.OrderAdapter
import kotlinx.android.synthetic.main.fragment_order.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

/**
 * Create by Pidan
 */
class OrderFragment : BaseMvpFragment<OrderListPresenter>(), OrderListView {
    private lateinit var mAdapter: OrderAdapter

    override fun injectComponent() {
        DaggerOrderComponent.builder()
            .activityComponent(mActivityComponent)
            .orderModule(OrderModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mOrderRv.layoutManager = LinearLayoutManager(context)
        mAdapter = OrderAdapter(context!!)
        mOrderRv.adapter = mAdapter
        mAdapter.listener = object : OrderAdapter.OnOptClickListener {
            override fun onOptClick(optType: Int, order: Order) {
                when (optType) {
                    OrderConstant.OPT_ORDER_PAY -> {
                        ARouter.getInstance()
                            .build(RouterPath.PaySDK.PATH_PAY)
                            .withInt(ProviderConstant.KEY_ORDER_ID, order.id)
                            .withLong(ProviderConstant.KEY_ORDER_PRICE, order.totalPrice)
                            .navigation()
                    }
                    OrderConstant.OPT_ORDER_CONFIRM -> {
                        mMultiStateView.startLoading()
                        mPresenter.confirmOrder(order.id)
                    }
                    OrderConstant.OPT_ORDER_CANCEL -> {
                        showCancelDialog(order)
                    }
                }
            }
        }
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Order> {
            override fun onItemClick(item: Order, position: Int) {
                startActivity<OrderDetailActivity>(ProviderConstant.KEY_ORDER_ID to item.id)
            }
        })
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        arguments?.let {
            val orderStatus = it.getInt(OrderConstant.KEY_ORDER_STATUS, -1)
            mPresenter.getOrderList(orderStatus)
        }
    }

    private fun showCancelDialog(order: Order) {
        AlertView.Builder().setContext(context)
            .setStyle(AlertView.Style.Alert)
            .setTitle("取消")
            .setMessage("确定取消该订单？")
            .setCancelText("取消")
            .setDestructive("确定")
            .setOthers(null)
            .setOnItemClickListener { o, position ->
                if (position == 0) {
                    mMultiStateView.startLoading()
                    mPresenter.cancelOrder(order.id)
                }
            }.build()
            .show()
    }

    override fun onGetOrderListResult(result: MutableList<Order>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    override fun onConfirmOrderResult(result: Boolean) {
        toast("确认成功")
        loadData()
    }

    override fun onCancelOrderResult(result: Boolean) {
        toast("取消成功")
        loadData()
    }
}