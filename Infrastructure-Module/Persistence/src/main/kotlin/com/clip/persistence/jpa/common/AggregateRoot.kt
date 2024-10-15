package com.clip.persistence.jpa.common

abstract class AggregateRoot<T : AggregateRoot<T>>(
    override val id: String,
) : BaseEntity(id)
