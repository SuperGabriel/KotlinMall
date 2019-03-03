package com.kotlin.goods.service.impl

import com.kotlin.base.ext.convert
import com.kotlin.base.ext.convertBoolean
import com.kotlin.goods.data.protocol.CartGoods
import com.kotlin.goods.data.repository.CartRepository
import com.kotlin.goods.service.CartService
import rx.Observable
import javax.inject.Inject

/**
 * Create by Pidan
 */
class CartServiceImpl @Inject constructor() : CartService {
    @Inject
    lateinit var repository: CartRepository

    override fun addCart(
        goodsId: Int,
        goodsDesc: String,
        goodsIcon: String,
        goodsPrice: Long,
        goodsCount: Int,
        goodsSku: String
    ): Observable<Int> =
        repository.addCart(goodsId, goodsDesc, goodsIcon, goodsPrice, goodsCount, goodsSku).convert()

    override fun getCartList(): Observable<MutableList<CartGoods>?> =
        repository.getCartList().convert()

    override fun deleteCartList(list: List<Int>): Observable<Boolean> =
        repository.deleteCartList(list).convertBoolean()

    override fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int> =
        repository.submitCart(list, totalPrice).convert()
}