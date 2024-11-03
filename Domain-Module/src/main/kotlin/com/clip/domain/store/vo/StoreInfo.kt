package com.clip.domain.store.vo

data class StoreInfo(
    val name: String,
    val imgUrl: String,
    val introduction: String?,
    val isRegistered: Boolean,
    val storePlace: StorePlace,
) {
    data class StorePlace(
        val longitude: Double,
        val latitude: Double,
    ) {
    }
}