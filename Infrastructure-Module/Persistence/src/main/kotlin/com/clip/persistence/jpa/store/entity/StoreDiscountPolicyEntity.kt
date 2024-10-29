package com.clip.persistence.jpa.store.entity

import com.clip.domain.store.enums.DiscountPolicyMethod
import com.clip.persistence.jpa.common.BaseEntity
import com.clip.persistence.jpa.common.EntityStatus
import jakarta.persistence.*

@Entity
@Table(name = "store_discount_policy")
class StoreDiscountPolicyEntity(
    title : String,
    method : DiscountPolicyMethod,
    storeId : String
): BaseEntity() {

    @Column(nullable = false, length = 255)
    val title: String = title

    @Enumerated(EnumType.STRING)
    @Column(
        name = "method",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    val method: DiscountPolicyMethod = method

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