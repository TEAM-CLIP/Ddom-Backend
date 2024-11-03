package com.clip.persistence.jpa.store

import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.StoreCategory
import com.clip.persistence.jpa.store.entity.StoreCategoryEntity

object StoreCategoryMapper {
    fun toStoreCategory(storeCategoryEntity: StoreCategoryEntity): StoreCategory {
        return StoreCategory(
            id = DomainId(storeCategoryEntity.id),
            type = storeCategoryEntity.type
        )
    }
}