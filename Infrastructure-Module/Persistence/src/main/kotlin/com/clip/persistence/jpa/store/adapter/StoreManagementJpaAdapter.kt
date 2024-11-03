package com.clip.persistence.jpa.store.adapter

import com.clip.application.store.port.out.StoreManagementPort
import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.Store
import com.clip.domain.store.enums.DiscountPolicyMethod
import com.clip.persistence.jpa.store.StoreMapper
import com.clip.persistence.jpa.store.repository.*
import org.springframework.stereotype.Repository

@Repository
class StoreManagementJpaAdapter(
    private val storeJpaRepository: StoreJpaRepository
): StoreManagementPort {
    override fun getAllStores(zoneId: DomainId?): List<Store> {
        val stores = storeJpaRepository.findAllActiveStoreByZoneId(zoneId?.value)
        val storeIds = stores.map { it.id }

        val discountPolicies = storeJpaRepository.findAllByStoreIdInAndPolicyMethod(storeIds, DiscountPolicyMethod.DEFAULT)

        return stores.map { store ->
            val storeDiscountPolicies = discountPolicies.filter { it.storeId == store.id }
            StoreMapper.toStore(store, storeDiscountPolicies)
        }
    }

    override fun countFavoriteUsersBy(storeIds: List<DomainId>): LinkedHashMap<DomainId, Long> {
        val results = storeJpaRepository.countAllActiveFavoriteUserByStoreId(storeIds.map { it.value })
        return results.associate { (storeId, count) ->
            DomainId(storeId as String) to (count as Long)
        }.toList().toMap(LinkedHashMap())
    }

    override fun getFavoritedStoreIdsBy(userId: DomainId, storeIds: List<DomainId>): LinkedHashSet<DomainId> {
        val favoritedStoreIds = storeJpaRepository.findAllActiveFavoriteStoreIdAndUserId(storeIds.map { it.value }, userId.value)
        return LinkedHashSet(favoritedStoreIds.map { DomainId(it.storeId) })
    }

}