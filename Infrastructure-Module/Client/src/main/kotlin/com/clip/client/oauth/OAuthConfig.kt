package com.clip.client.oauth

import com.clip.client.oauth.platform.AppleOAuthRetrieveHandler
import com.clip.client.oauth.platform.KakaoOAuthRetrieveHandler
import com.clip.domain.user.enums.SocialLoginProvider
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OAuthConfig(
    private val kakaoOAuthRetrieveHandler: KakaoOAuthRetrieveHandler,
    private val appleOAuthRetrieveHandler: AppleOAuthRetrieveHandler,
) {
    @Bean
    @Qualifier("oAuthRetrieveHandlers")
    fun oAuthRetrieveHandlers(): Map<SocialLoginProvider, OAuthRetrieveHandler> =
        mapOf(
            SocialLoginProvider.KAKAO to kakaoOAuthRetrieveHandler as OAuthRetrieveHandler,
            SocialLoginProvider.APPLE to appleOAuthRetrieveHandler as OAuthRetrieveHandler,
        )
}
