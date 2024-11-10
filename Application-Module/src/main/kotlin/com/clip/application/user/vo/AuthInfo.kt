package com.clip.application.user.vo

import com.clip.domain.user.enums.SocialLoginProvider

data class AuthInfo(
    val socialLoginProvider: SocialLoginProvider,
    val socialId: String,
    val email: String?
)
