package com.clip.application.user.port.out

import com.clip.domain.user.entity.UserToken

interface UserTokenManagementPort {

    fun isExistsToken(token: String): Boolean

    fun saveUserToken(userToken: UserToken): UserToken

    fun deleteUserToken(token: String)


}