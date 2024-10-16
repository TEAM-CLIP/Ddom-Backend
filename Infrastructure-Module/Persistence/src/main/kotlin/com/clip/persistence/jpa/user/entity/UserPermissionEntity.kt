package com.clip.persistence.jpa.user.entity

import com.clip.persistence.jpa.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user_permission")
class UserPermissionEntity(
    val servicePermission: Boolean,
    val privatePermission: Boolean,
    val marketingPermission: Boolean,
    val alarmPermission: Boolean,
    val cameraPermission: Boolean,
    val locationPermission: Boolean,
) : BaseEntity()
