package com.clip.admin.controller

import com.clip.admin.dto.*
import com.clip.application.store.port.`in`.GetStoreCategoryUseCase
import com.clip.application.store.port.`in`.GetStoreUseCase
import com.clip.common.paging.Page
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*


@Controller
class StoreController(
    private val getStoreUseCase: GetStoreUseCase,
    private val getStoreCategoryUseCase: GetStoreCategoryUseCase
) {

    @GetMapping("/page/store")
    fun storePage(): String {
        return "admin/store/storeList"
    }

    @GetMapping("/store")
    @ResponseBody
    fun getStore(
        @RequestParam size: Int,
        @RequestParam page: Int
    ): Page<GetStoreInfoResponse> {
        val response = getStoreUseCase.getStorePaging(
            GetStoreUseCase.GetPagingQuery(
                page = page,
                size = size
            )
        )

        return response.map {
            GetStoreInfoResponse(
                it.storeId,
                it.name,
                it.region,
                it.isRegistered
            )
        }
    }


    @GetMapping("/store/{id}")
    @ResponseBody
    fun getStoreById(
        @PathVariable id: String
    ): GetStoreDetailInfoResponse {
        val response = getStoreUseCase.getStoreDetail(
            GetStoreUseCase.GetDetailQuery(
                storeId = id
            )
        )

        return GetStoreDetailInfoResponse(
            id = response.storeId,
            name = response.storeName,
            imgUrl = response.storeImageUrl,
            introduction = response.introduction.orEmpty(),
            isRegistered = response.isRegistered,
            longitude = response.longitude,
            latitude = response.latitude,
            storeCategoryId = response.storeCategoryId,
            storeCategoryName = response.storeCategoryName
        )
    }


    @GetMapping("/store/category")
    @ResponseBody
    fun getStoreCategory(): List<GetStoreCategoryResponse>{
        val response = getStoreCategoryUseCase.getAll()
        return response.map {
            GetStoreCategoryResponse(
                it.id,
                it.name
            )
        }
    }

    @PostMapping("/store")
    @ResponseBody
    fun createStore(
        @RequestBody request: CreateStoreRequest
    ) {
        println("create store: $request")
    }

    @PutMapping("/store/{id}")
    @ResponseBody
    fun update(
        @PathVariable id: String,
        @RequestBody request: UpdateStoreDetailRequest
    ): GetStoreDetailInfoResponse {
        return GetStoreDetailInfoResponse(
            id,
            request.name,
            request.imgUrl,
            request.introduction,
            request.isRegistered,
            request.longitude,
            request.latitude,
            request.storeCategoryId,
            "KOREAN"
        )
    }


    @DeleteMapping("/store/{id}")
    @ResponseBody
    fun deleteStore(
        @PathVariable id: String
    ) {
        println("delete store: $id")
    }


}