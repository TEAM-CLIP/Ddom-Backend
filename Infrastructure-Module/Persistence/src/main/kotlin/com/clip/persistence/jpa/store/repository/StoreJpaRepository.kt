package com.clip.persistence.jpa.store.repository

import com.clip.domain.store.enums.DiscountPolicyMethod
import com.clip.persistence.jpa.common.EntityStatus
import com.clip.persistence.jpa.store.entity.FavoriteStoreEntity
import com.clip.persistence.jpa.store.entity.StoreDiscountPolicyEntity
import com.clip.persistence.jpa.store.entity.StoreEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StoreJpaRepository : JpaRepository<StoreEntity, String> {
    @Query(
        """
    SELECT new kotlin.Pair(r.storeId, COUNT(r.id))
    FROM FavoriteStoreEntity r
    WHERE r.storeId IN :storeIds
    AND r.entityStatus = :entityStatus
    GROUP BY r.storeId
    """
    )
    fun countAllFavoriteBy(
        storeIds: List<String>,
        entityStatus: EntityStatus
    ): List<Pair<String, Long>>

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

    @Query(
        """
        SELECT s
        FROM StoreEntity s
        WHERE s.entityStatus = :entityStatus
    """
    )
    fun findAllBy(pageable: Pageable, entityStatus: EntityStatus): Page<StoreEntity>

    @Query(
        """
        SELECT s
        FROM StoreDiscountPolicyEntity s
        WHERE s.storeId = :storeId
        AND s.entityStatus = :entityStatus
    """
    )
    fun findAllDiscountPolicyBy(storeId: String, entityStatus: EntityStatus): List<StoreDiscountPolicyEntity>

    @Query(
        """
            SELECT r
            FROM StoreDiscountPolicyEntity r
            WHERE r.storeId IN :storeIds
            AND r.method = :discountPolicyMethod
            AND r.entityStatus = :entityStatus
        """,
    )
    fun findAllDiscountPolicyBy(
        storeIds: List<String>,
        discountPolicyMethod: DiscountPolicyMethod,
        entityStatus: EntityStatus,
    ): List<StoreDiscountPolicyEntity>

}

fun StoreJpaRepository.countAllFavoriteUser(storeIds: List<String>): List<Pair<String, Long>> =
    countAllFavoriteBy(storeIds, EntityStatus.ACTIVE)

fun StoreJpaRepository.findAllActiveBy(storeIds: List<String>, userId: String): List<FavoriteStoreEntity> =
    findAllBy(storeIds, userId, EntityStatus.ACTIVE)

fun StoreJpaRepository.findAllActiveBy(zoneId: String?): List<StoreEntity> =
    findAllBy(zoneId, EntityStatus.ACTIVE)

fun StoreJpaRepository.findAllActiveBy(pageable: Pageable): Page<StoreEntity> =
    findAllBy(pageable, EntityStatus.ACTIVE)


fun StoreJpaRepository.findAllActiveDiscountPolicyBy(
    storeIds: List<String>,
    discountPolicyMethod: DiscountPolicyMethod
): List<StoreDiscountPolicyEntity> =
    findAllDiscountPolicyBy(storeIds, discountPolicyMethod, EntityStatus.ACTIVE)


fun StoreJpaRepository.findAllActiveDiscountPolicyBy(storeId: String): List<StoreDiscountPolicyEntity> =
    findAllDiscountPolicyBy(storeId, EntityStatus.ACTIVE)


