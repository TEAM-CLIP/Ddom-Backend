package com.clip.application.user.vo

import com.clip.domain.user.enums.SocialLoginProvider

class UserClaims {
    data class Register(
        val socialId: String,
        val socialLoginProvider: SocialLoginProvider,
        val email: String
    )

    data class Access(
        val userId: String,
    )

    data class Refresh(
        val userId: String,
    )
}
