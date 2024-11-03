const StoreActiveTime = {
    activeTimeModal: null,
    currentActiveTimeId: null,
    currentStoreId: null,
    timePickers: [],
    dayTypes: [],

    init(storeId) {
        this.activeTimeModal = new bootstrap.Modal(document.getElementById('activetimeModal'));
        this.currentStoreId = storeId;
        this.initializeTimePickers();
        this.loadDayTypes();
        this.loadActiveTimes();
        this.initializeEvents();
    },

    initializeEvents() {
        document.getElementById('activetimeForm').addEventListener('submit', (e) => {
            e.preventDefault();
            this.saveActiveTime();
        });

        // 모달이 닫힐 때 초기화
        document.getElementById('activetimeModal').addEventListener('hidden.bs.modal', () => {
            this.resetForm();
        });
    },

    initializeTimePickers() {
        // flatpickr 제거하고 기본 time input 사용
        document.getElementById('startAt').value = '09:00';
        document.getElementById('endAt').value = '18:00';
    },

    async loadActiveTimes() {
        try {
            const activeTimes = await StoreActiveTimeApi.getActiveTimes(this.currentStoreId);
            this.renderActiveTimeTable(activeTimes);
        } catch (error) {
            console.error('Error:', error);
            this.showError('운영 시간 목록을 불러오는데 실패했습니다.');
        }
    },

    async loadDayTypes() {
        try {
            this.dayTypes = await StoreActiveTimeApi.getActiveTimeTypes(this.currentStoreId);
            this.renderDayTypes();
        } catch (error) {
            console.error('Error:', error);
            this.showError('요일 목록을 불러오는데 실패했습니다.');
        }
    },

    renderActiveTimeTable(activeTimes) {
        const tableBody = document.getElementById('activetimeTableBody');
        tableBody.innerHTML = '';

        if (activeTimes.length === 0) {
            tableBody.innerHTML = `
                <tr>
                    <td colspan="5" class="text-center">등록된 운영 시간이 없습니다.</td>
                </tr>
            `;
            return;
        }

        activeTimes.forEach(activeTime => {
            const row = document.createElement('tr');
            row.innerHTML = `
                    <td>${this.formatDayOfWeek(activeTime.dayOfWeek)}</td>
                    <td>${this.formatDateTime(activeTime.startAt)}</td>
                    <td>${this.formatDateTime(activeTime.endAt)}</td>
                    <td>
                        <span class="badge ${activeTime.isActive ? 'bg-success' : 'bg-secondary'}">
                            ${activeTime.isActive ? '운영중' : '운영종료'}
                        </span>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-outline-primary me-1"
                            onclick="StoreActiveTime.openEditModal('${activeTime.id}')">
                            <i class="bi bi-pencil"></i>
                        </button>
                        <button class="btn btn-sm btn-outline-danger" 
                            onclick="StoreActiveTime.deleteActiveTime('${activeTime.id}')">
                            <i class="bi bi-trash"></i>
                        </button>
                    </td>
                `;
            tableBody.appendChild(row);
        });
    },

    formatDateTime(time) {
        try {
            // HH:mm 형식으로 변환
            return time.substring(0, 5);
        } catch (error) {
            console.error('시간 포맷팅 에러:', error);
            return time;
        }
    },

    renderDayTypes() {
        const select = document.getElementById('dayOfWeek');
        select.innerHTML = '<option value="">요일 선택</option>';

        this.dayTypes.forEach(type => {
            const option = document.createElement('option');
            option.value = type;
            option.textContent = this.formatDayOfWeek(type);
            select.appendChild(option);
        });
    },

    formatDayOfWeek(day) {
        const dayMap = {
            'MONDAY': '월요일',
            'TUESDAY': '화요일',
            'WEDNESDAY': '수요일',
            'THURSDAY': '목요일',
            'FRIDAY': '금요일',
            'SATURDAY': '토요일',
            'SUNDAY': '일요일'
        };
        return dayMap[day] || day;
    },

    openAddModal() {
        this.currentActiveTimeId = null;
        document.getElementById('activetimeModalLabel').textContent = '운영 시간 추가';
        this.activeTimeModal.show();
    },

    async openEditModal(activeTimeId) {
        try {
            const activeTime = await StoreActiveTimeApi.getActiveTime(this.currentStoreId, activeTimeId);
            this.currentActiveTimeId = activeTimeId;
            document.getElementById('activetimeModalLabel').textContent = '운영 시간 수정';
            document.getElementById('dayOfWeek').value = activeTime.dayOfWeek;
            document.getElementById('startAt').value = activeTime.startAt.substring(0, 5);
            document.getElementById('endAt').value = activeTime.endAt.substring(0, 5);
            document.getElementById('isActive').checked = activeTime.isActive;
            console.log(activeTime);
            this.activeTimeModal.show();
        } catch (error) {
            console.error('Error:', error);
            this.showError('운영 시간 정보를 불러오는데 실패했습니다.');
        }
    },

    async saveActiveTime() {
        if (!this.validateActiveTimeForm()) return;

        const activeTimeData = {
            dayOfWeek: document.getElementById('dayOfWeek').value,
            startAt: document.getElementById('startAt').value + ':00',
            endAt: document.getElementById('endAt').value + ':00',
            isActive: document.getElementById('isActive').checked
        };

        try {
            if (this.currentActiveTimeId) {
                await StoreActiveTimeApi.updateActiveTime(this.currentStoreId, this.currentActiveTimeId, activeTimeData);
            } else {
                await StoreActiveTimeApi.createActiveTime(this.currentStoreId, activeTimeData);
            }
            this.loadActiveTimes();
            this.activeTimeModal.hide();
            this.showSuccess('운영 시간이 저장되었습니다.');
        } catch (error) {
            console.error('Error:', error);
            this.showError('운영 시간 저장에 실패했습니다.');
        }
    },

    validateActiveTimeForm() {
        const dayOfWeek = document.getElementById('dayOfWeek').value;
        const startAt = document.getElementById('startAt').value;
        const endAt = document.getElementById('endAt').value;
        const isActive = document.getElementById('isActive').checked;

        if (!dayOfWeek) {
            this.showError('요일을 선택해주세요.');
            return false;
        }
        if (!startAt || !endAt) {
            this.showError('시작 시간과 종료 시간을 모두 선택해주세요.');
            return false;
        }

        return true;
    },

    resetForm() {
        document.getElementById('activetimeForm').reset();
        this.timePickers.forEach(picker => picker.clear());
        this.currentActiveTimeId = null;
    },

    showError(message) {
        alert(message);
    },

    showSuccess(message) {
        alert(message);
    }
};