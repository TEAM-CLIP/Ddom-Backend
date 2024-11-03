package com.clip.admin.dto

data class CreateDiscountPolicyRequest(
    val discountType: String,
    val discountValue: String
) {
}