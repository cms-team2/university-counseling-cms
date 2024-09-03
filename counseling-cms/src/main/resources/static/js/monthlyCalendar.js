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
                events.sort((a, b) => new Date(b.time) - new Date(a.time)); // Sort events by time in descending order
                
                let eventHtml = '';
                events.forEach(event => {
                    const color = getRandomColor();
                    eventHtml += `<div class="event-box" style="background-color: ${color};"><span>${event.time}</span><span> ${event.description}</span></div>`;
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
        }

        function getEventsForDate(year, month, date) {
            // 예시 일정 데이터 (날짜, 시간, 일정 내용)
            const events = {
                '2024-9-1': [
                    { time: '10:00', description: '김지현' }
                ],
                '2024-9-10': [
                    { time: '14:00', description: '박세은' },
                    { time: '09:00', description: '하현수' }
                ],
                '2024-9-15': [
                    { time: '12:00', description: '이윤석' }
                ],
                '2024-9-25': [
                    { time: '17:00', description: '강휘' },
                    { time: '19:00', description: '최한결' }
                ]
            };
            const formattedDate = `${year}-${month + 1}-${date}`;
            return events[formattedDate] || [];
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

        // Initialize calendar
        generateCalendar(currentYear, currentMonth);
        
        function viewWeekly() {
            location.href="/counselor/weekly-calendar";
        }

        function viewMonthly() {
        	 location.href="/counselor/monthly-calendar";
        }
