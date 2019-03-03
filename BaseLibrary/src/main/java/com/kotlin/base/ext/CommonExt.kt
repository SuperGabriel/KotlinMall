package com.kotlin.base.ext

import android.graphics.drawable.AnimationDrawable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.kotlin.base.R
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.BaseFunc
import com.kotlin.base.rx.BaseFuncBoolean
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.utils.GlideUtils
import com.kotlin.base.widgets.DefaultTextWatcher
import com.kennyc.view.MultiStateView
import com.trello.rxlifecycle.LifecycleProvider
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Create by Pidan
 */
fun <T> Observable<T>.execute(lifecycleProvider: LifecycleProvider<*>, subscriber: BaseSubscriber<T>) {
    this.subscribeOn(Schedulers.io())
        .compose(lifecycleProvider.bindToLifecycle())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)
}

fun <T> Observable<BaseResp<T>>.convert(): Observable<T> = this.flatMap(BaseFunc())

fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> = this.flatMap(BaseFuncBoolean())

fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/*
    扩展点击事件
 */
fun View.onClick(methed: () -> Unit): View {
    setOnClickListener { methed() }
    return this
}

/*
    扩展点击事件，参数为方法
 */
fun Button.enable(et: EditText, method: () -> Boolean) {
    val btn: Button = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

/*
ImageView加载网络图片
*/
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}

fun MultiStateView.startLoading() {
    viewState = MultiStateView.VIEW_STATE_LOADING
    val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
    val animBackground = loadingView!!.findViewById<View>(R.id.loading_anim_view).background
    (animBackground as AnimationDrawable).start()
}

fun View.setVisible(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}
