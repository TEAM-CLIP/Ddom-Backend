package com.clip.api.user.controller

import com.clip.application.user.port.`in`.RegisterUserUsecase
import com.clip.application.user.port.`in`.VerifyUserNicknameUsecase
import com.clip.api.user.api.UserApi
import com.clip.api.user.dto.NicknameVerifyRequest
import com.clip.api.user.dto.NicknameVerifyResponse
import com.clip.api.user.dto.RegisterUserRequest
import com.clip.api.user.dto.RegisterUserResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val registerUserUsecase: RegisterUserUsecase,
    private val verifyUserNicknameUsecase: VerifyUserNicknameUsecase
) : UserApi {

    override fun registerUser(request: RegisterUserRequest): RegisterUserResponse {
        val response = registerUserUsecase.registerUser(
            RegisterUserUsecase.Command(
                request.registerToken,
                request.servicePermission,
                request.privatePermission,
                request.advertisingPermission,
                request.marketingPermission,
                request.nickname,
                request.phoneNumber
            )
        )
        return RegisterUserResponse(response.accessToken, response.refreshToken)
    }

    override fun verifyNickname(request: NicknameVerifyRequest): NicknameVerifyResponse {
        val response = verifyUserNicknameUsecase.verifyNickname(
            VerifyUserNicknameUsecase.Command(
                request.nickname
            )
        )
        return NicknameVerifyResponse(response.isDuplicated)
    }
}