package com.clip.admin.dto

data class UpdateContactRequest(
    val contactType: String,
    val value: String
) {
}