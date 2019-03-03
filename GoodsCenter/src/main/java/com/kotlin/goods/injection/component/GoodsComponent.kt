package com.kotlin.goods.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.goods.injection.module.CartModule
import com.kotlin.goods.injection.module.GoodsModule
import com.kotlin.goods.ui.activity.GoodsActivity
import com.kotlin.goods.ui.fragment.GoodsDetailTabOneFragment
import com.kotlin.goods.ui.fragment.GoodsDetailTabTwoFragment
import dagger.Component

/**
 * Create by Pidan
 */
@PerComponentScope
@Component(modules = [GoodsModule::class, CartModule::class], dependencies = [ActivityComponent::class])
interface GoodsComponent {
    fun inject(activity: GoodsActivity)
    fun inject(fragment: GoodsDetailTabOneFragment)
    fun inject(fragment: GoodsDetailTabTwoFragment)
}