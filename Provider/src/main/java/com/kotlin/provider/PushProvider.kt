package com.kotlin.provider

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Create by Pidan
 */
interface PushProvider : IProvider {
    fun getPushId(): String
}