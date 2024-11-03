package com.clip.persistence.jpa.store.repository

import com.clip.persistence.jpa.common.EntityStatus
import com.clip.persistence.jpa.store.entity.StoreCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface StoreCategoryJpaRepository : JpaRepository<StoreCategoryEntity, String> {
    @Query(
        """
        SELECT s
        FROM StoreCategoryEntity s
        WHERE s.id in :storeCategoryIds
        AND s.entityStatus = :entityStatus
    """,
    )
    fun findAllBy(
        storeCategoryIds: List<String>,
        entityStatus: EntityStatus,
    ): List<StoreCategoryEntity>
}

fun StoreCategoryJpaRepository.findAllActiveStoreCategoryByIds(ids: List<String>): List<StoreCategoryEntity> =
    findAllBy(ids, EntityStatus.ACTIVE)