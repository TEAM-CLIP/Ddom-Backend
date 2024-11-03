package com.clip.application.store.port.`in`

interface GetStoreUseCase {

    fun getAll(query: GetAllQuery): GetAllResponse

    data class GetAllQuery(
        val userId: String,
        val zoneId: String?
    )

    data class GetAllResponse(
        val registeredStore: List<RegisteredStore>,
        val unregisteredStore: List<UnregisteredStore>,
    )

    data class RegisteredStore(
        val storeId: String,
        val storeName: String,
        val storeImgUrl: String,
        val favoriteUserCount: Long,
        val isFavorited: Boolean,
        val storeType: String,
        val discountPolicy : List<DiscountPolicy>
    )

    data class DiscountPolicy(
        val discountType: String?,
        val discountDescription: String?
    )

    data class UnregisteredStore(
        val storeId: String,
        val storeName: String,
        val storeImgUrl: String,
        val storeType: String
    )
}