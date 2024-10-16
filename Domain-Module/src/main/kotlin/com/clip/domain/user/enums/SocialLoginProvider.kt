package com.clip.domain.user.enums

import com.clip.common.exception.DefaultException

enum class SocialLoginProvider {
    KAKAO,
    APPLE
    ;

    companion object{
        fun parse(value: String): SocialLoginProvider {
            return when (value) {
                entries.firstOrNull { it.name == value }?.name -> valueOf(value)
                else -> throw DefaultException.InvalidArgumentException("유효하지 않은 소셜 로그인 제공자입니다.")
            }
        }
    }


}