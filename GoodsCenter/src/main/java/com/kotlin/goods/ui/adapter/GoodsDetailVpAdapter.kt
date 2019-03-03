package com.kotlin.goods.ui.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kotlin.goods.ui.fragment.GoodsDetailTabOneFragment
import com.kotlin.goods.ui.fragment.GoodsDetailTabTwoFragment

/**
 * Create by Pidan
 */
class GoodsDetailVpAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm) {

    private val titles = arrayOf("商品", "详情")

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            GoodsDetailTabOneFragment()
        } else {
            GoodsDetailTabTwoFragment()
        }
    }

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence? = titles[position]
}