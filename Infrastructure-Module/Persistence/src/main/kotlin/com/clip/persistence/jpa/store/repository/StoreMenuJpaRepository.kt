package com.clip.persistence.jpa.store.repository

import com.clip.persistence.jpa.store.entity.StoreMenuEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StoreMenuJpaRepository : JpaRepository<StoreMenuEntity, String> {
}