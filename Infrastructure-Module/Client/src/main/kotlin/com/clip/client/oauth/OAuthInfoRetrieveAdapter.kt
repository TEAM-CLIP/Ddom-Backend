package com.clip.client.oauth

import com.clip.application.user.exception.UserException
import com.clip.application.user.port.out.AuthInfoRetrievePort
import com.clip.application.user.vo.AuthInfo
import com.clip.client.oauth.exception.OAuthException
import com.clip.domain.user.enums.SocialLoginProvider
import org.springframework.stereotype.Component

@Component
class OAuthInfoRetrieveAdapter(
    private val oAuthRetrieveHandlers: Map<SocialLoginProvider, OAuthRetrieveHandler>,
) : AuthInfoRetrievePort {
    override fun getAuthInfo(
        provider: SocialLoginProvider,
        accessToken: String,
    ): AuthInfo {
        try {
            val oAuthResponse =
                oAuthRetrieveHandlers[provider]?.getOAuthInfo(OAuthRetrieveHandler.OAuthRequest(accessToken))
                    ?: throw OAuthException.OAuthRetrieveFailedException("OAuth 정보를 가져오는 핸들러가 존재하지 않습니다.")
            return AuthInfo(
                socialLoginProvider = provider,
                socialId = oAuthResponse.socialId,
                email = oAuthResponse.email,
            )
        } catch (e: OAuthException) {
            throw UserException.UserAuthNotFoundException("OAuth 정보를 가져오는데 실패했습니다. 에러 메시지: ${e.message}")
        }
    }
}
