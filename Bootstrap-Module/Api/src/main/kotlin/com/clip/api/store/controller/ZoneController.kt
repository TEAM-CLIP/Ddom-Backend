package com.clip.api.store.controller

import com.clip.api.store.api.ZoneApi
import com.clip.api.store.dto.GetAllZoneResponse
import com.clip.api.store.dto.GetAllZoneResponse.ZoneResponse
import com.clip.application.store.port.`in`.GetZoneUseCase
import org.springframework.web.bind.annotation.RestController

@RestController
class ZoneController(
    private val getZoneUseCase: GetZoneUseCase
): ZoneApi {
    override fun getZones(userId: String): GetAllZoneResponse {
        val response = getZoneUseCase.getAll()
        return GetAllZoneResponse(
            response.zones.map {
                ZoneResponse(
                    it.id,
                    it.name,
                    it.description
                )
            }
        )
    }


}