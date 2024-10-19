package com.clip.bootstrap.auth.dto

data class ReissueResponse(
    val accessToken: String,
    val refreshToken: String
) {
}