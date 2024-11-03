package com.clip.admin.controller

import com.clip.admin.dto.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*


@Controller
class StoreController {

    @GetMapping("/page/store")
    fun storePage(): String {
        return "admin/store/storeList"
    }

    @GetMapping("/store")
    @ResponseBody
    fun getStore(
        @RequestParam size: Int,
        @RequestParam page: Int
    ): List<GetStoreInfoResponse> = listOf(
        GetStoreInfoResponse(UUID.randomUUID().toString(), "store1", "seoul", true),
        GetStoreInfoResponse(UUID.randomUUID().toString(), "store2", "busan", false),
        GetStoreInfoResponse(UUID.randomUUID().toString(), "store3", "daegu", true),
        GetStoreInfoResponse(UUID.randomUUID().toString(), "store4", "incheon", false),
        GetStoreInfoResponse(UUID.randomUUID().toString(), "store5", "gwangju", true),
    )


    @GetMapping("/store/{id}")
    @ResponseBody
    fun getStoreById(
        @PathVariable id: String
    ): GetStoreDetailInfoResponse {
        return GetStoreDetailInfoResponse(
            UUID.randomUUID().toString(),
            "store1",
            "https://images.unsplash.com/photo-1529927066849-79b791a69825?q=80&w=3569&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            "store1 introduction",
            true,
            37.123456,
            127.123456,
            UUID.randomUUID().toString(),
            "KOREAN"
        )
    }


    @GetMapping("/store/category")
    @ResponseBody
    fun getStoreCategory(): List<GetStoreCategoryResponse> = listOf(
        GetStoreCategoryResponse(UUID.randomUUID().toString(), "카페"),
        GetStoreCategoryResponse(UUID.randomUUID().toString(), "양식"),
        GetStoreCategoryResponse(UUID.randomUUID().toString(), "중식"),
        GetStoreCategoryResponse(UUID.randomUUID().toString(), "한식"),
        GetStoreCategoryResponse(UUID.randomUUID().toString(), "일식")
    )


    @PostMapping("/store")
    @ResponseBody
    fun createStore(
        @RequestBody request: CreateStoreRequest
    ){
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
    ){
        println("delete store: $id")
    }




}