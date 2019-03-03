package com.kotlin.base.injection.module

import com.trello.rxlifecycle.LifecycleProvider
import dagger.Module
import dagger.Provides

/**
 * Create by Pidan
 */
@Module
class LifecycleProviderModule(private val lifecycleProvider: LifecycleProvider<*>) {

    @Provides
    fun providesLifecycleProvider(): LifecycleProvider<*> {
        return lifecycleProvider
    }
}