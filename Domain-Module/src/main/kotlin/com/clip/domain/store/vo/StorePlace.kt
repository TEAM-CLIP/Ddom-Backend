package com.clip.domain.store.vo

import com.clip.domain.common.DomainId

data class StorePlace(
    val longitude: Double,
    val latitude: Double,
    val zoneId: DomainId
)