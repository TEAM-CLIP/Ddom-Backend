package com.clip.api.store.api

import com.clip.api.common.security.annotation.AccessUser
import com.clip.api.store.dto.GetAllStoreResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Store", description = "Store API")
@RequestMapping("/api/v1/stores")
interface StoreApi {

    @Operation(summary = "가게 목록 조회")
    @GetMapping
    @ApiResponse
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "전체 가게 목록 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = GetAllStoreResponse::class),
                    ),
                ],
            ),
        ],
    )
    fun getSpaces(
        @AccessUser userId: String,
        @RequestParam(value = "region") region: String?
    ): GetAllStoreResponse






}