const StoreUpdate = {
    modal: null,
    currentStoreId: null,
    categories: [], // 카테고리 데이터 저장
    searchTimeout: null, // 검색 디바운싱을 위한 타이머

    init() {
        this.modal = new bootstrap.Modal(document.getElementById('editStoreModal'));
        this.initializeEvents();
        this.loadCategories(); // 카테고리 데이터 로드
    },

    initializeEvents() {
        // 모달 초기화
        document.getElementById('editStoreModal').addEventListener('hidden.bs.modal', () => {
            this.resetForm();
        });

        // 이미지 URL 변경 시 미리보기
        document.getElementById('imgUrl').addEventListener('change', (e) => {
            this.updateImagePreview(e.target.value);
        });

        // 폼 제출 이벤트
        document.getElementById('editStoreForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveChanges();
        });


        const searchInput = document.getElementById('categorySearch');
        searchInput.addEventListener('input', (e) => {
            clearTimeout(this.searchTimeout);
            this.searchTimeout = setTimeout(() => {
                this.filterCategories(e.target.value);
            }, 300); // 300ms 디바운싱
        });

        // 검색 입력 필드 클릭시 전체 목록 표시
        searchInput.addEventListener('click', () => {
            this.filterCategories('');
        });

        // 문서 클릭시 드롭다운 닫기
        document.addEventListener('click', (e) => {
            if (!e.target.closest('.dropdown')) {
                this.hideDropdown();
            }
        });
    },

    updateImagePreview(url) {
        const previewImage = document.getElementById('previewImage');
        if (url) {
            previewImage.src = url;
            previewImage.classList.remove('d-none');
        } else {
            previewImage.classList.add('d-none');
        }
    },

    fillModalWithData(storeData) {
        this.currentStoreId = storeData.id;
        document.getElementById('storeId').value = storeData.id;
        document.getElementById('storeName').value = storeData.name;
        document.getElementById('imgUrl').value = storeData.imgUrl;
        document.getElementById('introduction').value = storeData.introduction;
        document.getElementById('longitude').value = storeData.longitude;
        document.getElementById('latitude').value = storeData.latitude;
        document.getElementById('isRegistered').checked = storeData.isRegistered;


        // 카테고리 설정
        if (storeData.storeCategoryId) {
            const category = this.categories.find(c => c.id === storeData.storeCategoryId);
            if (category) {
                this.selectCategory(category);
            }
        }


        // 가게 메뉴 조회
        StoreMenu.init(storeData.id);
        // 가게 연락처 조회
        StoreContact.init(storeData.id);
        // 가게 할인 조회
        StoreDiscount.init(storeData.id);


        this.updateImagePreview(storeData.imgUrl);
    },

    getFormData() {
        return {
            id: this.currentStoreId,
            name: document.getElementById('storeName').value.trim(),
            imgUrl: document.getElementById('imgUrl').value.trim(),
            introduction: document.getElementById('introduction').value.trim(),
            longitude: parseFloat(document.getElementById('longitude').value),
            latitude: parseFloat(document.getElementById('latitude').value),
            isRegistered: document.getElementById('isRegistered').checked,
            storeCategoryId: document.getElementById('selectedCategoryId').value,
        };
    },

    validateForm() {
        const data = this.getFormData();

        if (!data.name) {
            this.showError('가게명을 입력해주세요.');
            return false;
        }
        if (!data.imgUrl) {
            this.showError('이미지 URL을 입력해주세요.');
            return false;
        }
        if (!data.introduction) {
            this.showError('가게 소개를 입력해주세요.');
            return false;
        }
        if (isNaN(data.longitude) || isNaN(data.latitude)) {
            this.showError('올바른 위치 정보를 입력해주세요.');
            return false;
        }
        if (!data.storeCategoryId) {
            this.showError('가게 유형을 선택해주세요.');
            return false;
        }

        return true;
    },

    async saveChanges() {
        if (!this.validateForm()) return;

        try {
            const storeData = this.getFormData();
            await StoreApi.updateStore(this.currentStoreId, storeData);
            this.showSuccess('가게 정보가 성공적으로 수정되었습니다.');
            this.hide();
            StorePage.loadStores();
        } catch (error) {
            console.error('Error saving store:', error);
            this.showError('가게 정보 저장 중 오류가 발생했습니다.');
        }
    },

    resetForm() {
        document.getElementById('editStoreForm').reset();
        document.getElementById('previewImage').classList.add('d-none');
        this.currentStoreId = null;
    },

    async loadCategories() {
        try {
            const categories = await StoreApi.fetchCategories();
            this.categories = categories;
            this.filterCategories(''); // 초기 목록 표시
        } catch (error) {
            console.error('Error loading categories:', error);
            this.showError('카테고리 목록을 불러오는 중 오류가 발생했습니다.');
        }
    },

    // 카테고리 검색 필터링
    filterCategories(searchTerm) {
        const dropdown = document.getElementById('categoryDropdown');
        const filteredCategories = this.categories.filter(category =>
            category.name.toLowerCase().includes(searchTerm.toLowerCase())
        );

        dropdown.innerHTML = '';

        if (filteredCategories.length === 0) {
            dropdown.innerHTML = '<li><span class="dropdown-item text-muted">검색 결과가 없습니다</span></li>';
            return;
        }

        filteredCategories.forEach(category => {
            const li = document.createElement('li');
            li.innerHTML = `
                    <a class="dropdown-item" href="#" data-id="${category.id}">
                        ${this.highlightMatch(category.name, searchTerm)}
                    </a>
                `;
            li.querySelector('a').addEventListener('click', (e) => {
                e.preventDefault();
                this.selectCategory(category);
            });
            dropdown.appendChild(li);
        });

        this.showDropdown();
    },

    highlightMatch(text, searchTerm) {
        if (!searchTerm) return text;
        const regex = new RegExp(`(${searchTerm})`, 'gi');
        return text.replace(regex, '<mark>$1</mark>');
    },

    selectCategory(category) {
        document.getElementById('categorySearch').value = category.name;
        document.getElementById('selectedCategoryId').value = category.id;
        document.getElementById('selectedCategory').textContent = `선택된 카테고리: ${category.name}`;
        this.hideDropdown();
    },

    showDropdown() {
        const dropdown = document.getElementById('categoryDropdown');
        dropdown.classList.add('show');
        dropdown.parentElement.classList.add('show');
    },

    hideDropdown() {
        const dropdown = document.getElementById('categoryDropdown');
        dropdown.classList.remove('show');
        dropdown.parentElement.classList.remove('show');
    },

    show() {
        this.modal.show();
    },

    hide() {
        this.modal.hide();
    },

    showError(message) {
        alert(message);
    },

    showSuccess(message) {
        alert(message);
    }
};