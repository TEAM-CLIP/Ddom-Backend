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
    private val storeCategoryManagementPort: StoreCategoryManagementPort,
    private val zoneManagementPort: ZoneManagementPort
) : GetStoreUseCase, GetStoreCategoryUseCase, GetZoneUseCase {

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
                                discountType = it.discountPolicyMethod.name,
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

    override fun getStoreDetail(query: GetStoreUseCase.GetDetailQuery): GetStoreUseCase.GetDetailResponse {
        val store = storeManagementPort.getStoreBy(DomainId(query.storeId))
        val storeCategory = storeCategoryManagementPort.getCategoryBy(store.storeCategory.categoryId)
        return with(store) {
            GetStoreUseCase.GetDetailResponse(
                storeId = id.value,
                storeName = storeInfo.name,
                storeImageUrl = storeInfo.imgUrl,
                introduction = storeInfo.introduction,
                isRegistered = storeInfo.isRegistered,
                longitude = storePlace.longitude,
                latitude = storePlace.latitude,
                storeCategoryId = storeCategory.id.value,
                storeCategoryName = storeCategory.type.name
            )
        }
    }

    override fun getStorePaging(query: GetStoreUseCase.GetPagingQuery): Page<GetStoreUseCase.GetPagingResponse> {
        val page = storeManagementPort.getAllStores(PageRequest(query.page, query.size))
        return page.map {
            GetStoreUseCase.GetPagingResponse(
                storeId = it.id.value,
                name = it.storeInfo.name,
                region = zoneManagementPort.getBy(it.storePlace.zoneId).name,
                isRegistered = it.storeInfo.isRegistered
            )
        }
    }

    override fun getAll(): List<GetStoreCategoryUseCase.Response> {
        return storeCategoryManagementPort.getAll().map {
            GetStoreCategoryUseCase.Response(
                id = it.id.value,
                name = it.type.description
            )
        }
    }

    override fun getAll(): GetZoneUseCase.GetAllResponse {
        val zones = zoneManagementPort.getAllZones()
        return GetZoneUseCase.GetAllResponse(
            zones = zones.map {
                GetZoneUseCase.ZoneDetail(
                    id = it.id.value,
                    name = it.name,
                    description = it.description
                )
            }
        )
    }
}