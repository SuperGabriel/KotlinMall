package com.kotlin.message

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.kotlin.provider.common.ProviderConstant
import com.kotlin.provider.event.MessageBadgeEvent
import com.kotlin.provider.router.RouterPath
import org.json.JSONObject


/**
 * Create by Pidan
 */
class MessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        Log.d(TAG, "onReceive - " + intent.action)

        when (intent.action) {
            JPushInterface.ACTION_REGISTRATION_ID -> {
                val regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                Log.d(TAG, "[MyReceiver] 接收 Registration Id : " + regId!!)
            }
            JPushInterface.ACTION_MESSAGE_RECEIVED -> {
                Log.d(TAG, "收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE)!!)
                // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
                Bus.send(MessageBadgeEvent(true))
            }
            JPushInterface.ACTION_NOTIFICATION_RECEIVED -> {
                Log.d(TAG, "收到了通知")
                Bus.send(MessageBadgeEvent(true))
            }
            JPushInterface.ACTION_NOTIFICATION_OPENED -> {
                val extra = bundle.getString(JPushInterface.EXTRA_EXTRA)
                val json = JSONObject(extra)
                val orderId = json.getInt("orderId")
                ARouter.getInstance()
                    .build(RouterPath.OrderCenter.PATH_ORDER_DETAIL)
                    .withInt(ProviderConstant.KEY_ORDER_ID, orderId)
                    .navigation()
            }
            else -> Log.d(TAG, "Unhandled intent - " + intent.action)
        }
    }

    companion object {
        const val TAG = "MessageReceiver";
    }
}