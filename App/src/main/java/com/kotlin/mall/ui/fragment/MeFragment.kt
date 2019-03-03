package com.kotlin.mall.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlin.base.ext.loadUrl
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.fragment.BaseFragment
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.mall.R
import com.kotlin.mall.R.id.mUserIconIv
import com.kotlin.mall.R.id.mUserNameTv
import com.kotlin.mall.ui.activity.SettingActivity
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.common.afterLogin
import com.kotlin.provider.common.isLogined
import com.kotlin.user.ui.activity.UserInfoActivity
import com.kotlin.order.common.OrderConstant
import com.kotlin.order.common.OrderStatus
import com.kotlin.order.ui.activity.OrderActivity
import com.kotlin.order.ui.activity.ShipAddressActivity
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * Create by Pidan
 */
class MeFragment : BaseFragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_me, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    private fun initView() {
        mUserIconIv.onClick(this)
        mUserNameTv.onClick(this)
        mSettingTv.onClick(this)
        mAddressTv.onClick(this)
        mAllOrderTv.onClick(this)
        mWaitPayOrderTv.onClick(this)
        mWaitConfirmOrderTv.onClick(this)
        mCompleteOrderTv.onClick(this)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun loadData() {
        if (isLogined()) {
            val userIcon = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_ICON)
            if (userIcon.isNotEmpty()) {
                mUserIconIv.loadUrl(userIcon)
            }
            mUserNameTv.text = AppPrefsUtils.getString(ProviderConstant.KEY_SP_USER_NAME)
        } else {
            mUserIconIv.setImageResource(R.drawable.icon_default_user)
            mUserNameTv.text = getString(R.string.un_login_text)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mUserIconIv, R.id.mUserNameTv -> afterLogin { startActivity<UserInfoActivity>() }
            R.id.mSettingTv -> afterLogin { startActivity<SettingActivity>() }
            R.id.mAddressTv -> afterLogin { startActivity<ShipAddressActivity>() }
            R.id.mAllOrderTv -> afterLogin { startActivity<OrderActivity>() }
            R.id.mWaitPayOrderTv -> afterLogin { startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_PAY) }
            R.id.mWaitConfirmOrderTv -> afterLogin { startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_WAIT_CONFIRM) }
            R.id.mCompleteOrderTv -> afterLogin { startActivity<OrderActivity>(OrderConstant.KEY_ORDER_STATUS to OrderStatus.ORDER_COMPLETED) }
        }
    }
}