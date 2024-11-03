package com.clip.application.store.service

import com.clip.application.store.port.`in`.GetStoreUseCase
import com.clip.application.store.port.out.StoreCategoryManagementPort
import com.clip.application.store.port.out.StoreManagementPort
import com.clip.domain.common.DomainId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StoreQueryService(
    private val storeManagementPort: StoreManagementPort,
    private val storeCategoryManagementPort: StoreCategoryManagementPort
) : GetStoreUseCase{

    override fun getAll(query: GetStoreUseCase.GetAllQuery): GetStoreUseCase.GetAllResponse {
        val stores = storeManagementPort.getAllStores(query.zoneId?.let { DomainId(it) })
        val storeIds = stores.map { it.id }

        val favoriteCountsMap = storeManagementPort.countFavoriteUsersBy(storeIds)
        val userFavoritedStoreIds = storeManagementPort.getFavoritedStoreIdsBy(DomainId(query.userId), storeIds)

        val categories = storeCategoryManagementPort.getCategoriesBy(stores.map { it.storeCategory.categoryId })
        val categoryMap = categories.associateBy { it.id }

        return GetStoreUseCase.GetAllResponse(
            registeredStore = stores.filter { it.storeInfo.isRegistered }.map { store ->
                with(store) {
                    GetStoreUseCase.RegisteredStore(
                        storeId = id.value,
                        storeName = storeInfo.name,
                        storeImgUrl = storeInfo.imgUrl,
                        favoriteUserCount = favoriteCountsMap[id] ?: 0,
                        isFavorited = userFavoritedStoreIds.contains(id),
                        storeType = categoryMap.getValue(storeCategory.categoryId).type.name,
                        discountPolicy = discount.map {
                            GetStoreUseCase.DiscountPolicy(
                                discountType = it.discountPolicyMethod.toString(),
                                discountDescription = it.title
                            )
                        }
                    )
                }
            },
            unregisteredStore = stores.filterNot { it.storeInfo.isRegistered }.map { store ->
                with(store) {
                    GetStoreUseCase.UnregisteredStore(
                        storeId = id.value,
                        storeName = storeInfo.name,
                        storeImgUrl = storeInfo.imgUrl,
                        storeType = categoryMap.getValue(storeCategory.categoryId).type.name,
                    )
                }
            }
        )
    }
}