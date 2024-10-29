package com.clip.persistence.jpa.store.repository

import com.clip.persistence.jpa.store.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryJpaRepository : JpaRepository<CategoryEntity, String> {
}