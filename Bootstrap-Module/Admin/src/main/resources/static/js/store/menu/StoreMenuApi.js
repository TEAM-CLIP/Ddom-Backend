const StoreMenuApi = {
    endpoints: {
        list: (storeId) => `/store/${storeId}/menu`,
        create: (storeId) => `/store/${storeId}/menu`,
        detail: (storeId, menuId) => `/store/${storeId}/menu/${menuId}`,
        update: (storeId, menuId) => `/store/${storeId}/menu/${menuId}`,
        delete: (storeId, menuId) => `/store/${storeId}/menu/${menuId}`,
    },

    async getMenus(storeId) {
        const response = await fetch(this.endpoints.list(storeId));
        if (!response.ok) throw new Error('메뉴 목록을 불러오는데 실패했습니다.');
        return await response.json();
    },

    async getMenu(storeId, menuId) {
        const response = await fetch(this.endpoints.detail(storeId, menuId));
        if (!response.ok) throw new Error('메뉴 정보를 불러오는데 실패했습니다.');
        return await response.json();
    },

    async createMenu(storeId, menuData) {
        console.log(menuData)
        const response = await fetch(this.endpoints.create(storeId), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(menuData)
        });
        if (!response.ok) throw new Error('메뉴 추가에 실패했습니다.');
    },

    async updateMenu(storeId, menuId, menuData) {
        const response = await fetch(this.endpoints.update(storeId, menuId), {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(menuData)
        });
        if (!response.ok) throw new Error('메뉴 수정에 실패했습니다.');
    },

    async deleteMenu(storeId, menuId) {
        const response = await fetch(this.endpoints.delete(storeId, menuId), {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('메뉴 삭제에 실패했습니다.');
        return true;
    }
};