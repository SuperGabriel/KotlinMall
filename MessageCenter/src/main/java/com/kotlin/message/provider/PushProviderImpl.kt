package com.kotlin.message.provider

import android.content.Context
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.facade.annotation.Route
import com.kotlin.provider.PushProvider
import com.kotlin.provider.router.RouterPath

/**
 * Create by Pidan
 */
@Route(path = RouterPath.MessageCenter.PATH_MESSAGE_PUSH)
class PushProviderImpl : PushProvider {
    var mContext: Context? = null

    override fun init(context: Context?) {
        mContext = context
    }

    override fun getPushId(): String = JPushInterface.getRegistrationID(mContext)
}