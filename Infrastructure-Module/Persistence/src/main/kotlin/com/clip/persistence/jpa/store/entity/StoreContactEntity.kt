package com.clip.persistence.jpa.store.entity

import com.clip.domain.store.enums.StoreContactType
import com.clip.persistence.jpa.common.BaseEntity
import com.clip.persistence.jpa.common.EntityStatus
import jakarta.persistence.*

@Entity
@Table(name = "store_contact")
class StoreContactEntity(
    val storeId: String,
    contactType: StoreContactType,
    value: String
) : BaseEntity() {

    @Enumerated(EnumType.STRING)
    @Column(
        name = "contact_type",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    val contactType: StoreContactType = contactType

    @Column(
        name = "value",
        nullable = false,
        columnDefinition = "varchar(255)",
    )
    val value: String = value

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", insertable = false, updatable = false)
    lateinit var store: StoreEntity

    @Enumerated(EnumType.STRING)
    @Column(
        name = "entity_status",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    var entityStatus: EntityStatus = EntityStatus.ACTIVE
}