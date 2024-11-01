package com.clip.persistence.jpa.user

import com.clip.domain.common.DomainId
import com.clip.domain.user.entity.User
import com.clip.domain.user.entity.UserAuth
import com.clip.domain.user.entity.UserToken
import com.clip.domain.user.enums.SocialLoginProvider
import com.clip.domain.user.vo.UserPermission
import com.clip.persistence.jpa.user.entity.UserAuthEntity
import com.clip.persistence.jpa.user.entity.UserEntity
import com.clip.persistence.jpa.user.entity.UserPermissionEntity
import com.clip.persistence.jpa.user.entity.UserTokenEntity

object UserMapper {
    fun toUserEntity(user: User): UserEntity =
        UserEntity(
            id = user.id.value,
            nickname = user.nickname,
            email = user.email,
            phoneNumber = user.phoneNumber,
            userPermission =
                UserPermissionEntity(
                    servicePermission = user.permission.servicePermission,
                    privatePermission = user.permission.privatePermission,
                    advertisingPermission = user.permission.advertisingPermission,
                    marketingPermission = user.permission.marketingPermission
                )
        )

    fun toUser(userEntity: UserEntity): User =
        User(
            id = DomainId(userEntity.id),
            nickname = userEntity.nickname,
            email = userEntity.email,
            phoneNumber = userEntity.phoneNumber,
            permission =
                UserPermission(
                    servicePermission = userEntity.userPermission.servicePermission,
                    privatePermission = userEntity.userPermission.privatePermission,
                    advertisingPermission = userEntity.userPermission.advertisingPermission,
                    marketingPermission = userEntity.userPermission.marketingPermission
                )
        )

    fun toUserAuthEntity(userAuth: UserAuth): UserAuthEntity =
        UserAuthEntity(
            id = userAuth.id.value,
            socialId = userAuth.socialId,
            socialLoginProvider = userAuth.socialLoginProvider.name,
            userId = userAuth.userId.value,
        )

    fun toUserAuth(userAuthEntity: UserAuthEntity): UserAuth =
        UserAuth(
            id = DomainId(userAuthEntity.id),
            socialId = userAuthEntity.socialId,
            socialLoginProvider = SocialLoginProvider.parse(userAuthEntity.socialLoginProvider),
            userId = DomainId(userAuthEntity.userId),
        )

    fun toUserTokenEntity(userToken: UserToken): UserTokenEntity =
        UserTokenEntity(
            id = userToken.id.value,
            token = userToken.token,
            userId = userToken.userId?.value,
        )

    fun toUserToken(userTokenEntity: UserTokenEntity): UserToken =
        UserToken(
            id = DomainId(userTokenEntity.id),
            token = userTokenEntity.token,
            userId = userTokenEntity.userId?.let { DomainId(it) },
        )
}
