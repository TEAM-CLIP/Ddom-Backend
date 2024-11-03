package com.clip.persistence.jpa.store.entity

import com.clip.persistence.jpa.common.AggregateRoot
import com.clip.persistence.jpa.common.EntityStatus
import jakarta.persistence.*

@Entity
@Table(name = "store")
class StoreEntity(
    id: String,
    name: String,
    imgUrl: String,
    introduction: String,
    isRegistered: Boolean,
    longitude: Double,
    latitude: Double,
    categoryId: String,
    zoneId: String
) : AggregateRoot<StoreEntity>(id) {

    @Column(nullable = false)
    var name: String = name

    var imgUrl: String = imgUrl

    @Column(length = 500)
    var introduction: String = introduction

    @Column(nullable = false)
    var isRegistered: Boolean = isRegistered

    @Column(nullable = false)
    var longitude: Double = longitude

    @Column(nullable = false)
    var latitude: Double = latitude

    @Column(nullable = false)
    var categoryId: String = categoryId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", insertable = false, updatable = false)
    lateinit var category: StoreCategoryEntity

    @Column(nullable = false)
    var zoneId: String = zoneId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zoneId", insertable = false, updatable = false)
    lateinit var zone: ZoneEntity

    @Enumerated(EnumType.STRING)
    @Column(
        name = "entity_status",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    var entityStatus: EntityStatus = EntityStatus.ACTIVE
}