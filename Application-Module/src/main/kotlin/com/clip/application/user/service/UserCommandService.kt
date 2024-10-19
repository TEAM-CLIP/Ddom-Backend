package com.clip.application.user.service

import com.clip.application.user.exception.UserException
import com.clip.application.user.port.`in`.RegisterUserUsecase
import com.clip.application.user.port.`in`.VerifyUserNicknameUsecase
import com.clip.application.user.port.out.UserAuthManagementPort
import com.clip.application.user.port.out.UserManagementPort
import com.clip.application.user.port.out.UserTokenConvertPort
import com.clip.application.user.port.out.UserTokenManagementPort
import com.clip.domain.user.entity.User
import com.clip.domain.user.entity.UserAuth
import com.clip.domain.user.entity.UserToken
import com.clip.domain.user.vo.UserPermission
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserCommandService (
    private val userTokenConvertPort: UserTokenConvertPort,
    private val userAuthManagementPort: UserAuthManagementPort,
    private val userManagementPort: UserManagementPort,
    private val userTokenManagementPort: UserTokenManagementPort
) : RegisterUserUsecase, VerifyUserNicknameUsecase {

    override fun registerUser(command: RegisterUserUsecase.Command): RegisterUserUsecase.Response {
        if (!userTokenManagementPort.isExistsToken(command.registerToken)) {
            throw UserException.UserPermissionDeniedException("존재하지 않는 가입 토큰입니다.")
        }
        val userClaims = userTokenConvertPort.resolveRegisterToken(command.registerToken)
        if (userAuthManagementPort.isExistsUserAuth(userClaims.socialId, userClaims.socialLoginProvider)) {
            throw UserException.UserAlreadyRegisteredException()
        }
        val registerUser =
            User(
                nickname = command.nickname,
                permission =
                UserPermission(
                    command.servicePermission,
                    command.privatePermission,
                    command.advertisingPermission,
                    command.marketingPermission,
                    command.alarmPermission,
                    command.cameraPermission,
                    command.locationPermission
                ),
                email = userClaims.email,
                phoneNumber = command.phoneNumber
            )
        val userAuth =
            UserAuth(
                userId = registerUser.id,
                socialId = userClaims.socialId,
                socialLoginProvider = userClaims.socialLoginProvider,
            )

        userManagementPort.saveUser(registerUser)
        userAuthManagementPort.saveUserAuth(userAuth)

        val accessToken = userTokenConvertPort.generateAccessToken(registerUser)
        val refreshToken = userTokenConvertPort.generateRefreshToken(registerUser)

        userTokenManagementPort.saveUserToken(UserToken(userId = registerUser.id, token = refreshToken))

        return RegisterUserUsecase.Response(accessToken, refreshToken)
    }

    override fun verifyNickname(command: VerifyUserNicknameUsecase.Command): VerifyUserNicknameUsecase.Response {
        val isDuplicated = userManagementPort.isExistsNickname(command.nickName)
        return VerifyUserNicknameUsecase.Response(
            isDuplicated = isDuplicated
        )
    }
}
