package com.clip.application.store.port.out

import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.StoreCategory

interface StoreCategoryManagementPort {
    fun getCategoriesBy(categoryIds: List<DomainId>): List<StoreCategory>
}