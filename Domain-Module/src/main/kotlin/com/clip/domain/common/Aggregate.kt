package com.clip.domain.common

abstract class Aggregate<T : Aggregate<T>>(
    val id: DomainId,
) {

}
