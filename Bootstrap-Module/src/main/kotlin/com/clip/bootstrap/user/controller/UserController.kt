package com.clip.bootstrap.user.controller

import com.clip.application.user.port.`in`.RegisterUserUsecase
import com.clip.application.user.port.`in`.VerifyNicknameUsecase
import com.clip.bootstrap.user.api.UserApi
import com.clip.bootstrap.user.dto.NicknameVerifyRequest
import com.clip.bootstrap.user.dto.NicknameVerifyResponse
import com.clip.bootstrap.user.dto.RegisterUserRequest
import com.clip.bootstrap.user.dto.RegisterUserResponse
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val registerUserUsecase: RegisterUserUsecase,
    private val verifyNicknameUsecase: VerifyNicknameUsecase
) : UserApi {

    override fun registerUser(request: RegisterUserRequest): RegisterUserResponse {
        val response = registerUserUsecase.registerUser(
            RegisterUserUsecase.Command(
                request.registerToken,
                request.servicePermission,
                request.privatePermission,
                request.advertisingPermission,
                request.marketingPermission,
                request.alarmPermission,
                request.cameraPermission,
                request.locationPermission,
                request.nickname,
                request.phoneNumber
            )
        )
        return RegisterUserResponse(response.accessToken, response.refreshToken)
    }

    override fun verifyNickname(request: NicknameVerifyRequest): NicknameVerifyResponse {
        val response = verifyNicknameUsecase.verifyNickname(
            VerifyNicknameUsecase.Command(
                request.nickname
            )
        )
        return NicknameVerifyResponse(response.isDuplicated)
    }
}