package com.clip.domain.store.entity

import com.clip.domain.common.DomainId

class Zone(
    id: DomainId,
    val name: String,
    val description: String?
): Aggregate<Zone>(id) {

}