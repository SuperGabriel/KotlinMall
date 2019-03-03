package com.kotlin.goods.service

import com.kotlin.goods.data.protocol.CartGoods
import rx.Observable

/**
 * Create by Pidan
 */
interface CartService {
    fun addCart(
        goodsId: Int,
        goodsDesc: String,
        goodsIcon: String,
        goodsPrice: Long,
        goodsCount: Int,
        goodsSku: String
    ): Observable<Int>

    fun getCartList(): Observable<MutableList<CartGoods>?>

    fun deleteCartList(list: List<Int>): Observable<Boolean>

    fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int>
}