package com.clip.application.user.service

import com.clip.application.user.port.`in`.VerifyNicknameUsecase
import com.clip.application.user.port.out.UserManagementPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VerifyNicknameService(
    private val userManagementPort: UserManagementPort
) : VerifyNicknameUsecase{

    override fun verifyNickname(command: VerifyNicknameUsecase.Command): VerifyNicknameUsecase.Response {
        val isDuplicated = userManagementPort.isExistsNickname(command.nickName)
        return VerifyNicknameUsecase.Response(
            isDuplicated = isDuplicated
        )
    }

}