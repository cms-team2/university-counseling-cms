		let currentDate = new Date();
        let currentWeek = 0;
        const studentColors = {};

        function getInitialWeek() {
            const firstDayOfCurrentMonth = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
            const dayOfWeek = firstDayOfCurrentMonth.getDay();
            const diff = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
            const firstMondayOfMonth = new Date(firstDayOfCurrentMonth);
            firstMondayOfMonth.setDate(firstDayOfCurrentMonth.getDate() + diff);
            const today = new Date();
            const daysFromFirstMonday = Math.floor((today - firstMondayOfMonth) / (24 * 60 * 60 * 1000));
            currentWeek = Math.floor(daysFromFirstMonday / 7);
        }

  function updateCalendar() {
    const firstDayOfWeek = getFirstDayOfWeek(currentDate, currentWeek);
    const dayHeaders = document.querySelector('.calendar-table thead tr');
    const appointmentsBody = document.getElementById('appointments');
    const weekTitle = document.getElementById('week-title');

    dayHeaders.innerHTML = '<th class="time-column">시간</th>'; // 시간 열

    appointmentsBody.innerHTML = '';

    const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
    const today = new Date().toISOString().split('T')[0]; // 오늘 날짜를 YYYY-MM-DD 형식으로
    let todayIndex = null;

    // 날짜 헤더 생성
    for (let i = 0; i < 7; i++) {
        const date = new Date(firstDayOfWeek);
        date.setDate(date.getDate() + i);
        const dayOfWeek = date.getDay();
        if (dayOfWeek === 0 || dayOfWeek === 6) {
            continue; // 토요일과 일요일은 제외
        }
        const month = date.getMonth() + 1; // 월 (0부터 시작하므로 +1)
        const day = date.getDate(); // 일
        const dateStr = date.toISOString().split('T')[0]; // YYYY-MM-DD 형식
        console.log(dateStr);
        console.log(today);
        
        if (dateStr === today) {
            todayIndex = i-1; // 오늘 날짜의 인덱스를 저장
      
		}
	   dayHeaders.innerHTML += `<th>${month}/${day}(${daysOfWeek[dayOfWeek]})</th>`;
			
    }

    // 시간별 상담 내용 생성
    for (let hour = 9; hour <= 18; hour++) {
        let rowHtml = `<tr><td class="time-column">${hour}:00 ~</td>`;
        for (let i = 0; i < 7; i++) {
            const date = new Date(firstDayOfWeek);
            date.setDate(date.getDate() + i);
            const dayOfWeek = date.getDay();
            if (dayOfWeek === 0 || dayOfWeek === 6) {
                continue; // 토요일과 일요일은 제외
            }
            const dateStr = date.toISOString().split('T')[0];
            
            const dailyAppointments = getAppointmentsForWeek(firstDayOfWeek)[dateStr] || [];
            const hourlyAppointments = dailyAppointments.filter(appointment => parseInt(appointment.time.split(':')[0]) === hour);
            const sortedAppointments = hourlyAppointments
                .sort((a, b) => a.time.localeCompare(b.time));
            const appointmentHtml = sortedAppointments
                .map(appointment => {
                    const color = getStudentColor(appointment.name);
                    return `
                        <div class="appointment" style="background-color: ${color}" data-bs-toggle="modal" data-bs-target="#eventModal" data-event='${JSON.stringify(event)}'>

                        <span class="student_info">
                            <span class="appointment-time">${appointment.time}</span>
                            <span class="appointment-time">${appointment.name}</span>
                        </span>
                            <span class="appointment-type info">${appointment.type}</span>
                        </div>
                    `;
                }).join('');
            
            const isTodayCell = todayIndex === i? 'today' : '';
            rowHtml += `<td class="${isTodayCell}">${appointmentHtml}</td>`;
        }
        rowHtml += '</tr>';
        appointmentsBody.innerHTML += rowHtml;
    }

    const monthName = firstDayOfWeek.toLocaleString('default', { month: 'long' });
    const weekNumber = getWeekNumber(firstDayOfWeek);
    weekTitle.textContent = `${monthName} ${weekNumber}째 주`;
}

        function getFirstDayOfWeek(date, weekOffset) {
            const firstDayOfMonth = new Date(date.getFullYear(), date.getMonth(), 1);
            const dayOfWeek = firstDayOfMonth.getDay();
            const diff = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
            const firstMonday = new Date(firstDayOfMonth);
            firstMonday.setDate(firstDayOfMonth.getDate() + diff);
            return new Date(firstMonday.setDate(firstMonday.getDate() + (weekOffset * 7)));
        }

        function getWeekNumber(date) {
            const firstDayOfMonth = new Date(date.getFullYear(), date.getMonth(), 1);
            const dayOfWeek = firstDayOfMonth.getDay();
            const daysFromStart = Math.floor((date - firstDayOfMonth) / (24 * 60 * 60 * 1000));
            return Math.ceil((daysFromStart + firstDayOfMonth.getDay() + 1) / 7);
        }

        function moveWeek(offset) {
            currentWeek += offset;
            updateCalendar();
        }

        function viewWeekly() {
            location.href="/counselor/weekly-calendar";
        }

        function viewMonthly() {
        	 location.href="/counselor/monthly-calendar";
        }

        function getAppointmentsForWeek(startDate) {
            const appointments = {
                '2024-09-01': [
                    { time: '09:00', type: '개인 상담', name: '학생 A' },
                    { time: '14:00', type: '그룹 상담', name: '학생 B' }
                ],
                '2024-09-02': [
                    { time: '10:00', type: '개인 상담', name: '학생 C' }
                ],
                '2024-09-03': [
                    { time: '11:30', type: '개인 상담', name: '학생 A' }
                ],
                // 추가 날짜 데이터...
            };
            return appointments;
        }

        function getStudentColor(studentName) {
            if (!studentColors[studentName]) {
                studentColors[studentName] = getRandomColor();
            }
            return studentColors[studentName];
        }

        function getRandomColor() {
            const letters = '0123456789ABCDEF';
            let color = '#';
            for (let i = 0; i < 6; i++) {
                color += letters[Math.floor(Math.random() * 16)];
            }
            return color;
        }

        getInitialWeek();
        updateCalendar();