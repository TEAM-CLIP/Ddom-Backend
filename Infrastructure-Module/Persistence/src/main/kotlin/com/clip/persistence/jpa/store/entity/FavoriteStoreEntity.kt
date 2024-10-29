package com.clip.persistence.jpa.store.entity

import com.clip.persistence.jpa.common.BaseEntity
import com.clip.persistence.jpa.common.EntityStatus
import com.clip.persistence.jpa.user.entity.UserEntity
import jakarta.persistence.*

@Entity
@Table(name = "favorite_store")
class FavoriteStoreEntity(
    storeId: String,
    userId: String,
    var visitedCnt: Int
) : BaseEntity() {

    @Column(nullable = false)
    val storeId: String = storeId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", insertable = false, updatable = false)
    lateinit var store: StoreEntity

    @Column(nullable = false)
    val userId: String = userId

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    lateinit var user: UserEntity

    @Enumerated(EnumType.STRING)
    @Column(
        name = "entity_status",
        nullable = false,
        columnDefinition = "varchar(20)",
    )
    var entityStatus: EntityStatus = EntityStatus.ACTIVE
}