package com.clip.client.oauth.platform

import com.clip.client.oauth.OAuthRetrieveHandler
import com.clip.client.oauth.exception.OAuthException
import com.clip.security.jwt.JwtProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.math.BigInteger
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.Base64

@Component
class AppleOAuthRetrieveHandler(
    @Qualifier("appleWebClient") private val appleWebClient: WebClient,
    private val objectMapper: ObjectMapper
) : OAuthRetrieveHandler {

    companion object {
        private const val APPLE_ISS = "https://appleid.apple.com"
    }

    override fun getOAuthInfo(request: OAuthRetrieveHandler.OAuthRequest): OAuthRetrieveHandler.OAuthResponse {
        val kid = getHeaderKid(request.accessToken)
        val key = verifyIdTokenAndGetKey(getApplePublicKeys(), kid).getRSAPublicKey()
        val claims = JwtProvider.extractClaimsByKey(request.accessToken, key, APPLE_ISS, "sub")

        return OAuthRetrieveHandler.OAuthResponse(
            socialId = claims["sub"].toString()
        )
    }

    private fun getApplePublicKeys(): AppleKeys = appleWebClient.get()
        .uri("/auth/keys")
        .retrieve()
        .onStatus({ it.isError }) {
            throw OAuthException.OAuthRetrieveFailedException("애플 공개 키를 가져오는 중 HTTP 에러가 발생했습니다: ${it.statusCode()}")
        }
        .bodyToMono(AppleKeys::class.java)
        .block() ?: throw OAuthException.OAuthRetrieveFailedException("애플 공개 키를 가져오는데 실패했습니다.")

    private fun verifyIdTokenAndGetKey(appleKeys: AppleKeys, kid: String) =
        appleKeys.keys.find { it.kid == kid }
            ?: throw OAuthException.OAuthRetrieveFailedException("Apple 공개 키에서 kid: $kid 를 찾을 수 없습니다.")

    private fun getHeaderKid(idToken: String) = String(
        Base64.getUrlDecoder().decode(idToken.split(".")[0])
    ).let { headerJson ->
        objectMapper.readValue<Map<String, Any>>(headerJson)["kid"] as? String
            ?: throw OAuthException.OAuthRetrieveFailedException("ID Token 헤더에 kid 값이 없습니다.")
    }

    private fun AppleKeys.PubKey.getRSAPublicKey(): PublicKey =
        KeyFactory.getInstance("RSA").generatePublic(
            RSAPublicKeySpec(
                BigInteger(1, Base64.getUrlDecoder().decode(n)),
                BigInteger(1, Base64.getUrlDecoder().decode(e))
            )
        )

    data class AppleKeys(val keys: List<PubKey>) {
        data class PubKey(
            val alg: String,
            val e: String,
            val kid: String,
            val kty: String,
            val n: String,
            val use: String
        )
    }
}
