package com.kotlin.message.data.repository


import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.message.data.api.MessageApi
import com.kotlin.message.data.protocol.Message
import rx.Observable
import javax.inject.Inject


/*
   消息数据层
 */
class MessageRepository @Inject constructor() {

    /*
        获取消息列表
     */
    fun getMessageList(): Observable<BaseResp<MutableList<Message>?>> {
        return RetrofitFactory.create(MessageApi::class.java).getMessageList()
    }


}
