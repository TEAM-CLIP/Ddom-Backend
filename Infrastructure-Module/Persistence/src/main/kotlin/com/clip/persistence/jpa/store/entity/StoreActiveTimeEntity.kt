package com.clip.persistence.jpa.store.entity

import com.clip.persistence.jpa.common.BaseEntity
import com.clip.persistence.jpa.common.EntityStatus
import jakarta.persistence.*
import java.time.DayOfWeek
import java.time.LocalTime

@Entity
@Table(name = "store_active_time")
class StoreActiveTimeEntity(
    storeId: String,
    dayOfWeek: DayOfWeek,
    startAt: LocalTime,
    endAt: LocalTime,
    isActive : Boolean
) : BaseEntity() {

    @Enumerated(EnumType.STRING)
    @Column(
        name = "dayOfWeek",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    val dayOfWeek: DayOfWeek = dayOfWeek

    @Column(nullable = false)
    val startAt: LocalTime = startAt

    @Column(nullable = false)
    val endAt: LocalTime = endAt

    @Column(nullable = false)
    val isActive: Boolean = isActive

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