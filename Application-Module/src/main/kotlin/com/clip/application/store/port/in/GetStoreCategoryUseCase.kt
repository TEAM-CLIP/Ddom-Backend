package com.clip.application.store.port.`in`

import com.clip.domain.store.vo.StoreCategoryInfo

interface GetStoreCategoryUseCase {

    fun getAllCategories(): List<Response>

    data class Response(
        val id: String,
        val name: String
    )
}