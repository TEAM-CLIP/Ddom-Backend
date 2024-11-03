package com.clip.domain.store.entity

import com.clip.domain.common.Aggregate
import com.clip.domain.common.DomainId
import com.clip.domain.store.enums.Storetype

class StoreCategory(
    id : DomainId,
    val type : Storetype
) : Aggregate<StoreCategory>(id) {
}