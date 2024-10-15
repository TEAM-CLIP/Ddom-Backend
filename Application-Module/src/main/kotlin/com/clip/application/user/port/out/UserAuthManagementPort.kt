package com.clip.application.user.port.out

import com.clip.domain.user.entity.UserAuth
import com.clip.domain.user.enums.SocialLoginProvider

interface UserAuthManagementPort {

    fun getUserAuth(
        socialId: String,
        socialLoginProvider: SocialLoginProvider
    ): UserAuth?


    fun isExistsUserAuth(
        socialId: String,
        socialLoginProvider: SocialLoginProvider
    ): Boolean


    fun saveUserAuth(
        userAuth: UserAuth
    ): UserAuth

}