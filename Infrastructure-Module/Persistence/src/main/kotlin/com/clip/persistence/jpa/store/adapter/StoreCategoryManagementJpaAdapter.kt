package com.clip.persistence.jpa.store.adapter

import com.clip.application.store.port.out.StoreCategoryManagementPort
import com.clip.domain.common.DomainId
import com.clip.domain.store.entity.StoreCategory
import com.clip.persistence.jpa.store.StoreCategoryMapper
import com.clip.persistence.jpa.store.repository.StoreCategoryJpaRepository
import com.clip.persistence.jpa.store.repository.findAllActiveStoreCategoryByIds
import org.springframework.stereotype.Repository

@Repository
class StoreCategoryManagementJpaAdapter(
    private val storeCategoryJpaRepository: StoreCategoryJpaRepository
) : StoreCategoryManagementPort {
    override fun getCategoriesBy(categoryIds: List<DomainId>): List<StoreCategory> {
        val categories = storeCategoryJpaRepository.findAllActiveStoreCategoryByIds(categoryIds.map { it.value })
        return categories.map { StoreCategoryMapper.toStoreCategory(it) }
    }

    override fun getCategoryBy(categoryId: DomainId): StoreCategory {
        val category = storeCategoryJpaRepository.findById(categoryId.value)
            .orElseThrow { throw IllegalStateException("Category not found") }
        return StoreCategoryMapper.toStoreCategory(category)
    }

    override fun getAll(): List<StoreCategory> {
        val categories = storeCategoryJpaRepository.findAll()
        return categories.map { StoreCategoryMapper.toStoreCategory(it) }
    }
}