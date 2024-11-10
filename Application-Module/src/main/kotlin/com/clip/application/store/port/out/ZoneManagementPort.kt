package com.clip.application.store.port.out

import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.Zone

interface ZoneManagementPort {
    fun getBy(id: DomainId): Zone
    fun getAllZones(): List<Zone>
}