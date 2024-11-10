package com.clip.persistence.jpa.store

import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.Zone
import com.clip.domain.store.vo.StoreInfo
import com.clip.domain.store.vo.StorePlace
import com.clip.persistence.jpa.store.entity.ZoneEntity

object ZoneMapper {

    fun toZone(zone: ZoneEntity): Zone {
        return Zone(
            id = DomainId(zone.id),
            name = zone.name,
            boundary = zone.boundary,
        )
    }
}