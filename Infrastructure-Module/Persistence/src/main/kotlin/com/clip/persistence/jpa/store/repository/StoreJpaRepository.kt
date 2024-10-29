package com.clip.persistence.jpa.store.repository

import com.clip.persistence.jpa.store.entity.StoreEntity
import org.springframework.data.jpa.repository.JpaRepository

interface StoreJpaRepository : JpaRepository<StoreEntity, String> {
}