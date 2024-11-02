const StoreContact = {
    contactModal: null,
    currentContactId: null,
    currentStoreId: null,
    contactTypes: [], // 연락처 타입 저장

    init(storeId) {
        this.contactModal = new bootstrap.Modal(document.getElementById('contactModal'));
        this.currentStoreId = storeId;
        this.loadContactTypes(storeId).then(() => {
            this.loadContacts();
            this.initializeEvents();
        });
    },

    initializeEvents() {

        document.getElementById('contactForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveContact();
        });

        document.getElementById('contactModal').addEventListener('hidden.bs.modal', () => {
            document.getElementById('contactForm').reset();
            this.currentContactId = null;
        });
    },

    async loadContacts() {
        try {
            const contacts = await StoreContactApi.getContacts(this.currentStoreId);
            this.renderContactTable(contacts);
        } catch (error) {
            console.error('Error:', error);
            this.showError('연락처 목록을 불러오는데 실패했습니다.');
        }
    },

    // 연락처 타입 로드
    async loadContactTypes(storeId) {
        try {
            this.contactTypes = await StoreContactApi.getContactTypes(storeId);
            this.renderContactTypes();
        } catch (error) {
            console.error('Error:', error);
            this.showError('연락처 타입을 불러오는데 실패했습니다.');
        }
    },

    renderContactTable(contacts) {
        const tableBody = document.getElementById('contactTableBody');
        tableBody.innerHTML = '';

        if (contacts.length === 0) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="3" class="text-center">등록된 연락처가 없습니다.</td>
                </tr>
            `;
            return;
        }

        contacts.forEach(contact => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>
                    <span class="badge bg-secondary">${this.formatContactType(contact.contactType)}</span>
                </td>
                <td>${contact.value}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary me-1"
                        onclick="StoreContact.openEditModal('${contact.id}')">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" 
                        onclick="StoreContact.deleteContact('${contact.id}')">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    },


    // 연락처 타입 드롭다운 렌더링
    renderContactTypes() {
        const select = document.getElementById('contactType');
        select.innerHTML = '<option value="">선택하세요</option>';

        this.contactTypes.forEach(type => {
            const option = document.createElement('option');
            option.value = type;
            option.textContent = this.formatContactType(type);
            select.appendChild(option);
        });
    },

    // 연락처 타입 포맷팅
    formatContactType(type) {
        const typeMap = {
            'PHONE': '전화번호',
            'INSTAGRAM': '인스타그램'
        };
        return typeMap[type] || type;
    },

    openAddModal() {
        this.currentContactId = null;
        document.getElementById('contactModalLabel').textContent = '메뉴 추가';
        document.getElementById('contactForm').reset();
        this.contactModal.show();
    },

    async openEditModal(contactId) {
        try {
            const contact = await StoreContactApi.getContact(this.currentStoreId, contactId);
            this.currentContactId = contactId;
            document.getElementById('contactModalLabel').textContent = '연락처 수정';
            document.getElementById('contactType').value = contact.contactType;
            document.getElementById('contactValue').value = contact.value;
            this.contactModal.show();
        } catch (error) {
            console.error('Error:', error);
            this.showError('연락처 정보를 불러오는데 실패했습니다.');
        }
    },


    async saveContact() {
        if (!this.validateContactForm()) return;

        const contactData = {
            contactType: document.getElementById('contactType').value,
            value: document.getElementById('contactValue').value
        }

        try {
            if (this.currentContactId) {
                await StoreContactApi.updateContact(this.currentStoreId, this.currentContactId, contactData);
            } else {
                await StoreContactApi.createContact(this.currentStoreId, contactData);
            }
            this.loadContacts();
            this.showSuccess(this.currentContactId ? '연락처가 수정되었습니다.' : '연락처가 추가되었습니다.');
            this.contactModal.hide();
        } catch (error) {
            console.error('Error:', error);
            this.showError('연락처 저장에 실패했습니다.');
        }
    },

    async deleteContact(contactId) {
        if (!confirm('정말로 삭제하시겠습니까?')) return;

        try {
            await StoreContactApi.deleteContact(this.currentStoreId, contactId);
            this.loadContacts();
            this.showSuccess('연락처가 삭제되었습니다.');
        } catch (error) {
            console.error('Error:', error);
            this.showError('연락처 삭제에 실패했습니다.');
        }
    },


    validateContactForm() {
        const type = document.getElementById('contactType').value;
        const value = document.getElementById('contactValue').value;
        if (!type || !value) {
            this.showError('연락처 유형과 값을 모두 입력해주세요.');
            return false;
        }
        return true;
    },

    showError(message) {
        alert(message);
    },

    showSuccess(message) {
        alert(message);
    }
}