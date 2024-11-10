package com.clip.common.paging

data class PageRequest(
    val page: Int,
    val size: Int,
    val sorts: Sort? = null,
)