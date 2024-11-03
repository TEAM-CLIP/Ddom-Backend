package com.clip.admin.dto

data class UpdateDiscountPolicyRequest(
    val discountType: String,
    val discountValue: String
) {
}