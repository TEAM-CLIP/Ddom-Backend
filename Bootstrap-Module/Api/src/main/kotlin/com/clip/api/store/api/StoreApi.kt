package com.clip.api.store.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Store", description = "Store API")
@RequestMapping("/api/v1/stores")
interface StoreApi {

}