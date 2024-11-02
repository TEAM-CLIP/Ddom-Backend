package com.clip.admin.dto

data class CreateContactRequest(
    val contactType: String,
    val value: String
) {
}