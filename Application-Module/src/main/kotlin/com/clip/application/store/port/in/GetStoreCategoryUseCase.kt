package com.clip.application.store.port.`in`

import com.clip.domain.store.vo.StoreCategoryInfo

interface GetStoreCategoryUseCase {

    fun getAll(): List<Response>

    data class Response(
        val id: String,
        val name: String
    )
}