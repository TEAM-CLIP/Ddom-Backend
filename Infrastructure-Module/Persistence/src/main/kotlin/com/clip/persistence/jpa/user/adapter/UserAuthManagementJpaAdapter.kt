package com.clip.persistence.jpa.user.adapter

import com.clip.application.user.port.out.UserAuthManagementPort
import com.clip.domain.user.entity.UserAuth
import com.clip.domain.user.enums.SocialLoginProvider
import com.clip.persistence.jpa.user.UserMapper
import com.clip.persistence.jpa.user.repository.UserAuthJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserAuthManagementJpaAdapter(
    private val userAuthJpaRepository: UserAuthJpaRepository,
) : UserAuthManagementPort {

    override fun getUserAuth(
        socialId: String,
        socialLoginProvider: SocialLoginProvider,
    ): UserAuth? =
        userAuthJpaRepository
            .findBySocialIdAndSocialLoginProvider(socialId, socialLoginProvider.name)
            ?.let { UserMapper.toUserAuth(it) }

    override fun isExistsUserAuth(
        socialId: String,
        socialLoginProvider: SocialLoginProvider,
    ): Boolean = userAuthJpaRepository.existsBySocialIdAndSocialLoginProvider(socialId, socialLoginProvider.name)

    override fun saveUserAuth(userAuth: UserAuth): UserAuth {
        val userAuthEntity = UserMapper.toUserAuthEntity(userAuth)
        userAuthJpaRepository.save(userAuthEntity)
        return userAuth
    }

}
