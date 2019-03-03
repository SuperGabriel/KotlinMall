package com.kotlin.goods.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.ext.onClick
import com.kotlin.base.ext.setVisible
import com.kotlin.base.ext.startLoading
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseMvpFragment
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.goods.R
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.event.CartAllCheckedEvent
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.goods.event.UpdateTotalPriceEvent
import com.kotlin.goods.injection.component.DaggerCartComponent
import com.kotlin.goods.injection.module.CartModule
import com.kotlin.goods.presenter.CartListPresenter
import com.kotlin.goods.presenter.view.CartListView
import com.kotlin.goods.ui.adapter.CartGoodsAdapter
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.router.RouterPath
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import com.kotlin.base.utils.YuanFenConverter
import kotlinx.android.synthetic.main.fragment_cart.*
import org.jetbrains.anko.support.v4.toast

/**
 * Create by Pidan
 */
class CartFragment : BaseMvpFragment<CartListPresenter>(), CartListView {

    private lateinit var mAdapter: CartGoodsAdapter
    private var mTotalPrice: Long = 0

    override fun injectComponent() {
        DaggerCartComponent.builder()
            .activityComponent(mActivityComponent)
            .cartModule(CartModule())
            .build()
            .inject(this)
        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserve()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(context)
        mAdapter = CartGoodsAdapter(context!!)
        mCartGoodsRv.adapter = mAdapter
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<CartGoods> {
            override fun onItemClick(item: CartGoods, position: Int) {
            }
        })
        mHeaderBar.getRightView().onClick {
            refreshEditStatus()
        }
        mAllCheckedCb.onClick {
            for (item in mAdapter.dataList) {
                item.isSelected = mAllCheckedCb.isChecked
            }
            updateTotalPrice()
            mAdapter.notifyDataSetChanged()
        }
        mDeleteBtn.onClick {
            val cartIdList: MutableList<Int> = arrayListOf()
            mAdapter.dataList.filter { it.isSelected }
                .mapTo(cartIdList) { it.id }
            if (cartIdList.size == 0) {
                toast("请选择要删除的数据")
            } else {
                mPresenter.deleteCartList(cartIdList)
            }
        }
        mSettleAccountsBtn.onClick {
            val cartGoodsList: MutableList<CartGoods> = arrayListOf()
            mAdapter.dataList.filter { it.isSelected }
                .mapTo(cartGoodsList) { it }
            if (cartGoodsList.size == 0) {
                toast("请选择要提交的数据")
            } else {
                mPresenter.submitCart(cartGoodsList, mTotalPrice)
            }
        }
    }

    private fun initObserve() {
        Bus.observe<CartAllCheckedEvent>()
            .subscribe { t: CartAllCheckedEvent ->
                run {
                    mAllCheckedCb.isChecked = t.isAllChecked
                    updateTotalPrice()
                }
            }.registerInBus(this)
        Bus.observe<UpdateTotalPriceEvent>()
            .subscribe {
                updateTotalPrice()
            }.registerInBus(this)
    }

    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getCartList()
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private fun refreshEditStatus() {
        val isEditStatus = getString(R.string.common_edit) == mHeaderBar.getRightView().text
        mTotalPriceTv.setVisible(isEditStatus.not())
        mSettleAccountsBtn.setVisible(isEditStatus.not())
        mDeleteBtn.setVisible(isEditStatus)
        mHeaderBar.getRightView().text = if (isEditStatus)
            getString(R.string.common_complete)
        else getString(R.string.common_edit)
    }

    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mHeaderBar.getRightView().setVisible(true)
            mAllCheckedCb.isChecked = false
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            updateTotalPrice()
        } else {
            mHeaderBar.getRightView().setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }

        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE, result?.size ?: 0)
        Bus.send(UpdateCartSizeEvent())
    }

    override fun onDeleteCartListResult(result: Boolean) {
        toast("删除成功")
        refreshEditStatus()
        loadData()
    }

    private fun updateTotalPrice() {
        mTotalPrice = mAdapter.dataList
            .filter { it.isSelected }
            .map { it.goodsCount * it.goodsPrice }
            .sum()
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(mTotalPrice)}"
    }

    override fun onSubmitCartResult(result: Int) {
        ARouter.getInstance()
            .build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
            .withInt(ProviderConstant.KEY_ORDER_ID, result)
            .navigation()
    }

    fun setBackVisible(isVisible: Boolean) {
        mHeaderBar.getLeftView().setVisible(isVisible)
    }
}
