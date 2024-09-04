document.addEventListener('DOMContentLoaded', function () {
    const daysContainer = document.getElementById('days');
    const monthYearDisplay = document.getElementById('monthYear');
    const prevMonthBtn = document.getElementById('prevMonth');
    const nextMonthBtn = document.getElementById('nextMonth');
    const scheduleView = document.getElementById('scheduleView');
    const dailySchedule = document.getElementById('dailySchedule');
    const monthlySchedule = document.getElementById('monthlySchedule');
    const monthDays = document.getElementById('monthDays');

    let currentDate = new Date();

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
            dayCell.addEventListener('click', () => alert(`${year}년 ${month + 1}월 ${day}일`));
            daysContainer.appendChild(dayCell);
        }
    }

    function renderMonthlySchedule(date) {
        const year = date.getFullYear();
        const month = date.getMonth();
        const firstDay = new Date(year, month, 1).getDay();
        const lastDate = new Date(year, month + 1, 0).getDate();

        const events = {
            11: [{ time: '10:30', name: '하현수', color: 'green' }, { time: '13:00', name: '이윤석', color: 'green' }, { time: '15:30', name: '최한결', color: 'red' }],
            16: [{ time: '11:00', name: '이윤석', color: 'green' }, { time: '11:00', name: '하현수', color: 'green' }],
            19: [{ time: '13:30', name: '김지원', color: 'purple' }, { time: '15:30', name: '박세은', color: 'orange' }],
            23: [{ time: '14:00', name: '박세은', color: 'orange' }],
            25: [{ time: '15:00', name: '김지원', color: 'purple' }, { time: '14:30', name: '강휘', color: 'blue' }],
            30: [{ time: '14:30', name: '강휘', color: 'blue' }]
        };

        monthDays.innerHTML = '';

        let row = document.createElement('tr');

        for (let i = 0; i < firstDay; i++) {
            row.appendChild(document.createElement('td'));
        }

        for (let day = 1; day <= lastDate; day++) {
            const cell = document.createElement('td');
            cell.innerHTML = `<span>${day}</span>`;

            if (events[day]) {
                events[day].forEach(event => {
                    const eventElem = document.createElement('div');
                    eventElem.className = `event ${event.color}`;
                    eventElem.textContent = `${event.time} ${event.name}`;
                    cell.appendChild(eventElem);
                });
            }

            row.appendChild(cell);

            if ((firstDay + day) % 7 === 0 || day === lastDate) {
                monthDays.appendChild(row);
                row = document.createElement('tr');
            }
        }
    }

    prevMonthBtn.addEventListener('click', function () {
        currentDate.setMonth(currentDate.getMonth() - 1);
        renderCalendar(currentDate);
        renderMonthlySchedule(currentDate);
    });

    nextMonthBtn.addEventListener('click', function () {
        currentDate.setMonth(currentDate.getMonth() + 1);
        renderCalendar(currentDate);
        renderMonthlySchedule(currentDate);
    });

    renderCalendar(currentDate);
    renderMonthlySchedule(currentDate);

    // 스케줄 보기 옵션 변경 시 테이블 전환
    scheduleView.addEventListener('change', function () {
        if (scheduleView.value === 'daily') {
            dailySchedule.style.display = 'table';
            monthlySchedule.style.display = 'none';
        } else if (scheduleView.value === 'monthly') {
            dailySchedule.style.display = 'none';
            monthlySchedule.style.display = 'block';
        }
    });
});