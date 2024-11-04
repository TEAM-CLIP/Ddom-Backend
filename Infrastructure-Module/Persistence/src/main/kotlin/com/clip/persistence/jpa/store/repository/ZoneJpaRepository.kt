package com.clip.persistence.jpa.store.repository

import com.clip.persistence.jpa.common.EntityStatus
import com.clip.persistence.jpa.store.entity.ZoneEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ZoneJpaRepository : JpaRepository<ZoneEntity, String> {

    @Query
        ("""
            SELECT z
            FROM ZoneEntity z
            WHERE z.entityStatus = :entityStatus
            """)
    fun findAllBy(entityStatus: EntityStatus): List<ZoneEntity>
}
fun ZoneJpaRepository.findAllActiveZone(): List<ZoneEntity> =
    findAllBy(EntityStatus.ACTIVE)
