package com.clip.application.store.port.`in`

interface GetZoneUseCase {

        fun getAllZones(): GetAllResponse

        data class GetAllResponse(
            val zones: List<ZoneDetail>
        )

        data class ZoneDetail(
            val id: String,
            val name: String,
            val description: String?
        )

}