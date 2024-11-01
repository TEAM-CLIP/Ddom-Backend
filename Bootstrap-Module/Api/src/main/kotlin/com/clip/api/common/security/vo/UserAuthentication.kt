package com.clip.api.common.security.vo

import com.clip.common.security.Authentication

class UserAuthentication(
    val userId: String
):Authentication<String> {
    override fun getDetails(): String {
        return userId
    }

    override fun getName(): String {
        return userId
    }

}