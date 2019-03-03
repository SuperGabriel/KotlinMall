package com.kotlin.base.injection.module

import android.content.Context
import com.kotlin.base.common.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Create by Pidan
 */
@Module
class AppModule(private val context: BaseApplication) {

    @Singleton
    @Provides
    fun providesContext(): Context {
        return context
    }
}