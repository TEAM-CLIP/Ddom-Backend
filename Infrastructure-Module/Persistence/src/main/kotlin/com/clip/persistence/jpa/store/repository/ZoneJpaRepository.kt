package com.clip.persistence.jpa.store.repository

import com.clip.persistence.jpa.store.entity.ZoneEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ZoneJpaRepository : JpaRepository<ZoneEntity, String> {
}