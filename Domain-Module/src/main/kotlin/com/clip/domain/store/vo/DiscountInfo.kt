package com.clip.domain.store.vo

import com.clip.domain.common.DomainId
import com.clip.domain.store.enums.DiscountPolicyMethod

data class DiscountInfo(
    val discountId: DomainId?,
    val discountPolicyMethod: DiscountPolicyMethod?,
    val title: String?,
) {
}