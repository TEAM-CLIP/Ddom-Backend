package com.clip.persistence.jpa.common

import com.clip.domain.common.DomainId
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity(
    @Id
    open val id: String = DomainId.generate().value,
) {
    var createdAt: LocalDateTime = LocalDateTime.now()

    open var updatedAt: LocalDateTime = LocalDateTime.now()

    fun update() {
        this.updatedAt = LocalDateTime.now()
    }
}
