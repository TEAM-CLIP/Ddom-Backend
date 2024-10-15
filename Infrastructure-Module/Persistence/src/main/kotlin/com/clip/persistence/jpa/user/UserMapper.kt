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
            userPermission =
                UserPermissionEntity(
                    servicePermission = user.permission.servicePermission,
                    privatePermission = user.permission.privatePermission,
                    marketingPermission = user.permission.marketingPermission,
                    alarmPermission = user.permission.alarmPermission,
                    cameraPermission = user.permission.cameraPermission,
                    locationPermission = user.permission.locationPermission,
                ),
        )

    fun toUser(userEntity: UserEntity): User =
        User(
            id = DomainId(userEntity.id),
            nickname = userEntity.nickname,
            email = userEntity.email,
            permission =
                UserPermission(
                    servicePermission = userEntity.userPermission.servicePermission,
                    privatePermission = userEntity.userPermission.privatePermission,
                    marketingPermission = userEntity.userPermission.marketingPermission,
                    alarmPermission = userEntity.userPermission.alarmPermission,
                    cameraPermission = userEntity.userPermission.cameraPermission,
                    locationPermission = userEntity.userPermission.locationPermission,
                ),
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
        )

    fun toUserToken(userTokenEntity: UserTokenEntity): UserToken =
        UserToken(
            id = DomainId(userTokenEntity.id),
            token = userTokenEntity.token,
        )
}
