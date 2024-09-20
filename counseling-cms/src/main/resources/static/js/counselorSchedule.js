document.addEventListener('DOMContentLoaded', function () {
    const calendarDays = document.getElementById('calendarDays');
    const monthYearDisplay = document.getElementById('monthYear');
    const prevMonthBtn = document.getElementById('prevMonth');
    const nextMonthBtn = document.getElementById('nextMonth');
    const searchBtn = document.getElementById('searchBtn');
    const counselorNameInput = document.getElementById('counselorName');
    const resultMessage = document.getElementById('resultMessage');

    let currentDate = new Date();
    let currentEvents = {}; // 상담 일정 데이터를 저장하는 객체
    
    // 상담사 이름 검색 후 URL에 파라미터 추가
    searchBtn.addEventListener('click', function () {
        const counselorName = counselorNameInput.value.trim();

        if (counselorName) {
            // URL에 상담사 이름 추가
            const newUrl = new URL(window.location.href);
            newUrl.searchParams.set('name', counselorName); // 'name' 파라미터로 상담사 이름 추가

            // 페이지를 다시 로드하여 상담사 일정을 표시
            window.location.href = newUrl.toString();
        }
    });

    // URL에서 상담사 이름 파라미터 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const counselorName = urlParams.get('name');

    if (counselorName) {
        counselorNameInput.value = counselorName; // 검색창에 기존 이름 유지
        loadSchedules(counselorName);
    }

    // 캘린더를 렌더링하는 함수
    function renderCalendar(date) {
        const year = date.getFullYear();
        const month = date.getMonth();
        const firstDay = new Date(year, month, 1).getDay();
        const lastDate = new Date(year, month + 1, 0).getDate();

        calendarDays.innerHTML = ''; // 캘린더 초기화
        monthYearDisplay.textContent = `${year}년 ${month + 1}월`;

        // 이전 달의 빈 칸 채우기
        for (let i = 0; i < firstDay; i++) {
            const emptyCell = document.createElement('td');
            calendarDays.appendChild(emptyCell);
        }

        // 현재 월의 날짜 그리기
        for (let day = 1; day <= lastDate; day++) {
            const dayCell = document.createElement('td');
            dayCell.textContent = day;

            // 상담사 일정이 해당 날짜에 있는 경우
            if (currentEvents[day]) {
                currentEvents[day].forEach(event => {
                    const eventElement = document.createElement('div');
                    eventElement.classList.add('event');
                    eventElement.textContent = `${event.counselorName}: ${event.reservationDate}`;
                    dayCell.appendChild(eventElement);
                });
                dayCell.classList.add('active'); // 일정이 있는 날짜를 강조
            }

            calendarDays.appendChild(dayCell);

            if ((firstDay + day) % 7 === 0) {
                const row = document.createElement('tr');
                calendarDays.appendChild(row);
            }
        }
    }

    // 이전 달로 이동
    prevMonthBtn.addEventListener('click', function () {
        currentDate.setMonth(currentDate.getMonth() - 1);
        currentEvents = {}; // 이전 일정을 초기화
        renderCalendar(currentDate);
        loadSchedules();
    });

    // 다음 달로 이동
    nextMonthBtn.addEventListener('click', function () {
        currentDate.setMonth(currentDate.getMonth() + 1);
        currentEvents = {}; // 이전 일정을 초기화
        renderCalendar(currentDate);
        loadSchedules();
    });

    // 검색 버튼 클릭 시 일정을 불러옴
    searchBtn.addEventListener('click', function () {
        currentEvents = {}; // 검색할 때도 이전 일정을 초기화
        loadSchedules();
    });

    // 상담사 일정을 불러오는 함수
    function loadSchedules() {
        const counselorName = counselorNameInput.value.trim();
        const month = currentDate.toISOString().slice(0, 7); // 'YYYY-MM' 형식

        // 검색된 상담사 이름이 없으면 전체 상담사의 일정을 가져옴
        const url = counselorName 
            ? `/admin/schedule?name=${encodeURIComponent(counselorName)}&month=${month}`
            : `/admin/schedule/all?month=${month}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                if (data.length === 0) {
                    resultMessage.textContent = "해당 월에 상담 일정이 없습니다.";
                } else {
                    resultMessage.textContent = counselorName 
                        ? `${counselorName}의 일정을 표시합니다.`
                        : "전체 상담사의 일정을 표시합니다.";
                    currentEvents = formatScheduleData(data, currentDate); // 현재 일정을 저장
                    renderCalendar(currentDate); // 캘린더 다시 렌더링
                }
            })
            .catch(error => {
                console.error('Error fetching schedules:', error);
                resultMessage.textContent = '일정을 불러오는 중 오류가 발생했습니다.';
            });
    }

    // 상담 일정을 날짜별로 정렬하는 함수
    function formatScheduleData(data, currentDate) {
        const events = {};
        const currentMonth = currentDate.getMonth();
        const currentYear = currentDate.getFullYear();

        data.forEach(schedule => {
            const reservationDate = new Date(schedule.reservationDate);

            // 상담 일시가 현재 선택된 월과 연도에 맞는 경우만 추가
            if (reservationDate.getMonth() === currentMonth && reservationDate.getFullYear() === currentYear) {
                const day = reservationDate.getDate();
                if (!events[day]) {
                    events[day] = [];
                }
                events[day].push({
                    reservationDate: reservationDate.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
                    counselorName: schedule.counselorName
                });
            }
        });
        return events; // 현재 월과 연도에 맞는 일정만 반환
    }

    // 처음 페이지가 로드될 때 실행
    renderCalendar(currentDate);
    loadSchedules();
});
