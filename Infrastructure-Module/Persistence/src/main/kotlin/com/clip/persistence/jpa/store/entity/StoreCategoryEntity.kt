package com.clip.persistence.jpa.store.entity

import com.clip.domain.store.enums.StoreType
import com.clip.persistence.jpa.common.AggregateRoot
import com.clip.persistence.jpa.common.EntityStatus
import jakarta.persistence.*

@Entity
@Table(name = "store_category")
class StoreCategoryEntity(
    id: String,
    type: StoreType
) : AggregateRoot<StoreCategoryEntity>(id) {

    @Enumerated(EnumType.STRING)
    @Column(
        name = "type",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    var type: StoreType = type

    @Enumerated(EnumType.STRING)
    @Column(
        name = "entity_status",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    var entityStatus: EntityStatus = EntityStatus.ACTIVE
}