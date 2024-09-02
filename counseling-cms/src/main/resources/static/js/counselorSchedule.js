document.addEventListener('DOMContentLoaded', function () {
    const daysContainer = document.getElementById('days');
    const monthYearDisplay = document.getElementById('monthYear');
    const prevMonthBtn = document.getElementById('prevMonth');
    const nextMonthBtn = document.getElementById('nextMonth');

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

    prevMonthBtn.addEventListener('click', function () {
        currentDate.setMonth(currentDate.getMonth() - 1);
        renderCalendar(currentDate);
    });

    nextMonthBtn.addEventListener('click', function () {
        currentDate.setMonth(currentDate.getMonth() + 1);
        renderCalendar(currentDate);
    });

    renderCalendar(currentDate);
});