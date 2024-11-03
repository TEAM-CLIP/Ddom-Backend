const StoreDiscountApi = {
    endpoints: {
        list: (storeId) => `/store/${storeId}/discount`,
        create: (storeId) => `/store/${storeId}/discount`,
        detail: (storeId, discountId) => `/store/${storeId}/discount/${discountId}`,
        update: (storeId, discountId) => `/store/${storeId}/discount/${discountId}`,
        delete: (storeId, discountId) => `/store/${storeId}/discount/${discountId}`,
        types: (storeId) => `/store/${storeId}/discount/type`
    },

    async getDiscounts(storeId) {
        const response = await fetch(this.endpoints.list(storeId));
        if (!response.ok) throw new Error('할인 목록을 불러오는데 실패했습니다.');
        return await response.json();
    },

    async getDiscount(storeId, discountId) {
        const response = await fetch(this.endpoints.detail(storeId, discountId));
        if (!response.ok) throw new Error('할인 정보를 불러오는데 실패했습니다.');
        return await response.json();
    },

    async createDiscount(storeId, discountData) {
        const response = await fetch(this.endpoints.create(storeId), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(discountData)
        });
        if (!response.ok) throw new Error('할인 추가에 실패했습니다.');
    },

    async updateDiscount(storeId, discountId, discountData) {
        const response = await fetch(this.endpoints.update(storeId, discountId), {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(discountData)
        });
        if (!response.ok) throw new Error('할인 수정에 실패했습니다.');
    },

    async deleteDiscount(storeId, discountId) {
        const response = await fetch(this.endpoints.delete(storeId, discountId), {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('할인 삭제에 실패했습니다.');
        return true;
    },

    async getDiscountTypes(storeId) {
        const response = await fetch(this.endpoints.types(storeId));
        if (!response.ok) throw new Error('할인 유형을 불러오는데 실패했습니다.');
        return await response.json();
    }
}