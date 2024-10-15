package com.clip.application.user.port.out

import com.clip.application.user.vo.UserClaims
import com.clip.domain.user.entity.User

interface UserTokenConvertPort {
    fun resolveRegisterToken(token: String): UserClaims.Register

    fun generateRegisterToken(
        socialId: String,
        socialLoginProvider: String,
        email: String,
    ): String

    fun generateAccessToken(user: User): String

    fun resolveAccessToken(token: String): UserClaims.Access

    fun generateRefreshToken(user: User): String

    fun resolveRefreshToken(token: String): UserClaims.Refresh
}
