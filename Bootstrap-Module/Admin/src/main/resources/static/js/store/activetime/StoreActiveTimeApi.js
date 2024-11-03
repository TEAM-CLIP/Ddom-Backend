const StoreActiveTimeApi = {
    endpoints: {
        list: (storeId) => `/store/${storeId}/active-time`,
        create: (storeId) => `/store/${storeId}/active-time`,
        detail: (storeId, activeTimeId) => `/store/${storeId}/active-time/${activeTimeId}`,
        update: (storeId, activeTimeId) => `/store/${storeId}/active-time/${activeTimeId}`,
        delete: (storeId, activeTimeId) => `/store/${storeId}/active-time/${activeTimeId}`,
        type: (storeId) => `/store/${storeId}/active-time/type`
    },

    async getActiveTimes(storeId) {
        const response = await fetch(this.endpoints.list(storeId));
        if (!response.ok) throw new Error('영업시간 목록을 불러오는데 실패했습니다.');
        return await response.json();
    },

    async getActiveTime(storeId, activeTimeId) {
        const response = await fetch(this.endpoints.detail(storeId, activeTimeId));
        if (!response.ok) throw new Error('영업시간 정보를 불러오는데 실패했습니다.');
        return await response.json();
    },

    async createActiveTime(storeId, activeTimeData) {
        const response = await fetch(this.endpoints.create(storeId), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(activeTimeData)
        });
        if (!response.ok) throw new Error('영업시간 추가에 실패했습니다.');
    },

    async updateActiveTime(storeId, activeTimeId, activeTimeData) {
        const response = await fetch(this.endpoints.update(storeId, activeTimeId), {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(activeTimeData)
        });
        if (!response.ok) throw new Error('영업시간 수정에 실패했습니다.');
    },

    async deleteActiveTime(storeId, activeTimeId) {
        const response = await fetch(this.endpoints.delete(storeId, activeTimeId), {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('영업시간 삭제에 실패했습니다.');
        return true;
    },

    async getActiveTimeTypes(storeId) {
        const response = await fetch(this.endpoints.type(storeId));
        if (!response.ok) throw new Error('영업시간 유형을 불러오는데 실패했습니다.');
        return await response.json();
    }

}