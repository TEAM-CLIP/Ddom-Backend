package com.clip.admin.dto

data class CreateStoreRequest(
    val name: String,
    val imgUrl: String,
    val introduction: String,
    val isRegistered: Boolean,
    val longitude: Double,
    val latitude: Double,
    val storeCategoryId: String,
) {
}