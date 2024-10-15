package com.clip.persistence.jpa.user.repository

import com.clip.persistence.jpa.user.entity.UserAuthEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserAuthJpaRepository : JpaRepository<UserAuthEntity, String> {

    fun findBySocialIdAndSocialLoginProvider(
        socialId: String,
        socialLoginProvider: String,
    ): UserAuthEntity?

    fun existsBySocialIdAndSocialLoginProvider(
        socialId: String,
        socialLoginProvider: String,
    ): Boolean
}
