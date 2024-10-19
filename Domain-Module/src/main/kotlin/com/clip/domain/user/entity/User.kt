package com.clip.domain.user.entity

import com.clip.domain.common.DomainId
import com.clip.domain.user.vo.UserPermission

data class User(
    val id: DomainId = DomainId.generate(),
    val nickname: String,
    val email: String,
    val permission: UserPermission,
    val phoneNumber: String,
)
