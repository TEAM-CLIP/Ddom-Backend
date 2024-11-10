package com.clip.persistence.jpa.store.adapter

import com.clip.application.store.port.out.StoreManagementPort
import com.clip.common.paging.Page
import com.clip.common.paging.PageRequest
import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.Store
import com.clip.domain.store.enums.DiscountPolicyMethod
import com.clip.persistence.jpa.common.JpaPageUtils.convertToJpaPageable
import com.clip.persistence.jpa.common.JpaPageUtils.convertToPage
import com.clip.persistence.jpa.store.StoreMapper
import com.clip.persistence.jpa.store.repository.StoreJpaRepository
import com.clip.persistence.jpa.store.repository.countAllFavoriteUser
import com.clip.persistence.jpa.store.repository.findAllActiveBy
import com.clip.persistence.jpa.store.repository.findAllActiveDiscountPolicyBy
import org.springframework.stereotype.Repository

@Repository
class StoreManagementJpaAdapter(
    private val storeJpaRepository: StoreJpaRepository
) : StoreManagementPort {
    override fun getAllStores(zoneId: DomainId?): List<Store> {
        val stores = storeJpaRepository.findAllActiveBy(zoneId?.value)
        val storeIds = stores.map { it.id }

        val discountPolicies = storeJpaRepository.findAllActiveDiscountPolicyBy(storeIds, DiscountPolicyMethod.DEFAULT)

        return stores.map { store ->
            val storeDiscountPolicies = discountPolicies.filter { it.storeId == store.id }
            StoreMapper.toStore(store, storeDiscountPolicies)
        }
    }

    override fun getAllStores(pageRequest: PageRequest): Page<Store> {
        val pageable = pageRequest.convertToJpaPageable()
        val stores = storeJpaRepository.findAllActiveBy(pageable)

        return stores
            .map { StoreMapper.toStore(it, storeJpaRepository.findAllActiveDiscountPolicyBy(it.id)) }
            .convertToPage()
    }

    override fun countFavoriteUsersBy(storeIds: List<DomainId>): Map<DomainId, Long> {
        val results = storeJpaRepository.countAllFavoriteUser(storeIds.map { it.value })
        return results.associate { (storeId, count) ->
            DomainId(storeId) to count
        }.toMap()
    }

    override fun getFavoritedStoreIdsBy(userId: DomainId, storeIds: List<DomainId>): Set<DomainId> {
        val favoritedStoreIds = storeJpaRepository.findAllActiveBy(storeIds.map { it.value }, userId.value)
        return favoritedStoreIds.map { DomainId(it.storeId) }.toSet()
    }

    override fun getStoreBy(storeId: DomainId): Store {
        val store = storeJpaRepository.findById(storeId.value)
            .orElseThrow { throw IllegalArgumentException("Store not found") }

        val discountPolicies = storeJpaRepository.findAllActiveDiscountPolicyBy(storeId.value)
        return StoreMapper.toStore(store, discountPolicies)
    }

}