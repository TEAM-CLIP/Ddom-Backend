package com.clip.bootstrap.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "닉네임 중복 확인 요청")
data class NicknameVerifyRequest(
    @Schema(description = "닉네임")
    val nickname: String
)
