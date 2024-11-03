package com.clip.domain.store.entity

import com.clip.domain.common.Aggregate
import com.clip.domain.common.DomainId
import com.clip.domain.store.vo.StoreCategoryInfo
import com.clip.domain.store.vo.DiscountInfo
import com.clip.domain.store.vo.StoreInfo

class Store(
    id: DomainId,
    val storeInfo: StoreInfo,
    val storeCategory: StoreCategoryInfo,
    val discount : List<DiscountInfo>
) : Aggregate<Store>(id) {

}