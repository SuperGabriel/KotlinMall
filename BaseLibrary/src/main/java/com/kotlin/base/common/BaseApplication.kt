package com.kotlin.base.common

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.kotlin.base.injection.component.AppComponent
import com.kotlin.base.injection.component.DaggerAppComponent
import com.kotlin.base.injection.module.AppModule

/**
 * Create by Pidan
 */
open class BaseApplication : Application() {

    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        context = this
        initInjection()
        initARouter()
    }

    private fun initInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }

    private fun initARouter() {
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }

    companion object {
        lateinit var context: Context
    }
}