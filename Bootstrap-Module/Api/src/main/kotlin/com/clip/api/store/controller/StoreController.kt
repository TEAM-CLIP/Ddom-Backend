package com.clip.api.store.controller

import com.clip.api.store.api.StoreApi
import com.clip.api.store.dto.GetAllStoreResponse
import com.clip.api.store.dto.GetAllStoreResponse.UnregisteredStoreResponse
import com.clip.api.store.dto.GetAllStoreResponse.RegisteredStoreResponse
import com.clip.application.store.port.`in`.GetStoreUseCase
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController(
    private val getStoreUseCase: GetStoreUseCase
) : StoreApi {

    override fun getSpaces(userId: String, zoneId: String?): GetAllStoreResponse {
        val response = getStoreUseCase.getAll(
            GetStoreUseCase.GetAllQuery(userId, zoneId)
        )
        return GetAllStoreResponse(
            response.registeredStore.map { registeredStore ->
                RegisteredStoreResponse(
                    registeredStore.storeId,
                    registeredStore.storeName,
                    registeredStore.storeImgUrl,
                    registeredStore.favoriteUserCount,
                    registeredStore.isFavorited,
                    registeredStore.storeType,
                    registeredStore.discountPolicy.map {
                        RegisteredStoreResponse.DiscountPolicyResponse(
                            it.discountType,
                            it.discountDescription
                        )
                    }
                )
            },
            response.unregisteredStore.map {
                UnregisteredStoreResponse(
                    it.storeId,
                    it.storeName,
                    it.storeImgUrl,
                    it.storeType
                )
            }

        )
    }
}