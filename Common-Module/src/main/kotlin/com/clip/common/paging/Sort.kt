package com.clip.common.paging

data class Sort(
    val property: String,
    val direction: Direction,
) {
    enum class Direction {
        ASC,
        DESC,
    }
}