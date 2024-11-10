package com.clip.application.store.port.out

import com.clip.common.paging.Page
import com.clip.common.paging.PageRequest
import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.Store

interface StoreManagementPort {
    fun getAllStores(zoneId: DomainId?): List<Store>
    fun countFavoriteUsersBy(storeIds: List<DomainId>): Map<DomainId, Long>
    fun getFavoritedStoreIdsBy(userId: DomainId, storeIds: List<DomainId>): Set<DomainId>

    fun getAllStores(pageRequest: PageRequest): Page<Store>

    fun getStoreBy(storeId: DomainId): Store
}