package com.clip.application.user.port.`in`

interface RegisterUserUsecase {

    fun registerUser(command: Command): Response


    data class Command(
        val registerToken: String,
        val servicePermission: Boolean,
        val privatePermission: Boolean,
        val advertisingPermission: Boolean,
        val marketingPermission: Boolean,
        val alarmPermission: Boolean,
        val cameraPermission: Boolean,
        val locationPermission: Boolean,
        val nickname: String,
        val phoneNumber: String
    )

    data class Response(
        val accessToken: String,
        val refreshToken: String
    )
}