package com.clip.persistence.jpa.user.repository

import com.clip.persistence.jpa.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, String> {

    fun existsByNickname (nickName: String): Boolean
}
