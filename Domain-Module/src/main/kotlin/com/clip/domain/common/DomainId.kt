package com.clip.domain.common

import com.clip.uuid.UuidCreator

@JvmInline
value class DomainId(
    val value: String
) {
    companion object{
        fun generate(): DomainId {
            return DomainId(UuidCreator.create().toString())
        }
    }
}