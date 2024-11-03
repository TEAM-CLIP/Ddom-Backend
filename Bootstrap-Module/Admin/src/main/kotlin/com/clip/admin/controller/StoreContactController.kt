package com.clip.admin.controller

import com.clip.admin.dto.CreateContactRequest
import com.clip.admin.dto.GetStoreContactInfoResponse
import com.clip.admin.dto.UpdateContactRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Controller
@RequestMapping("/store/{id}/contact")
class StoreContactController {

    @GetMapping
    @ResponseBody
    fun getStoreContact(
        @PathVariable id: String
    ): List<GetStoreContactInfoResponse> {
        return listOf(
            GetStoreContactInfoResponse(UUID.randomUUID().toString(),"PHONE", "010-1234-5678"),
            GetStoreContactInfoResponse(UUID.randomUUID().toString(),"INSTAGRAM", "@store1"),
        )
    }

    @GetMapping("/type")
    @ResponseBody
    fun getStoreContactType(): List<String> {
        return listOf("PHONE", "INSTAGRAM")
    }

    @GetMapping("/{contactId}")
    @ResponseBody
    fun getStoreContactDetail(
        @PathVariable id: String,
        @PathVariable contactId: String
    ): GetStoreContactInfoResponse {
        return GetStoreContactInfoResponse(UUID.randomUUID().toString(),"PHONE", "010-1234-5678")
    }

    @PostMapping
    @ResponseBody
    fun createStoreContact(
        @PathVariable id: String,
        @RequestBody request: CreateContactRequest
    ) {
        println("create contact: $request")
    }

    @PutMapping("/{contactId}")
    @ResponseBody
    fun updateStoreContact(
        @PathVariable id: String,
        @PathVariable contactId: String,
        @RequestBody request: UpdateContactRequest
    ) {
        println("update contact: $request")
    }

    @DeleteMapping("/{contactId}")
    @ResponseBody
    fun deleteStoreContact(
        @PathVariable id: String,
        @PathVariable contactId: String
    ) {
        println("delete contact: $contactId")
    }
}