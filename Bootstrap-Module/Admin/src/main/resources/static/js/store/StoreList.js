const StorePage = {
    // 상태 관리
    state: {
        currentPage: 0,
        size: 10,
        totalPages: 0
    },

    // 초기화
    init() {
        StoreUpdate.init();
        this.initializeEventListeners();
        this.loadStores();
    },

    // 이벤트 리스너 초기화
    initializeEventListeners() {
        // 검색 버튼 이벤트
        document.getElementById('searchButton')?.addEventListener('click', () => {
            this.handleSearch();
        });

        // 검색 입력 필드 엔터 키 이벤트
        document.getElementById('searchInput')?.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();
                this.handleSearch();
            }
        });
    },

    // 가게 목록 로드
    async loadStores() {
        try {
            this.showLoading(true);
            console.log(this.state.currentPage, this.state.size)
            const data = await StoreApi.fetchStores(this.state.currentPage, this.state.size);
            this.renderStoreTable(data);
            this.renderPagination(data);
            this.state.totalPages = data.totalPages;
        } catch (error) {
            console.error('Error:', error);
            this.showError('데이터를 불러오는 중 오류가 발생했습니다.');
        } finally {
            this.showLoading(false);
        }
    },

    // 테이블 렌더링
    renderStoreTable(stores) {
        const tableBody = document.getElementById('storeTableBody');
        if (!tableBody) return;

        tableBody.innerHTML = '';

        if (stores.length === 0) {
            const emptyRow = document.createElement('tr');
            emptyRow.innerHTML = `
                <td colspan="5" class="text-center">등록된 가게가 없습니다.</td>
            `;
            tableBody.appendChild(emptyRow);
            return;
        }

        const storeContent = stores.content;

        storeContent.forEach(store => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${store.id}</td>
                <td>${this.escapeHtml(store.name)}</td>
                <td>${this.escapeHtml(store.region)}</td>
                <td>
                    <span class="badge ${store.isPartner ? 'bg-success' : 'bg-secondary'}">
                        ${store.isPartner ? '제휴중' : '미제휴'}
                    </span>
                </td>
                <td>
                    <button class="btn btn-sm btn-outline-primary me-1" onclick="StorePage.editStore('${store.id}')">
                        <i class="bi bi-pencil"></i>
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="StorePage.deleteStore('${store.id}')">
                        <i class="bi bi-trash"></i>
                    </button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    },

    // 페이지네이션 렌더링
    renderPagination(data) {
        const pagination = document.getElementById('pagination');
        if (!pagination) return;

        pagination.innerHTML = '';

        // 이전 페이지 버튼
        this.appendPageItem(pagination, '이전', this.state.currentPage > 0, () => {
            this.goToPage(this.state.currentPage - 1);
        });

        // 페이지 번호 버튼들
        for (let i = 0; i < data.totalPages; i++) {
            const pageItem = document.createElement('li');
            pageItem.className = `page-item ${i === this.state.currentPage ? 'active' : ''}`;
            pageItem.innerHTML = `
                <button class="page-link" onclick="StorePage.goToPage(${i})">${i + 1}</button>
            `;
            pagination.appendChild(pageItem);
        }

        // 다음 페이지 버튼
        this.appendPageItem(pagination, '다음', this.state.currentPage < data.totalPages - 1, () => {
            this.goToPage(this.state.currentPage + 1);
        });
    },

    // 페이지네이션 아이템 추가
    appendPageItem(pagination, text, enabled, onClick) {
        const li = document.createElement('li');
        li.className = `page-item ${!enabled ? 'disabled' : ''}`;
        const button = document.createElement('button');
        button.className = 'page-link';
        button.textContent = text;
        if (enabled) {
            button.onclick = onClick;
        }
        li.appendChild(button);
        pagination.appendChild(li);
    },

    // 페이지 이동
    async goToPage(page) {
        this.state.currentPage = page;
        await this.loadStores();
    },

    // 가게 정보 수정
    async editStore(id) {
        try {
            this.showLoading(true);
            const storeData = await StoreApi.fetchStore(id);
            StoreUpdate.fillModalWithData(storeData);
            StoreUpdate.showEditModal();
            StoreUpdate.toggleSubPage(true);
        } catch (error) {
            console.error('Error:', error);
            this.showError('가게 정보를 불러오는 중 오류가 발생했습니다.');
        } finally {
            this.showLoading(false);
        }
    },

    // 가게 삭제
    async deleteStore(id) {
        if (!confirm('정말로 이 가게를 삭제하시겠습니까?')) return;

        try {
            this.showLoading(true);
            await StoreApi.deleteStore(id);
            this.showSuccess('가게가 성공적으로 삭제되었습니다.');
            await this.loadStores();
        } catch (error) {
            console.error('Error:', error);
            this.showError('가게 삭제 중 오류가 발생했습니다.');
        } finally {
            this.showLoading(false);
        }
    },

    // 검색 처리
    async handleSearch() {
        const searchInput = document.getElementById('searchInput');
        if (!searchInput) return;

        const searchTerm = searchInput.value.trim();
        this.state.currentPage = 0; // 검색 시 첫 페이지로 이동
        // 검색 로직 구현 필요
        await this.loadStores();
    },

    // 유틸리티 함수들
    showLoading(show) {
        const spinner = document.getElementById('loadingSpinner');
        if (spinner) {
            spinner.classList.toggle('d-none', !show);
        }
    },

    showError(message) {
        alert(message);
    },

    showSuccess(message) {
        alert(message);
    },

    escapeHtml(unsafe) {
        return unsafe
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    },

    // 날짜 포맷팅
    formatDate(dateString) {
        const options = {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        };
        return new Date(dateString).toLocaleDateString('ko-KR', options);
    }
};

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    StorePage.init();
});