package com.clip.application.user.port.out

import com.clip.application.user.exception.UserException
import com.clip.application.user.vo.AuthInfo
import com.clip.domain.user.enums.SocialLoginProvider

interface AuthInfoRetrievePort {

    @Throws(UserException.UserAuthNotFoundException::class)
    fun getAuthInfo(provider: SocialLoginProvider, accessToken: String): AuthInfo
}