package com.kotlin.base.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.FrameLayout
import com.kotlin.base.R
import com.kotlin.base.common.AppManager
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import org.jetbrains.anko.find

/**
 * Create by Pidan
 */
open class BaseActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        AppManager.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.remove(this)
    }

    val contentView: View
        get() {
            val content = find<FrameLayout>(android.R.id.content)
            return content.getChildAt(0)
        }
}