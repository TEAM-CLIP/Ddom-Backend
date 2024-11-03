package com.clip.admin.controller

import com.clip.admin.dto.CreateActiveTimeRequest
import com.clip.admin.dto.GetActiveTimeInfoResponse
import com.clip.admin.dto.UpdateActiveTimeRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

@Controller
@RequestMapping("/store/{id}/active-time")
class StoreActiveTimeController {

    @GetMapping
    @ResponseBody
    fun getStoreActiveTime(
        @PathVariable id: String
    ): List<GetActiveTimeInfoResponse> {
        return listOf(
            GetActiveTimeInfoResponse(
                "1",
                DayOfWeek.MONDAY,
                LocalTime.MIN,
                LocalTime.MAX,
                false
            ),
            GetActiveTimeInfoResponse(
                "2",
                DayOfWeek.TUESDAY,
                LocalTime.MIN,
                LocalTime.MAX,
                true
            ),
            GetActiveTimeInfoResponse(
                "3",
                DayOfWeek.WEDNESDAY,
                LocalTime.MIN,
                LocalTime.MAX,
                false
            ),
            GetActiveTimeInfoResponse(
                "4",
                DayOfWeek.THURSDAY,
                LocalTime.MIN,
                LocalTime.MAX,
                true
            ),
            GetActiveTimeInfoResponse(
                "5",
                DayOfWeek.FRIDAY,
                LocalTime.MIN,
                LocalTime.MAX,
                true
            ),
            GetActiveTimeInfoResponse(
                "6",
                DayOfWeek.SATURDAY,
                LocalTime.MIN,
                LocalTime.MAX,
                true
            ),
            GetActiveTimeInfoResponse(
                "7",
                DayOfWeek.SUNDAY,
                LocalTime.MIN,
                LocalTime.MAX,
                true
            ),
        )
    }

    @GetMapping("/{activeTimeId}")
    @ResponseBody
    fun getStoreActiveTimeDetail(
        @PathVariable activeTimeId: String,
        @PathVariable id: String
    ): GetActiveTimeInfoResponse {
        val now = LocalDate.now()
        return GetActiveTimeInfoResponse(
            activeTimeId,
            DayOfWeek.MONDAY,
            LocalTime.MIN,
            LocalTime.MAX,
            false
        )
    }

    @GetMapping("/type")
    @ResponseBody
    fun getStoreActiveTimeType(@PathVariable id: String) = DayOfWeek.entries.map { it.name }

    @PostMapping
    @ResponseBody
    fun createStoreActiveTime(
        @PathVariable id: String,
        @RequestBody request: CreateActiveTimeRequest
    ) {
        println("create store active time: $request")
    }

    @PutMapping("/{activeTimeId}")
    @ResponseBody
    fun updateStoreActiveTime(
        @PathVariable id: String,
        @PathVariable activeTimeId: String,
        @RequestBody request: UpdateActiveTimeRequest
    ) {
        println("update store active time: $request")
    }


    @DeleteMapping("/{activeTimeId}")
    @ResponseBody
    fun deleteStoreActiveTime(
        @PathVariable activeTimeId: String,
        @PathVariable id: String
    ) {
        println("delete store active time")
    }

}