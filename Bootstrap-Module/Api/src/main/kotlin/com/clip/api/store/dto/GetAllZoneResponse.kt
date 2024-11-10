package com.clip.api.store.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "구역 리스트 확인 응답")
data class GetAllZoneResponse(
    @Schema(description = "구역 리스트")
    val zone: List<ZoneResponse> = emptyList()
) {
    @Schema(description = "구역 정보")
    data class ZoneResponse(
        @Schema(description = "구역 ID")
        val id: String,
        @Schema(description = "구역 이름")
        val name: String,
        @Schema(description = "구역 설명")
        val description: String?
    )
}
