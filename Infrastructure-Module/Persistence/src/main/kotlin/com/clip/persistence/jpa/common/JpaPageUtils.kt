package com.clip.persistence.jpa.common


import com.clip.common.paging.Page
import com.clip.common.paging.PageRequest
import com.clip.common.paging.Sort

typealias JpaPageable = org.springframework.data.domain.PageRequest
typealias JpaPage<T> = org.springframework.data.domain.Page<T>
typealias JpaSort = org.springframework.data.domain.Sort
typealias JpaSortDirection = org.springframework.data.domain.Sort.Direction


object JpaPageUtils {

    fun PageRequest.convertToJpaPageable(): JpaPageable {
        return JpaPageable.of(
            page,
            size,
            sorts.convertToJpaSort()
        )
    }

    fun <T> JpaPage<T>.convertToPage(): Page<T> {
        return Page.of(
            content,
            totalElements,
            totalPages,
            size,
            number
        )
    }



    private fun Sort?.convertToJpaSort(): JpaSort {
        return this?.let {
            JpaSort.by(
                JpaSortDirection.valueOf(this.direction.name),
                this.property
            )
        } ?: JpaSort.unsorted()
    }
}
