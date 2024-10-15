package com.clip.persistence.jpa.user.adapter

import com.clip.application.user.exception.UserException
import com.clip.application.user.port.out.UserManagementPort
import com.clip.domain.common.DomainId
import com.clip.domain.user.entity.User
import com.clip.persistence.jpa.user.UserMapper
import com.clip.persistence.jpa.user.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class UserManagementJpaAdapter(
    private val userJpaRepository: UserJpaRepository,
) : UserManagementPort {
    override fun saveUser(user: User): User {
        val userEntity = UserMapper.toUserEntity(user)
        userJpaRepository.save(userEntity)
        return user
    }

    override fun getUser(userId: DomainId): User? = userJpaRepository.findByIdOrNull(userId.value)?.let { UserMapper.toUser(it) }

    override fun getUserNotNull(userId: DomainId): User =
        userJpaRepository.findByIdOrNull(userId.value)?.let { UserMapper.toUser(it) }
            ?: throw UserException.UserNotFoundException()

    override fun isExistsNickname(nickName: String): Boolean {
        return userJpaRepository.existsByNickname(nickName)
    }
}
