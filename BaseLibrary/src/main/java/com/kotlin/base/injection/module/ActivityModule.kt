package com.kotlin.base.injection.module

import android.app.Activity
import com.kotlin.base.injection.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Create by Pidan
 */
@Module
class ActivityModule(private val activity: Activity) {

    @ActivityScope
    @Provides
    fun providesActivity(): Activity {
        return activity
    }
}