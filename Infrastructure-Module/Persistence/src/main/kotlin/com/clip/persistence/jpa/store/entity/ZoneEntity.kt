package com.clip.persistence.jpa.store.entity

import com.clip.persistence.jpa.common.AggregateRoot
import com.clip.persistence.jpa.common.EntityStatus
import jakarta.persistence.*

@Entity
@Table(name = "zone")
class ZoneEntity(
    id: String,
    name: String,
    boundary: String
) : AggregateRoot<ZoneEntity>(id) {

    @Column(nullable = false, length = 100)
    var name: String = name

    @Column(nullable = false)
    var boundary: String = boundary

    @Enumerated(EnumType.STRING)
    @Column(
        name = "entity_status",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    var entityStatus: EntityStatus = EntityStatus.ACTIVE
}