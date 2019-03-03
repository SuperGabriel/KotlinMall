package com.kotlin.goods.presenter.view

import com.kotlin.base.presenter.view.BaseView
import com.kotlin.goods.data.protocol.Category

/**
 * Create by Pidan
 */
interface CategoryView : BaseView {
    fun onGetCategoryResult(result: MutableList<Category>?)
}