package com.clip.admin

import com.clip.admin.dto.CreateDiscountPolicyRequest
import com.clip.admin.dto.GetDiscountPolicyInfoResponse
import com.clip.admin.dto.UpdateDiscountPolicyRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/store/{storeId}/discount")
class DiscountController {

    @GetMapping
    @ResponseBody
    fun getStoreDiscount(
        @PathVariable storeId: String
    ): List<GetDiscountPolicyInfoResponse> = listOf(
        GetDiscountPolicyInfoResponse("1", "DEFAULT", "10"),
        GetDiscountPolicyInfoResponse("2", "VISIT_5_TIMES", "5"),
        GetDiscountPolicyInfoResponse("3", "VISIT_10_TIMES", "10")
    )

    @GetMapping("/{discountId}")
    @ResponseBody
    fun getStoreDiscountById(
        @PathVariable storeId: String,
        @PathVariable discountId: String
    ): GetDiscountPolicyInfoResponse = GetDiscountPolicyInfoResponse(discountId, "VISIT_5_TIMES", "5")


    @GetMapping("/type")
    @ResponseBody
    fun getStoreDiscountByType(
        @PathVariable storeId: String,
    ) = listOf("DEFAULT", "VISIT_5_TIMES", "VISIT_10_TIMES")

    @PostMapping
    @ResponseBody
    fun createDiscountPolicy(
        @PathVariable storeId: String,
        @RequestBody request: CreateDiscountPolicyRequest
    ){
        println("Create discount policy: $request")
    }

    @PutMapping("/{discountId}")
    @ResponseBody
    fun updateDiscountPolicy(
        @PathVariable storeId: String,
        @PathVariable discountId: String,
        @RequestBody request: UpdateDiscountPolicyRequest
    ){
        println("Update discount policy: $request")
    }

    @DeleteMapping("/{discountId}")
    @ResponseBody
    fun deleteDiscountPolicy(
        @PathVariable storeId: String,
        @PathVariable discountId: String
    ){
        println("Delete discount policy: $discountId")
    }
}