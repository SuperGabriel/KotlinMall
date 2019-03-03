package com.kotlin.user.data.api

import com.kotlin.base.data.protocol.BaseResp
import retrofit2.http.POST
import rx.Observable

/**
 * Create by Pidan
 */
interface UploadApi {
    @POST("common/getUploadToken")
    fun getUploadToken(): Observable<BaseResp<String>>
}