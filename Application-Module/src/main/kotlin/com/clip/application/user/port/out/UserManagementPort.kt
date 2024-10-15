package com.clip.application.user.port.out

import com.clip.application.user.exception.UserException
import com.clip.domain.common.DomainId
import com.clip.domain.user.entity.User

interface UserManagementPort {
    fun saveUser(user: User): User

    fun getUser(userId: DomainId): User?

    @Throws(UserException.UserNotFoundException::class)
    fun getUserNotNull(userId: DomainId): User

    fun isExistsNickname(nickName: String): Boolean
}