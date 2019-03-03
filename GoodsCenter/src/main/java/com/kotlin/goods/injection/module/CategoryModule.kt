package com.kotlin.goods.injection.module

import com.kotlin.goods.service.impl.CategoryServiceImpl
import com.kotlin.user.service.CategoryService
import dagger.Module
import dagger.Provides

/**
 * Create by Pidan
 */
@Module
class CategoryModule {
    @Provides
    fun providesCategoryService(service: CategoryServiceImpl): CategoryService = service
}