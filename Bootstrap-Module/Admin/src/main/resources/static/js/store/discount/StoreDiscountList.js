const StoreDiscount = {
    discountModal: null,
    currentDiscountId: null,
    currentStoreId: null,
    discountTypes: [],


    init(storeId) {
        this.discountModal = new bootstrap.Modal(document.getElementById('discountModal'));
        this.currentStoreId = storeId;
        this.loadDiscountTypes(storeId)
        this.loadDiscounts().then(() => {
            this.initializeEvents();
        });
    },

    initializeEvents() {
        document.getElementById('discountForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveDiscount();
        });

        document.getElementById('discountModal').addEventListener('hidden.bs.modal', () => {
            document.getElementById('discountForm').reset();
            this.currentDiscountId = null;
        });
    },

    async loadDiscounts() {
        try {
            this.discounts = await StoreDiscountApi.getDiscounts(this.currentStoreId);
            this.renderDiscounts();
        } catch (error) {
            console.error('Error:', error);
            this.showError('할인 정보를 불러오는데 실패했습니다.');
        }
    },

    async loadDiscountTypes(storeId) {
        try {
            this.discountTypes = await StoreDiscountApi.getDiscountTypes(storeId);
            this.renderDiscountTypes();
        } catch (error) {
            console.error('Error:', error);
            this.showError('할인 타입을 불러오는데 실패했습니다.');
        }
    },

    renderDiscounts() {
        const discountList = document.getElementById('discountTableBody');
        if(this.discounts.length === 0) {
            discountList.innerHTML = `
                <tr>
                    <td colspan="3" class="text-center">할인 정보가 없습니다.</td>
                </tr>
            `;
            return;
        }

        discountList.innerHTML = this.discounts.map(discount => `
            <tr>
                <td>${discount.discountType}</td>
                <td>${discount.discountValue}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary me-1"
                        onclick="StoreDiscount.openEditModal('${discount.id}')">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" 
                        onclick="StoreDiscount.deleteDiscount('${discount.id}')">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
            </tr>
        `).join('');
    },

    renderDiscountTypes() {
        const select = document.getElementById('discountType');
        select.innerHTML = '<option value="">선택하세요</option>';

        this.discountTypes.forEach(type => {
            const option = document.createElement('option');
            option.value = type;
            option.innerText = type;
            select.appendChild(option);
        });
    },

    openAddModal() {
        this.currentDiscountId = null;
        document.getElementById('discountModalLabel').textContent = '할인 추가';
        document.getElementById('discountForm').reset();
        this.discountModal.show();
    },

    async openEditModal(discountId) {
        const discount = await StoreDiscountApi.getDiscount(this.currentStoreId, discountId);
        this.currentDiscountId = discountId;
        document.getElementById('discountModalLabel').textContent = '할인 수정';
        document.getElementById('discountType').value = discount.discountType;
        document.getElementById('discountValue').value = discount.discountValue;
        this.discountModal.show();
    },

    async saveDiscount() {
        if(!this.validateDiscountForm()) return;

        const discountData = {
            discountType: document.getElementById('discountType').value,
            discountValue: document.getElementById('discountValue').value
        }

        try {
            if (this.currentDiscountId) {
                await StoreDiscountApi.updateDiscount(this.currentStoreId, this.currentDiscountId, discountData);
            } else {
                await StoreDiscountApi.createDiscount(this.currentStoreId, discountData);
            }
            this.loadDiscounts();
            this.discountModal.hide();
            this.showSuccess('할인 정보가 저장되었습니다.');
        } catch (error) {
            console.error('Error:', error);
            this.showError('할인 정보를 저장하는데 실패했습니다.');
        }
    },

    async deleteDiscount(discountId) {
        if (!confirm('정말로 삭제하시겠습니까?')) return;

        try {
            await StoreDiscountApi.deleteDiscount(this.currentStoreId, discountId);
            this.loadDiscounts();
            this.showSuccess('할인 정보가 삭제되었습니다.');
        } catch (error) {
            console.error('Error:', error);
            this.showError('할인 정보를 삭제하는데 실패했습니다.');
        }
    },

    validateDiscountForm() {
        const discountType = document.getElementById('discountType');
        const discountValue = document.getElementById('discountValue');

        if (!discountType.value) {
            this.showError('할인 타입을 선택하세요.');
            discountType.focus();
            return false;
        }

        if (!discountValue.value) {
            this.showError('할인 금액을 입력하세요.');
            discountValue.focus();
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