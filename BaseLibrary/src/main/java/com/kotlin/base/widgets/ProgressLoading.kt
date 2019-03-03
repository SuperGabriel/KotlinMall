package com.kotlin.base.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.view.Gravity
import android.widget.ImageView
import com.kotlin.base.R
import org.jetbrains.anko.find

/**
 * Create by Pidan
 */
class ProgressLoading private constructor(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    companion object {
        private lateinit var dialog: ProgressLoading
        private var animDrawable: AnimationDrawable? = null

        fun create(context: Context): ProgressLoading {
            dialog = ProgressLoading(context, R.style.LightProgressDialog)
            dialog.setContentView(R.layout.progress_dialog)
            dialog.setCancelable(true)
            dialog.setCanceledOnTouchOutside(false)

            val lp = dialog.window.attributes
            lp.gravity = Gravity.CENTER
            lp.dimAmount = 0.2f
            dialog.window.attributes = lp

            val loadingView = dialog.find<ImageView>(R.id.iv_loading)
            animDrawable = loadingView.background as AnimationDrawable

            return dialog
        }
    }

    fun showLoading() {
        super.show()
        animDrawable?.start()
    }

    fun hideLoading() {
        super.dismiss()
        animDrawable?.stop()
    }
}