package com.clip.persistence.jpa.store.entity

import com.clip.persistence.jpa.common.AggregateRoot
import com.clip.persistence.jpa.common.EntityStatus
import jakarta.persistence.*

@Entity
@Table(name = "store_menu")
class StoreMenuEntity(
    id : String,
    storeId: String,
    name: String,
    price: Double
) : AggregateRoot<StoreMenuEntity>(id) {

    @Column(nullable = false, length = 255)
    var name: String = name

    @Column(nullable = false)
    var price: Double = price

    @Column(nullable = false)
    val storeId: String = storeId

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