package com.kotlin.user.data.repository

import com.kotlin.base.data.net.RetrofitFactory
import com.kotlin.user.data.api.UploadApi
import javax.inject.Inject

/**
 * Create by Pidan
 */
class UploadRepository @Inject constructor() {
    fun getUploadToken() = RetrofitFactory.create(UploadApi::class.java).getUploadToken()
}