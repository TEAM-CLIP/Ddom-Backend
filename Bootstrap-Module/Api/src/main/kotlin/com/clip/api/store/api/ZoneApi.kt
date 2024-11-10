package com.clip.api.store.api

import com.clip.api.common.security.annotation.AccessUser
import com.clip.api.store.dto.GetAllZoneResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Zone", description = "Zone API")
@RequestMapping("/api/v1/zones")
interface ZoneApi {

    @Operation(summary = "구역 목록 조회")
    @GetMapping
    @ApiResponse
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "전체 구역 목록 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = GetAllZoneResponse::class),
                    ),
                ],
            ),
        ],
    )
    fun getZones(
        @AccessUser userId: String,
    ): GetAllZoneResponse


}