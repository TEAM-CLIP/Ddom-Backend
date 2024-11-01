package com.clip.api.auth.dto

data class ReissueResponse(
    val accessToken: String,
    val refreshToken: String
) {
}