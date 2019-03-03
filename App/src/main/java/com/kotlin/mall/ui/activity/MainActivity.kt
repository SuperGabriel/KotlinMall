package com.kotlin.mall.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.kotlin.base.common.AppManager
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.mall.R
import com.kotlin.mall.ui.fragment.HomeFragment
import com.kotlin.mall.ui.fragment.MeFragment
import com.kotlin.goods.common.GoodsConstant
import com.kotlin.goods.event.UpdateCartSizeEvent
import com.kotlin.goods.ui.fragment.CartFragment
import com.kotlin.goods.ui.fragment.CategoryFragment
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kotlin.message.ui.fragment.MessageFragment
import com.kotlin.provider.event.MessageBadgeEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private var pressTime: Long = 0
    //Fragment 栈管理
    private val mStack = Stack<Fragment>()
    //主界面Fragment
    private val mHomeFragment by lazy { HomeFragment() }
    //商品分类Fragment
    private val mCategoryFragment by lazy { CategoryFragment() }
    //购物车Fragment
    private val mCartFragment by lazy { CartFragment() }
    //消息Fragment
    private val mMsgFragment by lazy { MessageFragment() }
    //"我的"Fragment
    private val mMeFragment by lazy { MeFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
        initBottomNav()
        changeFragment(0)
        initObserve()
        loadCartSize()
    }

    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContainer, mHomeFragment)
        manager.add(R.id.mContainer, mCategoryFragment)
        manager.add(R.id.mContainer, mCartFragment)
        manager.add(R.id.mContainer, mMsgFragment)
        manager.add(R.id.mContainer, mMeFragment)
        manager.commit()

        mStack.add(mHomeFragment)
        mStack.add(mCategoryFragment)
        mStack.add(mCartFragment)
        mStack.add(mMsgFragment)
        mStack.add(mMeFragment)
    }

    private fun initBottomNav() {
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {
            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
            }
        })
    }

    private fun loadCartSize() {
        mBottomNavBar.checkCartBadge(AppPrefsUtils.getInt(GoodsConstant.SP_CART_SIZE))
    }

    private fun initObserve() {
        Bus.observe<UpdateCartSizeEvent>()
            .subscribe { loadCartSize() }
            .registerInBus(this)

        Bus.observe<MessageBadgeEvent>()
            .subscribe { t: MessageBadgeEvent ->
                kotlin.run {
                    mBottomNavBar.checkMsgBadge(t.isVisible)
                }
            }
            .registerInBus(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    private fun changeFragment(position: Int) {
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack) {
            manager.hide(fragment)
        }

        manager.show(mStack[position])
        manager.commit()
    }

    /*
        重写Back事件，双击退出
     */
    override fun onBackPressed() {
        val time = System.currentTimeMillis()
        if (time - pressTime > 2000) {
            toast("再按一次退出程序")
            pressTime = time
        } else {
            AppManager.exitApp(this)
        }
    }
}
