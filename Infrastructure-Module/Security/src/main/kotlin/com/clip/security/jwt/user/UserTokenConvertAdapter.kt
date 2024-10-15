package com.clip.security.jwt.user

import com.clip.application.user.port.out.UserTokenConvertPort
import com.clip.application.user.vo.UserClaims
import com.clip.domain.user.entity.User
import com.clip.domain.user.enums.SocialLoginProvider
import com.clip.security.jwt.JwtClaims
import com.clip.security.jwt.JwtPayload
import com.clip.security.jwt.JwtProvider
import com.clip.security.jwt.JwtProvider.resolveToken
import com.clip.security.jwt.exception.TokenException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.stereotype.Component

@Component
class UserTokenConvertAdapter(
    private val userJwtProperties: UserJwtProperties,
) : UserTokenConvertPort {

    override fun resolveRegisterToken(token: String): UserClaims.Register {
        val jwtPayload: JwtPayload<UserRegisterJwtClaims> =
            resolveToken { resolveToken(token, userJwtProperties.secret) }
        val jwtClaims = jwtPayload.claims
        return UserClaims.Register(
            socialId = jwtClaims.socialId,
            socialLoginProvider = jwtClaims.socialLoginProvider,
            email = jwtClaims.email,
        )
    }

    override fun generateRegisterToken(
        socialId: String,
        socialLoginProvider: String,
        email: String,
    ): String {
        val jwtClaims =
            UserRegisterJwtClaims(
                socialId = socialId,
                socialLoginProvider = SocialLoginProvider.parse(socialLoginProvider),
                email = email
            )
        val payload = getDefaultPayload(jwtClaims, UserJwtProperties.REGISTER_TOKEN_EXPIRE_TIME)
        return JwtProvider.createToken(payload, userJwtProperties.secret)
    }

    override fun generateAccessToken(user: User): String {
        val jwtClaims =
            UserJwtClaims(
                userId = user.id.value,
                tokenType = TokenType.ACCESS,
            )
        val payload = getDefaultPayload(jwtClaims, UserJwtProperties.ACCESS_TOKEN_EXPIRE_TIME)
        return JwtProvider.createToken(payload, userJwtProperties.secret)
    }

    override fun resolveAccessToken(token: String): UserClaims.Access {
        val jwtPayload: JwtPayload<UserJwtClaims> =
            TokenType.ACCESS.resolveUserToken {
                resolveToken(token, userJwtProperties.secret)
            }
        val jwtClaims = jwtPayload.claims
        return UserClaims.Access(
            userId = jwtClaims.userId,
        )
    }

    override fun generateRefreshToken(user: User): String {
        val jwtClaims =
             UserJwtClaims(
                userId = user.id.value,
                tokenType = TokenType.REFRESH,
            )
        val payload = getDefaultPayload(jwtClaims, UserJwtProperties.REFRESH_TOKEN_EXPIRE_TIME)
        return JwtProvider.createToken(payload, userJwtProperties.secret)
    }

    override fun resolveRefreshToken(token: String): UserClaims.Refresh {
        val jwtPayload: JwtPayload<UserJwtClaims> =
            TokenType.REFRESH.resolveUserToken {
                resolveToken(token, userJwtProperties.secret)
            }
        val jwtClaims = jwtPayload.claims
        return UserClaims.Refresh(
            userId = jwtClaims.userId,
        )
    }

    private fun <T : JwtClaims> getDefaultPayload(
        jwtClaims: T,
        expireTime: Long,
    ): JwtPayload<T> =
        JwtPayload(
            issuer = UserJwtProperties.ISSUER,
            subject = UserJwtProperties.SUBJECT,
            expireTime = expireTime,
            claims = jwtClaims,
        )

    private fun <T : JwtClaims> resolveToken(resolve: () -> JwtPayload<T>): JwtPayload<T> {
        try {
            return resolve()
        } catch (e: Exception) {
            when (e) {
                is MalformedJwtException -> throw TokenException.InvalidTokenException()
                is ExpiredJwtException -> throw TokenException.ExpiredTokenException()
                else -> throw e
            }
        }
    }

    private fun TokenType.resolveUserToken(resolve: () -> JwtPayload<UserJwtClaims>): JwtPayload<UserJwtClaims> {
        return resolveToken {
            val jwtPayload = resolve()
            if (jwtPayload.claims.equalsTokenType(this).not()) {
                throw TokenException.InvalidTokenException("요청 토큰 타입이 올바르지 않습니다.")
            }
            return@resolveToken jwtPayload
        }
    }

}
