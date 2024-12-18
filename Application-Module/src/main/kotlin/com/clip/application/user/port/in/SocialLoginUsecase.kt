package com.clip.application.user.port.`in`


interface SocialLoginUsecase {

    fun login(command: Command): Response

    data class Command(
        val provider: String,
        val accessToken: String,
        val email: String? = null
    )

    sealed class Response {
    }
    data class Success(
        val accessToken: String,
        val refreshToken: String
    ) : Response()

    data class NonRegistered(
        val registerToken: String
    ) : Response()
}