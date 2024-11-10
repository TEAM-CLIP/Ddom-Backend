package com.clip.application.store.port.`in`

import com.clip.common.paging.Page

interface GetStoreUseCase {

    fun getAll(query: GetAllQuery): GetAllResponse

    fun getStoreDetail(query: GetDetailQuery): GetDetailResponse

    fun getStorePaging(query: GetPagingQuery): Page<GetPagingResponse>

    data class GetAllQuery(
        val userId: String,
        val zoneId: String?
    )

    data class GetDetailQuery(
        val storeId: String
    )

    data class GetPagingQuery(
        val page: Int,
        val size: Int
    )

    data class GetAllResponse(
        val registeredStore: List<RegisteredStore>,
        val unregisteredStore: List<UnregisteredStore>,
    )

    data class GetDetailResponse(
        val storeId: String,
        val storeName: String,
        val storeImageUrl: String,
        val introduction: String?,
        val isRegistered: Boolean,
        val longitude: Double,
        val latitude: Double,
        val storeCategoryId: String,
        val storeCategoryName: String
    )

    data class GetPagingResponse(
        val storeId: String,
        val name: String,
        val region: String,
        val isRegistered: Boolean,
    )

    data class RegisteredStore(
        val storeId: String,
        val storeName: String,
        val storeImgUrl: String,
        val favoriteUserCount: Long,
        val isFavorited: Boolean,
        val storeType: String,
        val discountPolicy: List<DiscountPolicy>
    )

    data class DiscountPolicy(
        val discountType: String,
        val discountDescription: String
    )

    data class UnregisteredStore(
        val storeId: String,
        val storeName: String,
        val storeImgUrl: String,
        val storeType: String
    )
}