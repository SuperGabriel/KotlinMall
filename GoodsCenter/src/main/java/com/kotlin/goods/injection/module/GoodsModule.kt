package com.kotlin.goods.injection.module

import com.kotlin.goods.service.impl.GoodsServiceImpl
import com.kotlin.goods.service.GoodsService
import dagger.Module
import dagger.Provides

/**
 * Create by Pidan
 */
@Module
class GoodsModule {
    @Provides
    fun providesGoodsService(service: GoodsServiceImpl): GoodsService = service
}