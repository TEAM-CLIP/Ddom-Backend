package com.clip.admin

import com.clip.admin.dto.CreateMenuRequest
import com.clip.admin.dto.GetStoreMenuInfoResponse
import com.clip.admin.dto.UpdateMenuRequest
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/store/{id}/menu")
class MenuController {

    @GetMapping
    @ResponseBody
    fun getStoreMenu(
        @PathVariable id: String
    ): List<GetStoreMenuInfoResponse> = listOf(
        GetStoreMenuInfoResponse("1", "menu1", "10000"),
        GetStoreMenuInfoResponse("2", "menu2", "20000"),
        GetStoreMenuInfoResponse("3", "menu3", "30000"),
        GetStoreMenuInfoResponse("4", "menu4", "40000"),
        GetStoreMenuInfoResponse("5", "menu5", "50000"),
    )

    @GetMapping("/{menuId}")
    @ResponseBody
    fun getStoreMenuById(
        @PathVariable id: String,
        @PathVariable menuId: String
    ): GetStoreMenuInfoResponse {
        return GetStoreMenuInfoResponse(menuId, "menu1", "10000")
    }

    @PostMapping
    @ResponseBody
    fun createMenu(
        @PathVariable id: String,
        @RequestBody request: CreateMenuRequest
    ) {
        println(request)
    }

    @PutMapping("/{menuId}")
    @ResponseBody
    fun updateMenu(
        @PathVariable id: String,
        @PathVariable menuId: String,
        @RequestBody request: UpdateMenuRequest
    ) {
        println(request)
    }

    @DeleteMapping("/{menuId}")
    @ResponseBody
    fun deleteMenu(
        @PathVariable id: String,
        @PathVariable menuId: String
    ) {
        println("delete menu")
    }
}