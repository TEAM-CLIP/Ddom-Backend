package com.clip.domain.user.entity

import com.clip.domain.common.DomainId

data class UserToken(
    val id: DomainId = DomainId.generate(),
    val userId: DomainId? = null,
    val token: String,
)
