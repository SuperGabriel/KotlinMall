package com.kotlin.base.injection.component

import android.app.Activity
import android.content.Context
import com.kotlin.base.injection.ActivityScope
import com.kotlin.base.injection.module.ActivityModule
import com.kotlin.base.injection.module.LifecycleProviderModule
import com.trello.rxlifecycle.LifecycleProvider
import dagger.Component

/**
 * Create by Pidan
 */
@ActivityScope
@Component(
    modules = [ActivityModule::class, LifecycleProviderModule::class],
    dependencies = [AppComponent::class]
)
interface ActivityComponent {
    fun activity(): Activity

    fun context(): Context

    fun lifecycleProvider(): LifecycleProvider<*>
}