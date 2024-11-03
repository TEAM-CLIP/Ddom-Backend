package com.clip.api.store.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "가게 리스트 확인 응답")
data class GetAllStoreResponse(
    @Schema(description = "입점된 가게 리스트")
    val registered: List<RegisteredStoreResponse> = emptyList(),
    @Schema(description = "미입점 가게 리스트")
    val unregistered: List<UnregisteredStoreResponse> = emptyList()
) {
    @Schema(description = "입점된 가게 정보")
    data class RegisteredStoreResponse(
        @Schema(description = "가게 ID")
        val storeId: String,
        @Schema(description = "가게 이름")
        val storeName: String,
        @Schema(description = "가게 이미지 URL")
        val storeImgUrl: String,
        @Schema(description = "가게 단골 수")
        val favoriteUserCount: Long,
        @Schema(description = "가게 단골 지정 여부")
        val isFavorited: Boolean,
        @Schema(description = "가게 타입")
        val storeType: String,
        @Schema(description = "할인 정책")
        val discountPolicy: List<DiscountPolicyResponse> = emptyList()
    ) {
        @Schema(description = "할인 정책")
        data class DiscountPolicyResponse(
            @Schema(description = "할인 타입")
            val discountType: String?,
            @Schema(description = "할인 설명")
            val discountDescription: String?
        )
    }

    @Schema(description = "입점된 가게 정보")
    data class UnregisteredStoreResponse(
        @Schema(description = "가게 ID")
        val storeId: String,
        @Schema(description = "가게 이름")
        val storeName: String,
        @Schema(description = "가게 이미지 URL")
        val storeImgUrl: String,
        @Schema(description = "가게 타입")
        val storeType: String
    ) {
    }

}