package com.clip.domain.store.entity

import com.clip.domain.common.DomainId

class Zone(
    val id: DomainId = DomainId.generate(),
    var name: String,
    var boundary: String
) {

    companion object{
        fun of(
            name: String,
            boundary: String
        ): Zone {
            return Zone(
                name = name,
                boundary = boundary
            )
        }
    }

}