package com.clip.admin.dto

data class GetStoreDetailInfoResponse(
    val id: String,
    val name: String,
    val imgUrl: String,
    val introduction: String,
    val isRegistered: Boolean,
    val longitude: Double,
    val latitude: Double,
    val storeCategoryId: String,
    val storeCategoryName: String
)