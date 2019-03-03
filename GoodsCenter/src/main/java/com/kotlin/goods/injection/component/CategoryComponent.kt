package com.kotlin.goods.injection.component

import com.kotlin.base.injection.PerComponentScope
import com.kotlin.base.injection.component.ActivityComponent
import com.kotlin.goods.injection.module.CategoryModule
import com.kotlin.goods.ui.fragment.CategoryFragment
import dagger.Component

/**
 * Create by Pidan
 */
@PerComponentScope
@Component(modules = [CategoryModule::class], dependencies = [ActivityComponent::class])
interface CategoryComponent {
    fun inject(fragment: CategoryFragment)
}