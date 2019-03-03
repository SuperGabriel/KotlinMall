package com.kotlin.order.ui.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.base.ui.activity.BaseActivity
import com.kotlin.provider.router.RouterPath
import com.kotlin.order.R
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.ui.adapter.OrderVpAdapter
import kotlinx.android.synthetic.main.activity_order.*

/**
 * Create by Pidan
 */
@Route(path = RouterPath.OrderCenter.PATH_ORDER_LIST)
class OrderActivity : BaseActivity() {
    @Autowired(name = OrderConstant.KEY_ORDER_STATUS)
    @JvmField
    var mOrderStatus = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        initView()
    }

    private fun initView() {
        mOrderTab.tabMode = TabLayout.MODE_FIXED
        mOrderVp.adapter = OrderVpAdapter(supportFragmentManager)
        mOrderTab.setupWithViewPager(mOrderVp)


//        mOrderVp.currentItem = intent.getIntExtra(OrderConstant.KEY_ORDER_STATUS, 0)
        mOrderVp.currentItem = mOrderStatus
    }
}