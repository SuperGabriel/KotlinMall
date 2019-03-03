package com.kotlin.base.data.protocol

/**
 * Create by Pidan
 */
data class BaseResp<T>(val status: Int, val message: String, val data: T)