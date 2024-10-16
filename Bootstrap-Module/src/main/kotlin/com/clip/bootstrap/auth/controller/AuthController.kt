package com.clip.bootstrap.auth.controller

import com.clip.application.user.port.`in`.SocialLoginUsecase
import com.clip.bootstrap.auth.api.AuthApi
import com.clip.bootstrap.auth.dto.SocialLoginRequest
import com.clip.bootstrap.auth.dto.SocialLoginResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val socialLoginUsecase: SocialLoginUsecase
) : AuthApi {

    override fun socialLogin(
        provider: String,
        request: SocialLoginRequest
    ): ResponseEntity<SocialLoginResponse> {
        val command = SocialLoginUsecase.Command(
            provider = provider,
            accessToken = request.accessToken
        )
        return when (val response = socialLoginUsecase.login(command)) {
            is SocialLoginUsecase.Success -> ResponseEntity.ok(
                SocialLoginResponse.Success(
                    response.accessToken,
                    response.refreshToken
                )
            )

            is SocialLoginUsecase.NonRegistered -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(SocialLoginResponse.NonRegistered(response.registerToken))
        }
    }

}