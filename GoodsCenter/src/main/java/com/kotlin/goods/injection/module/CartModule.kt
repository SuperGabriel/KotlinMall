package com.kotlin.goods.injection.module

import com.kotlin.goods.service.CartService
import com.kotlin.goods.service.impl.CartServiceImpl
import dagger.Module
import dagger.Provides

/**
 * Create by Pidan
 */
@Module
class CartModule {
    @Provides
    fun providesCartService(service: CartServiceImpl): CartService = service
}