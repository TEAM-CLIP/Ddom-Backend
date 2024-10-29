package com.clip.bootstrap.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "닉네임 중복 확인 응답")
data class NicknameVerifyResponse(
    @Schema(description = "닉네임 중복 여부")
    val isDuplicated: Boolean
) {
}
