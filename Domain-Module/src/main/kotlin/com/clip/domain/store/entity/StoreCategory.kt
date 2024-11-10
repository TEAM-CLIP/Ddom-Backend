package com.clip.domain.store.entity

import com.clip.domain.common.Aggregate
import com.clip.domain.common.DomainId
import com.clip.domain.store.enums.StoreType

class StoreCategory(
    id : DomainId,
    val type : StoreType
) : Aggregate<StoreCategory>(id) {
}