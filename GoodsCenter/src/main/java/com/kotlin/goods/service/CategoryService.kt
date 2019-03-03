package com.kotlin.user.service

import com.kotlin.goods.data.protocol.Category
import rx.Observable

/**
 * Create by Pidan
 */
interface CategoryService {
    fun getCategory(parentId: Int): Observable<MutableList<Category>?>
}