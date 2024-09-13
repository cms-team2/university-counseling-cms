document.addEventListener('DOMContentLoaded', function () {
    const daysContainer = document.getElementById('days');
    const monthYearDisplay = document.getElementById('monthYear');
    const prevMonthBtn = document.getElementById('prevMonth');
    const nextMonthBtn = document.getElementById('nextMonth');
    const searchBtn = document.getElementById('searchBtn');
    const counselorNameInput = document.getElementById('counselorName');
    const resultMessage = document.getElementById('resultMessage');

    let currentDate = new Date();
    let currentEvents = {};

    function renderCalendar(date) {
        const year = date.getFullYear();
        const month = date.getMonth();
        const firstDay = new Date(year, month, 1).getDay();
        const lastDate = new Date(year, month + 1, 0).getDate();

        daysContainer.innerHTML = '';
        monthYearDisplay.textContent = `${year}년 ${month + 1}월`;

        // 이전 달의 빈 칸
        for (let i = 0; i < firstDay; i++) {
            const emptyCell = document.createElement('li');
            daysContainer.appendChild(emptyCell);
        }

        // 날짜 그리기
        for (let day = 1; day <= lastDate; day++) {
            const dayCell = document.createElement('li');
            dayCell.textContent = day;

            // 상담사 일정이 있을 경우 해당 날짜 강조
            if (currentEvents[day]) {
                dayCell.classList.add('active');
                dayCell.addEventListener('click', () => showScheduleDetails(day));
            }

            daysContainer.appendChild(dayCell);
        }
    }

    prevMonthBtn.addEventListener('click', function () {
        currentDate.setMonth(currentDate.getMonth() - 1);
        renderCalendar(currentDate);
    });

    nextMonthBtn.addEventListener('click', function () {
        currentDate.setMonth(currentDate.getMonth() + 1);
        renderCalendar(currentDate);
    });

    renderCalendar(currentDate);

    // 상담사 이름 검색
    searchBtn.addEventListener('click', function () {
        const counselorName = counselorNameInput.value.trim();
        if (!counselorName) {
            resultMessage.textContent = "상담사 이름을 입력해주세요.";
            return;
        }

        fetch(`/admin/schedule?month=${encodeURIComponent(counselorName)}`)
            .then(response => response.json())
            .then(data => {
                if (data.length === 0) {
                    resultMessage.textContent = "해당 상담사의 일정이 없습니다.";
                } else {
                    resultMessage.textContent = `${counselorName}의 일정을 표시합니다.`;
                    currentEvents = formatScheduleData(data); 
                    renderCalendar(currentDate); 
                }
            })
            .catch(error => {
                console.error("Error fetching schedule:", error);
                resultMessage.textContent = "일정을 불러오는 중 오류가 발생했습니다.";
            });
    });

    function formatScheduleData(data) {
        const events = {};
        data.forEach(schedule => {
            const date = new Date(schedule.reservationDate).getDate();
            if (!events[date]) {
                events[date] = [];
            }
            events[date].push({
                reservationDate: new Date(schedule.reservationDate).toLocaleTimeString(),
                counselorName: schedule.counselorName,
                color: 'green'
            });
        });
        return events;
    }

    function showScheduleDetails(day) {
        const events = currentEvents[day];
        if (events) {
            alert(`상담 일정: ${events.map(e => `${e.reservationDate} ${e.counselorName}`).join(', ')}`);
        }
    }
});
