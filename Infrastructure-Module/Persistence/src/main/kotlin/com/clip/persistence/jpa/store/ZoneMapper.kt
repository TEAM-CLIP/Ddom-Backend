package com.clip.persistence.jpa.store

import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.Zone
import com.clip.persistence.jpa.store.entity.ZoneEntity

object ZoneMapper {
    fun toZone(zoneEntity: ZoneEntity): Zone {
        return Zone(
            DomainId(zoneEntity.id),
            zoneEntity.name,
            zoneEntity.description
        )
    }

}