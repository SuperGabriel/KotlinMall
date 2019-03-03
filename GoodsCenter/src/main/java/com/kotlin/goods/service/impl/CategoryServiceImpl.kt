package com.kotlin.goods.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.goods.data.protocol.Category
import com.kotlin.goods.data.repository.CategoryRepository
import com.kotlin.user.service.CategoryService
import rx.Observable
import javax.inject.Inject

/**
 * Create by Pidan
 */
class CategoryServiceImpl @Inject constructor() : CategoryService {
    @Inject
    lateinit var repository: CategoryRepository

    override fun getCategory(parentId: Int): Observable<MutableList<Category>?> =
        repository.getCategory(parentId).convert()

}