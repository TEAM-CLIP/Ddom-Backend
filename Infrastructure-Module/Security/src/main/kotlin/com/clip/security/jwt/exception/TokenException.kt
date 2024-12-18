package com.clip.security.jwt.exception

import com.clip.common.exception.BusinessException

sealed class TokenException(
    codePrefix: String = CODE_PREFIX,
    errorCode: Int,
    httpStatus: Int = 401,
    message: String
): BusinessException(codePrefix, errorCode, httpStatus, message) {

    class InvalidTokenException(
        message : String = "유효하지 않은 토큰입니다."
    ): TokenException(
        errorCode = 1,
        message = message
    )

    class ExpiredTokenException(
        message : String = "만료된 토큰입니다."
    ): TokenException(
        errorCode = 2,
        message = message
    )

    companion object{
        const val CODE_PREFIX = "TOKEN"
    }
}