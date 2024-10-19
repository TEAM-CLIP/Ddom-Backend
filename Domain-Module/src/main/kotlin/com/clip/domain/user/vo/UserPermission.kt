package com.clip.domain.user.vo

import com.clip.common.exception.DefaultException

data class UserPermission(
    val servicePermission: Boolean,
    val privatePermission: Boolean,
    val advertisingPermission: Boolean,
    val marketingPermission: Boolean,
    val alarmPermission: Boolean,
    val cameraPermission: Boolean,
    val locationPermission: Boolean,
) {
    init {
        validate()
    }

    private fun validate() {
        if (!servicePermission) {
            throw DefaultException.InvalidDefaultException("서비스 이용약관에 동의해야 합니다.")
        }
        if (!privatePermission) {
            throw DefaultException.InvalidDefaultException("개인정보 수집 및 이용에 동의해야 합니다.")
        }
    }
}
