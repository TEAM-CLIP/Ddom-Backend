package com.clip.admin.dto

import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

data class UpdateActiveTimeRequest(
    val dayOfWeek: DayOfWeek,
    val startAt: LocalTime,
    val endAt: LocalTime,
    val isActive: Boolean
) {
}