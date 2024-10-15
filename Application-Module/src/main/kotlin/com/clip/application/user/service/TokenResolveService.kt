package com.clip.application.user.service

import com.clip.application.user.port.`in`.TokenResolveUsecase
import com.clip.application.user.port.out.UserTokenConvertPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class TokenResolveService(
    private val userTokenConvertPort: UserTokenConvertPort,
) : TokenResolveUsecase {

    override fun resolveAccessToken(token: String): TokenResolveUsecase.Response {
        val userClaims = userTokenConvertPort.resolveAccessToken(token)
        return TokenResolveUsecase.Response(
            userId = userClaims.userId,
        )
    }

}
