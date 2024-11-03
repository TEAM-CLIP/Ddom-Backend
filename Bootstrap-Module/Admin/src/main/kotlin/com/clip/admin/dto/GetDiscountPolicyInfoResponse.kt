package com.clip.admin.dto

data class GetDiscountPolicyInfoResponse(
    val id: String,
    val discountType: String,
    val discountValue: String
) {
}