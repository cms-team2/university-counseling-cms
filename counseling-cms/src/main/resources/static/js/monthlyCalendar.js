let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth();

function generateCalendar(year, month) {
    const calendarBody = document.getElementById('calendar-body');
    const title = document.getElementById('calendar-title');
    const firstDay = new Date(year, month, 1).getDay();
    const lastDate = new Date(year, month + 1, 0).getDate();
    const currentDate = new Date();

    title.textContent = `${year}년 ${month + 1}월`;

    let html = '<tr>';
    
    // Empty cells before the first day
    for (let i = 0; i < firstDay; i++) {
        html += '<td></td>';
    }

    // Dates
    for (let date = 1; date <= lastDate; date++) {
        if ((firstDay + date - 1) % 7 === 0 && date !== 1) {
            html += '</tr><tr>';
        }
        
        const isToday = (year === currentDate.getFullYear() && month === currentDate.getMonth() && date === currentDate.getDate());
        const events = getEventsForDate(year, month, date);
        
        let eventHtml = '';
        events.forEach(event => {
            const color = getEventColor(event.description);
            eventHtml += `
                <div class="event-box" style="background-color: ${color};" data-bs-toggle="modal" data-bs-target="#eventModal" data-event='${JSON.stringify(event)}'>
                    <span>${event.time}</span><span> ${event.description}</span>
                </div>`;
        });

        html += `<td${isToday ? ' class="today"' : ''} data-date="${year}-${month + 1}-${date}"><div>${date}</div>${eventHtml}</td>`;
    }

    // Fill the remaining cells
    const remainingCells = (7 - (firstDay + lastDate) % 7) % 7;
    for (let i = 0; i < remainingCells; i++) {
        html += '<td></td>';
    }

    html += '</tr>';
    calendarBody.innerHTML = html;

    // Attach event listener to event boxes
    document.querySelectorAll('.event-box').forEach(box => {
        box.addEventListener('click', (e) => {
            const event = JSON.parse(e.currentTarget.getAttribute('data-event'));
            showEventDetails(event);
        });
    });
}

function getEventsForDate(year, month, date) {
    // Example event data (date, time, event description)
    const events = {
        '2024-9-1': [
            { time: '10:00', description: '김지현' }
        ],
        '2024-9-10': [
            { time: '09:00', description: '박세은' },
            { time: '14:00', description: '하현수' }
        ],
        '2024-9-15': [
            { time: '12:00', description: '이윤석' }
        ],
        '2024-9-25': [
            { time: '19:00', description: '강휘' },
            { time: '14:00', description: '김지현' },
            { time: '17:00', description: '최한결' }
        ]
    };
    const formattedDate = `${year}-${month + 1}-${date}`;
    const dateEvents = events[formattedDate] || [];

    // Sort events by time in ascending order
    return dateEvents.sort((a, b) => new Date(`1970-01-01T${a.time}:00`) - new Date(`1970-01-01T${b.time}:00`));
}

const colorCache = {};

function getEventColor(description) {
    // Use cached color if available
    if (colorCache[description]) {
        return colorCache[description];
    }

    // Generate a new color and cache it
    const color = getRandomColor();
    colorCache[description] = color;
    return color;
}

function getRandomColor() {
    // Generate a random color
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

function showEventDetails(event) {
    const modalBody = document.getElementById('modal-event-details');
    modalBody.innerHTML = `
 <table class="form_write">
                    <tbody>
                        <tr>
                            <td><label for="name">이름</label></td>
                            <td>
                                <input type="text" id="name" name="name" value="${event.description}" readonly>
                            </td>
                            <td><label for="studentId">학번</label></td>
                            <td>
                                <input type="text" id="studentId" name="studentId" value="" readonly>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="department">학과</label></td>
                            <td>
                                <input type="text" id="department" name="department" value="" readonly>
                            </td>
                            <td><label for="contact">연락처</label></td>
                            <td>
                                <input type="text" id="contact" name="contact" value="" readonly>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="appointmentTime">상담 일시</label></td>
                            <td>
                                <input type="text" id="appointmentTime" name="appointmentTime" value="${event.time}" readonly>
                            </td>
                            <td><label for="consultationType">상담분류</label></td>
                            <td>
                                <input type="text" id="consultationType" name="consultationType" value="" readonly>
                            </td>
                        </tr>
                    </tbody>
                </table>
    `;
}

function updateCalendar(monthOffset) {
    currentMonth += monthOffset;
    if (currentMonth > 11) {
        currentMonth = 0;
        currentYear++;
    } else if (currentMonth < 0) {
        currentMonth = 11;
        currentYear--;
    }
    generateCalendar(currentYear, currentMonth);
}

document.getElementById('prev-month').addEventListener('click', () => updateCalendar(-1));
document.getElementById('next-month').addEventListener('click', () => updateCalendar(1));

generateCalendar(currentYear, currentMonth);
