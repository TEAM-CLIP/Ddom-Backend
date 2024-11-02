package com.clip.admin.dto

data class GetStoreContactInfoResponse(
    val id: String,
    val contactType: String,
    val value: String
) {
}