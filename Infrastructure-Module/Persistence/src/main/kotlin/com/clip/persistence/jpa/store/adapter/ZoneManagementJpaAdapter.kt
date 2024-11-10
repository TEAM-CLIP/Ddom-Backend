package com.clip.persistence.jpa.store.adapter

import com.clip.application.store.port.out.ZoneManagementPort
import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.Zone
import com.clip.persistence.jpa.store.ZoneMapper
import com.clip.persistence.jpa.store.repository.ZoneJpaRepository
import com.clip.persistence.jpa.store.repository.findAllActiveZone
import org.springframework.stereotype.Repository

@Repository
class ZoneManagementJpaAdapter(
    private val zoneJpaRepository: ZoneJpaRepository
): ZoneManagementPort {
    override fun getBy(id: DomainId): Zone {
        val zone = zoneJpaRepository.findById(id.value)
            .orElseThrow { throw IllegalStateException("Zone not found") }
        return ZoneMapper.toZone(zone)
    }

    override fun getAllZones(): List<Zone> {
        val zones = zoneJpaRepository.findAllActiveZone()
        return zones.map { ZoneMapper.toZone(it) }
    }
}