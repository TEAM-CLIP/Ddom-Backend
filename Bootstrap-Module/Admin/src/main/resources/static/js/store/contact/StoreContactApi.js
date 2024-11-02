const StoreContactApi = {

    endpoints: {
        list: (storeId) => `/store/${storeId}/contact`,
        create: (storeId) => `/store/${storeId}/contact`,
        detail: (storeId, contactId) => `/store/${storeId}/contact/${contactId}`,
        update: (storeId, contactId) => `/store/${storeId}/contact/${contactId}`,
        delete: (storeId, contactId) => `/store/${storeId}/contact/${contactId}`,
        types: (storeId) => `/store/${storeId}/contact/type`
    },

    async getContacts(storeId) {
        const response = await fetch(this.endpoints.list(storeId));
        if (!response.ok) throw new Error('연락처 목록을 불러오는데 실패했습니다.');
        return await response.json();
    },

    async getContact(storeId, contactId) {
        const response = await fetch(this.endpoints.detail(storeId, contactId));
        if (!response.ok) throw new Error('연락처 정보를 불러오는데 실패했습니다.');
        return await response.json();
    },

    async createContact(storeId, contactData) {
        const response = await fetch(this.endpoints.create(storeId), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(contactData)
        });
        if (!response.ok) throw new Error('연락처 추가에 실패했습니다.');
    },

    async updateContact(storeId, contactId, contactData) {
        const response = await fetch(this.endpoints.update(storeId, contactId), {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(contactData)
        });
        if (!response.ok) throw new Error('연락처 수정에 실패했습니다.');
    },

    async deleteContact(storeId, contactId) {
        const response = await fetch(this.endpoints.delete(storeId, contactId), {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('연락처 삭제에 실패했습니다.');
        return true;
    },

    async getContactTypes(storeId) {
        const response = await fetch(this.endpoints.types(storeId));
        if (!response.ok) throw new Error('연락처 유형을 불러오는데 실패했습니다.');
        return await response.json();
    }


}