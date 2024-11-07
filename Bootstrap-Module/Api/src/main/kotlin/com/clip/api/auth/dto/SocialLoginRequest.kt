package com.clip.api.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "소셜 로그인 요청")
data class SocialLoginRequest(
    @Schema(description = "oauth access token")
    val accessToken: String,

    @Schema(description = "사용자 이메일 (애플 로그인 시 필수)")
    val email: String?
)
