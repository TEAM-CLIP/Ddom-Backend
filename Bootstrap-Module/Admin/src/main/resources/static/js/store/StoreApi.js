const StoreApi = {
    // API 엔드포인트
    endpoints: {
        list: '/store',
        detail: (id) => `/store/${id}`,
        update: (id) => `/store/${id}`,
        category: '/store/category'
    },

    // 가게 목록 조회
    async fetchStores(page, size) {
        try {
            const response = await fetch(`${this.endpoints.list}?page=${page}&size=${size}`);
            if (!response.ok) throw new Error('가게 목록을 가져오는데 실패했습니다.');
            return await response.json();
        } catch (error) {
            console.error('Error fetching stores:', error);
            throw error;
        }
    },

    // 개별 가게 조회
    async fetchStore(id) {
        try {
            const response = await fetch(this.endpoints.detail(id));
            console.log(response)
            if (!response.ok) throw new Error('가게 정보를 가져오는데 실패했습니다.');
            return await response.json();
        } catch (error) {
            console.error('Error fetching store:', error);
            throw error;
        }
    },

    // 가게 정보 수정
    async updateStore(id, storeData) {
        try {
            const response = await fetch(this.endpoints.update(id), {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(storeData)
            });
            if (!response.ok) throw new Error('가게 정보 수정에 실패했습니다.');
            return await response.json();
        } catch (error) {
            console.error('Error updating store:', error);
            throw error;
        }
    },

    async fetchCategories() {
        try {
            const response = await fetch(this.endpoints.category);
            if (!response.ok) throw new Error('카테고리 목록을 가져오는데 실패했습니다.');
            return await response.json();
        } catch (error) {
            console.error('Error fetching categories:', error);
            throw error;
        }
    }
};