package com.clip.admin.dto

data class GetStoreInfoResponse(
    val id: String,
    val name: String,
    val region: String,
    val isPartner: Boolean,
) {
}