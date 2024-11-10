package com.clip.persistence.jpa.store

import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.Store
import com.clip.domain.store.vo.DiscountInfo
import com.clip.domain.store.vo.StoreCategoryInfo
import com.clip.domain.store.vo.StoreInfo
import com.clip.domain.store.vo.StorePlace
import com.clip.persistence.jpa.store.entity.StoreDiscountPolicyEntity
import com.clip.persistence.jpa.store.entity.StoreEntity

object StoreMapper {
    fun toStore(storeEntity: StoreEntity, discountPolicies: List<StoreDiscountPolicyEntity>): Store {
        return Store(
            id = DomainId(storeEntity.id),
            storeInfo = StoreInfo(
                name = storeEntity.name,
                imgUrl = storeEntity.imgUrl,
                introduction = storeEntity.introduction,
                isRegistered = storeEntity.isRegistered,
            ),
            storePlace = StorePlace(
                longitude = storeEntity.longitude,
                latitude = storeEntity.latitude,
                zoneId = DomainId(storeEntity.zoneId)
            ),
            storeCategory = StoreCategoryInfo(DomainId(storeEntity.categoryId)),
            discount = discountPolicies.map {
                DiscountInfo(
                    discountId = DomainId(it.id),
                    discountPolicyMethod = it.method,
                    title = it.title
                )
            }
        )
    }
}