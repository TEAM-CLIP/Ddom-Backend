package com.clip.application.store.port.out

import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.Store

interface StoreManagementPort {
    fun getAllStores(zoneId: DomainId?): List<Store>
    fun countFavoriteUsersBy(storeIds: List<DomainId>): LinkedHashMap<DomainId, Long>
    fun getFavoritedStoreIdsBy(userId: DomainId, storeIds: List<DomainId>): LinkedHashSet<DomainId>
}