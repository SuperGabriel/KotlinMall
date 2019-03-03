package com.kotlin.base.common

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/**
 * Create by Pidan
 */
object AppManager {
    private val activityStack: Stack<Activity> = Stack()

    fun add(activity: Activity) {
        activityStack.add(activity)
    }

    fun remove(activity: Activity) {
        activityStack.remove(activity)
    }

    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    fun currentActivity(): Activity = activityStack.lastElement()

    fun finishAllActivity() {
        activityStack.forEach {
            it.finish()
        }
        activityStack.clear()
    }

    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}