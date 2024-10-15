package com.clip.security.jwt.user

import com.clip.domain.user.enums.SocialLoginProvider
import com.clip.security.jwt.JwtClaims

class UserJwtClaims(
    val userId: String,
    val tokenType: TokenType,
) : JwtClaims {
    fun equalsTokenType(tokenType: TokenType): Boolean = this.tokenType == tokenType
}

class UserRegisterJwtClaims(
    val socialId: String,
    val socialLoginProvider: SocialLoginProvider,
    val email: String,
) : JwtClaims
