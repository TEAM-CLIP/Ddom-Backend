package com.clip.application.user.port.`in`

interface VerifyUserNicknameUsecase {

    fun verifyNickname(command: Command): Response

    data class Command(
        val nickName: String
    )

    data class Response(
        val isDuplicated: Boolean
    )

}