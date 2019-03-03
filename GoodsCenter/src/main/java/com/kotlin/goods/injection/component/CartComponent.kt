package com.kotlin.goods.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.goods.injection.module.CartModule
import com.kotlin.goods.ui.fragment.CartFragment
import dagger.Component

/**
 * Create by Pidan
 */
@PerComponentScope
@Component(modules = [CartModule::class], dependencies = [ActivityComponent::class])
interface CartComponent {
    fun inject(fragment: CartFragment)
}