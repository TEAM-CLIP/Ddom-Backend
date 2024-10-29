package com.clip.persistence.jpa.store.entity

import com.clip.domain.store.enums.Storetype
import com.clip.persistence.jpa.common.AggregateRoot
import com.clip.persistence.jpa.common.EntityStatus
import jakarta.persistence.*

@Entity
@Table(name = "category")
class CategoryEntity(
    id: String,
    type: Storetype
) : AggregateRoot<CategoryEntity>(id) {

    @Enumerated(EnumType.STRING)
    @Column(
        name = "type",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    val type: Storetype = type

    @Enumerated(EnumType.STRING)
    @Column(
        name = "entity_status",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    var entityStatus: EntityStatus = EntityStatus.ACTIVE
}