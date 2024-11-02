package com.clip.admin

import com.clip.admin.dto.GetStoreCategoryResponse
import com.clip.admin.dto.GetStoreDetailInfoResponse
import com.clip.admin.dto.GetStoreInfoResponse
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
            "https://store1.com",
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


}