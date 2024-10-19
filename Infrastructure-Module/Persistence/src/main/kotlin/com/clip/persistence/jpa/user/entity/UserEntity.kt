package com.clip.persistence.jpa.user.entity

import com.clip.persistence.jpa.common.AggregateRoot
import jakarta.persistence.*

@Entity
@Table(name = "user")
class UserEntity(
    id: String,
    nickname: String,
    email: String,
    phoneNumber: String,
    userPermission: UserPermissionEntity,
) : AggregateRoot<UserEntity>(id) {
    @Column(nullable = false)
    val nickname: String = nickname

    @Column(
        columnDefinition = "varchar(100)",
    )
    val email: String = email

    val phoneNumber: String = phoneNumber

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val userPermission: UserPermissionEntity = userPermission

}
