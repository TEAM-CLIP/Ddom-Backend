const StoreMenu = {
    menuModal: null,
    currentStoreId: null,
    currentMenuId: null,

    init(storeId) {
        this.menuModal = new bootstrap.Modal(document.getElementById('menuModal'));
        this.currentStoreId = storeId;
        this.loadMenus();
        this.initializeEvents();
    },

    initializeEvents() {
        document.getElementById('menuPrice').addEventListener('input', (e) => {
            e.target.value = e.target.value.replace(/[^0-9]/g, '');
        });

        document.getElementById('menuForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveMenu();
        });
    },

    async loadMenus() {
        try {
            const menus = await StoreMenuApi.getMenus(this.currentStoreId);
            this.renderMenuTable(menus);
        } catch (error) {
            console.error('Error:', error);
            this.showError('메뉴 목록을 불러오는데 실패했습니다.');
        }
    },

    renderMenuTable(menus) {
        const tableBody = document.getElementById('menuTableBody');
        tableBody.innerHTML = '';

        if (menus.length === 0) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="3" class="text-center">등록된 메뉴가 없습니다.</td>
                </tr>
            `;
            return;
        }

        menus.forEach(menu => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${this.escapeHtml(menu.name)}</td>
                <td>${this.formatPrice(menu.price)}원</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary me-1" 
                            onclick="StoreMenu.openEditModal('${menu.id}')">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" 
                            onclick="StoreMenu.deleteMenu('${menu.id}')">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    },

    openAddModal() {
        this.currentMenuId = null;
        document.getElementById('menuModalLabel').textContent = '메뉴 추가';
        document.getElementById('menuForm').reset();
        this.menuModal.show();
    },

    async openEditModal(menuId) {
        try {
            const menu = await StoreMenuApi.getMenu(this.currentStoreId, menuId);
            this.currentMenuId = menuId;
            document.getElementById('menuModalLabel').textContent = '메뉴 수정';
            document.getElementById('menuName').value = menu.name;
            document.getElementById('menuPrice').value = menu.price;
            this.menuModal.show();
        } catch (error) {
            console.error('Error:', error);
            this.showError('메뉴 정보를 불러오는데 실패했습니다.');
        }
    },

    async saveMenu() {
        if (!this.validateMenuForm()) return;

        const menuData = {
            name: document.getElementById('menuName').value.trim(),
            price: document.getElementById('menuPrice').value
        };

        try {
            if (this.currentMenuId) {
                await StoreMenuApi.updateMenu(this.currentStoreId, this.currentMenuId, menuData);
            } else {
                await StoreMenuApi.createMenu(this.currentStoreId, menuData);
            }

            this.menuModal.hide();
            this.loadMenus();
            this.showSuccess(this.currentMenuId ? '메뉴가 수정되었습니다.' : '메뉴가 추가되었습니다.');
        } catch (error) {
            console.error('Error:', error);
            this.showError('메뉴 저장에 실패했습니다.');
        }
    },

    async deleteMenu(menuId) {
        if (!confirm('정말로 이 메뉴를 삭제하시겠습니까?')) return;

        try {
            await StoreMenuApi.deleteMenu(menuId);
            this.loadMenus();
            this.showSuccess('메뉴가 삭제되었습니다.');
        } catch (error) {
            console.error('Error:', error);
            this.showError('메뉴 삭제에 실패했습니다.');
        }
    },

    validateMenuForm() {
        const name = document.getElementById('menuName').value.trim();
        const price = document.getElementById('menuPrice').value;

        if (!name) {
            this.showError('메뉴명을 입력해주세요.');
            return false;
        }
        if (!price) {
            this.showError('가격을 입력해주세요.');
            return false;
        }
        return true;
    },

    formatPrice(price) {
        return Number(price).toLocaleString();
    },

    escapeHtml(unsafe) {
        return unsafe
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    },

    showError(message) {
        alert(message);
    },

    showSuccess(message) {
        alert(message);
    }
};