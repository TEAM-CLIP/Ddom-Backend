package com.clip.bootstrap.user.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원 가입 요청")
data class RegisterUserRequest(
    @Schema(description = "register_token, 소셜 로그인이로부터 전달받은 토큰")
    val registerToken: String,
    @Schema(description = "서비스 이용약관 동의")
    val servicePermission: Boolean,
    @Schema(description = "개인정보 수집 및 이용 동의")
    val privatePermission: Boolean,
    @Schema(description = "맞춤형 광고 및 개인정보 제공 동의")
    val advertisingPermission: Boolean,
    @Schema(description = "마케팅 정보 수신 동의")
    val marketingPermission: Boolean,
    @Schema(description = "알림 수신 동의")
    val alarmPermission: Boolean,
    @Schema(description = "카메라 권한 동의")
    val cameraPermission: Boolean,
    @Schema(description = "위치 권한 동의")
    val locationPermission: Boolean,
    @Schema(description = "닉네임")
    val nickname: String,
    @Schema(description = "핸드폰 번호")
    val phoneNumber: String
) {
}