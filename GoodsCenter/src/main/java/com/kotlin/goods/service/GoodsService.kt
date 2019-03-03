package com.kotlin.goods.service

import com.kotlin.goods.data.protocol.Goods
import rx.Observable

/**
 * Create by Pidan
 */
interface GoodsService {
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?>
    fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<MutableList<Goods>?>
    fun getGoodsDetail(goodsId: Int): Observable<Goods>
}