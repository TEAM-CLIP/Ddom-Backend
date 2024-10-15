package com.clip.domain.user.entity

import com.clip.domain.common.DomainId
import com.clip.domain.user.enums.SocialLoginProvider

data class UserAuth(
    val id: DomainId = DomainId.generate(),
    val userId: DomainId,
    val socialId: String,
    val socialLoginProvider: SocialLoginProvider,
)
