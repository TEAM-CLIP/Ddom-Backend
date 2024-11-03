package com.clip.persistence.jpa.store.repository

import com.clip.domain.store.enums.DiscountPolicyMethod
import com.clip.persistence.jpa.common.EntityStatus
import com.clip.persistence.jpa.store.entity.FavoriteStoreEntity
import com.clip.persistence.jpa.store.entity.StoreDiscountPolicyEntity
import com.clip.persistence.jpa.store.entity.StoreEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StoreJpaRepository : JpaRepository<StoreEntity, String> {
    @Query(
        """
    SELECT r.storeId, COUNT(r.id)
    FROM FavoriteStoreEntity r
    WHERE r.storeId IN :storeIds
    AND r.entityStatus = :entityStatus
    GROUP BY r.storeId
    """
    )
    fun countAllFavoriteBy(
        storeIds: List<String>,
        entityStatus: EntityStatus
    ): List<Array<Any>>

    @Query(
        """
        SELECT r
        FROM FavoriteStoreEntity r
        WHERE r.storeId IN :storeIds
        AND r.userId = :userId
        AND r.entityStatus = :entityStatus
    """,
    )
    fun findAllBy(
        storeIds: List<String>,
        userId: String,
        entityStatus: EntityStatus,
    ): List<FavoriteStoreEntity>

    @Query(
        """
        SELECT r
        FROM StoreDiscountPolicyEntity r
        WHERE r.storeId IN :storeIds
        AND r.method = :discountPolicyMethod
        AND r.entityStatus = :entityStatus
    """,
    )
    fun findAllBy(
        storeIds: List<String>,
        discountPolicyMethod: DiscountPolicyMethod,
        entityStatus: EntityStatus,
    ): List<StoreDiscountPolicyEntity>

    @Query(
        """
    SELECT s
    FROM StoreEntity s
    WHERE (:zoneId IS NULL OR s.zoneId = :zoneId)
    AND s.entityStatus = :entityStatus
    """
    )
    fun findAllBy(
        zoneId: String?,
        entityStatus: EntityStatus
    ): List<StoreEntity>

}

fun StoreJpaRepository.countAllActiveFavoriteUserByStoreId(storeIds: List<String>): List<Array<Any>> =
    countAllFavoriteBy(storeIds, EntityStatus.ACTIVE)

fun StoreJpaRepository.findAllActiveFavoriteStoreIdAndUserId(storeIds: List<String>, userId: String): List<FavoriteStoreEntity> =
    findAllBy(storeIds, userId, EntityStatus.ACTIVE)

fun StoreJpaRepository.findAllByStoreIdInAndPolicyMethod(storeIds: List<String>, discountPolicyMethod: DiscountPolicyMethod): List<StoreDiscountPolicyEntity> =
    findAllBy(storeIds, discountPolicyMethod, EntityStatus.ACTIVE)

fun StoreJpaRepository.findAllActiveStoreByZoneId(zoneId: String?): List<StoreEntity> =
    findAllBy(zoneId, EntityStatus.ACTIVE)




