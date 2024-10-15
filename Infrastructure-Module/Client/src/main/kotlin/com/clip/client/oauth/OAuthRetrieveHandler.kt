package com.clip.client.oauth

interface OAuthRetrieveHandler {
    fun getOAuthInfo(request: OAuthRequest): OAuthResponse

    data class OAuthRequest(
        val accessToken: String,
    )

    data class OAuthResponse(
        val socialId: String,
        val email: String,
    )
}
