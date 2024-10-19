package com.clip.application.user.service

import com.clip.application.user.exception.UserException
import com.clip.application.user.port.`in`.ReissueTokenUsecase
import com.clip.application.user.port.`in`.TokenResolveUsecase
import com.clip.application.user.port.out.UserManagementPort
import com.clip.application.user.port.out.UserTokenConvertPort
import com.clip.application.user.port.out.UserTokenManagementPort
import com.clip.domain.common.DomainId
import com.clip.domain.user.entity.UserToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserTokenCommandService(
    private val userTokenConvertPort: UserTokenConvertPort,
    private val userTokenManagementPort: UserTokenManagementPort,
    private val userManagementPort: UserManagementPort,
) : TokenResolveUsecase, ReissueTokenUsecase{

    override fun resolveAccessToken(token: String): TokenResolveUsecase.Response {
        val userClaims = userTokenConvertPort.resolveAccessToken(token)
        return TokenResolveUsecase.Response(
            userId = userClaims.userId,
        )
    }
    override fun reissue(request: ReissueTokenUsecase.Command): ReissueTokenUsecase.Response {
        if (userTokenManagementPort.isExistsToken(request.refreshToken).not()) {
            throw UserException.UserPermissionDeniedException("존재하지 않는 토큰입니다.")
        }
        val userClaims = userTokenConvertPort.resolveRefreshToken(request.refreshToken)
        userTokenManagementPort.deleteUserToken(request.refreshToken)

        val user = userManagementPort.getUserNotNull(DomainId(userClaims.userId))

        val accessToken = userTokenConvertPort.generateAccessToken(user)
        val refreshToken = userTokenConvertPort.generateRefreshToken(user)
        userTokenManagementPort.saveUserToken(UserToken(userId = user.id, token = refreshToken))
        return ReissueTokenUsecase.Response(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

}