package com.clip.persistence.jpa.user.adapter

import com.clip.application.user.port.out.UserTokenManagementPort
import com.clip.domain.user.entity.UserToken
import com.clip.persistence.jpa.user.UserMapper
import com.clip.persistence.jpa.user.repository.UserTokenJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserTokenManagementJpaAdapter(
    private val userTokenJpaRepository: UserTokenJpaRepository,
) : UserTokenManagementPort {
    override fun isExistsToken(token: String): Boolean = userTokenJpaRepository.existsByToken(token)

    override fun saveUserToken(userToken: UserToken): UserToken {
        val userTokenEntity = UserMapper.toUserTokenEntity(userToken)
        userTokenJpaRepository.save(userTokenEntity)
        return userToken
    }

    override fun deleteUserToken(token: String) {
        userTokenJpaRepository.deleteByToken(token)
    }
}
