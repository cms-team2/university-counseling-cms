let severToday;
let tomorrow;
let currentDate = new Date();
let currentWeek = 0;
const studentColors = {};
const newDate = document.querySelector("#new_date");
const newTime=document.querySelector("#new_time");

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

	let dateArray = [];
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

		
		if (dateStr === today) {
			todayIndex = i; // 오늘 날짜의 인덱스를 저장
		}
		dayHeaders.innerHTML += `<th>${month}/${day}(${daysOfWeek[dayOfWeek]})</th>`;
		dateArray.push(date.getFullYear() + "-" + month + "-" + day);
	}
	
	let startdate = formatDate(dateArray[0]);
	let enddate = formatDate(dateArray[dateArray.length - 1]);
	
	fetch("/counselor/weekly-data", {
	    method: "POST",
	    headers: {
	        "Content-Type": "application/json"
	    },
		body: JSON.stringify({ startdate : startdate , enddate : enddate })
	})
	.then(response => response.json())
	.then(data => {
		serverToday=data.today;
		tomorrow=serverToday.split("-")[0]+"-"+serverToday.split("-")[1]+"-"+(parseInt(serverToday.split("-")[2], 10)+1);
		
		// input의 min 속성에 오늘 날짜를 설정합니다.
		newDate.setAttribute('min', tomorrow);
		
		// 시간별 상담 내용 생성
		for (let hour = 9; hour <= 17; hour++) {
			let rowHtml = `<tr><td class="time-column">${hour}:00 ~</td>`;
			for (let i = 0; i < 7; i++) {
				const date = new Date(firstDayOfWeek);
				date.setDate(date.getDate() + i);
				const dayOfWeek = date.getDay();

				if (dayOfWeek === 0 || dayOfWeek === 6) {
					continue; // 토요일과 일요일은 제외
				}

				const dateStr = date.toISOString().split('T')[0];
				const dailyAppointments = getAppointmentsForWeek(firstDayOfWeek,data)[dateStr] || [];
				const hourlyAppointments = dailyAppointments.filter(appointment => parseInt(appointment.time.split(':')[0]) === hour);
				
				const sortedAppointments = hourlyAppointments.sort((a, b) => a.time.localeCompare(b.time));
				const appointmentHtml = sortedAppointments.map(appointment => {
					const color = getStudentColor(appointment.name);
					return `
		            <div class="appointment" style="background-color: ${color}" data-bs-toggle="modal" data-bs-target="#eventModal" data-event='${JSON.stringify(appointment)}'>

		            <span class="student_info">
		                <span class="appointment-time">${appointment.time}</span>
		                <span class="appointment-name">${appointment.name}</span>
		            </span>
		                <span class="appointment-type info">${appointment.type}</span>
		            </div>
		            `;
				}).join('');

				const isTodayCell = todayIndex === i ? 'today' : '';
				rowHtml += `<td class="${isTodayCell}">${appointmentHtml}</td>`;
			}
			rowHtml += '</tr>';
			appointmentsBody.innerHTML += rowHtml;
			
			document.querySelectorAll('.appointment').forEach(box => {
				box.addEventListener('click', (e) => {
					const event = JSON.parse(e.currentTarget.getAttribute('data-event'));
					showEventDetails(event);
				});
			});
			
		}
		
	})
	.catch(error => {
	    console.error('Error:', error);
	});	

	const monthName = firstDayOfWeek.toLocaleString('default', { month: 'long' });
	const weekNumber = getWeekNumber(firstDayOfWeek);
	weekTitle.textContent = `${monthName} ${weekNumber}째 주`;
}

function showEventDetails(event) {
	
    const modalBody = document.getElementById('modal-event-details');
    modalBody.querySelector('#studentName').value = event.name;
	modalBody.querySelector('#studentNo').value = event.studentNo;
	modalBody.querySelector('#studentMajor').value = event.studentMajor;
	modalBody.querySelector('#studentTelNo').value = event.studentTelNo;
	modalBody.querySelector('#consultationDate').value = event.consultationDate.slice(0, 16);
	modalBody.querySelector('#consultationCategory').value = event.type;
	modalBody.querySelector('#consultationWay').value = event.consultationWay;
	
	const modalfooter = document.getElementById('modal-event-footers');
	const recordLink = modalfooter.querySelector("#record");
	const modifyLink = modalfooter.querySelector("#weeklyModify");
	recordLink.href = "/counselor/writeCounselingRecord?applyNo="+event.applyNo;
	
	modalBody.querySelector("#changeDate").addEventListener("click",function(){
		this.parentNode.style.display="none"
		modalBody.querySelector("#newDate").style.display="flex";
		modalBody.querySelector("#modifyCK").value="Y";
	})

	modifyLink.onclick = function() {
	    var modify_date = modalBody.querySelector("#new_date").value;
	    var modify_time = modalBody.querySelector("#new_time").value;

	    if (modalBody.querySelector("#modifyCK").value == "N") {
	        alert("변경하기 버튼을 눌러 수정사항을 입력해주세요");
	    } else {
	        if (modify_date == "" || modify_time == "") {
	            alert("수정할 날짜, 시간을 입력해주세요");
	        } else {
	            // 날짜와 시간을 결합
	            const fullDateString = `${modify_date}T${modify_time}`; // 예: "2024-09-20T09:00:00"
	            const selectedDate = new Date(fullDateString);

	            const day = selectedDate.getUTCDay(); // 0: Sunday, 6: Saturday
	            const now = new Date(); // 현재 시간

	            console.log(now);
	            console.log(selectedDate);

	            if (day === 0 || day === 6) {
	                alert("토요일과 일요일은 선택할 수 없습니다.");
	                modify_date = ''; // 선택 초기화
	            } else if (selectedDate < now) {
	                alert("현재 시간보다 앞선 날짜와 시간은 선택할 수 없습니다.");
	                modify_date = '';
	            } else {
	                fetch("/counselor/monthly-modify", {
	                    method: "POST",
	                    headers: {
	                        "Content-Type": "application/json"
	                    },
	                    body: JSON.stringify({ modify_date: modify_date, modify_time: modify_time, applyNo: event.applyNo })
	                })
	                .then(response => {
	                    if (response.ok) {
	                        alert("수정 완료되었습니다.");
	                        location.href = "/counselor/weekly-calendar"; // 필요 시 주석 해제
	                    } else if (response.status == 608) {
	                        alert("서버 오류로 인해 일정 수정에 실패하였습니다.");
	                        return false;
	                    }
	                })
	                .catch(error => {
	                    console.error('Error:', error);
	                });
	            }
	        }
	    }
	};
}

function formatDate(dateString) {
    const [year, month, day] = dateString.split('-');
    return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
}

function getFirstDayOfWeek(date, weekOffset) {
	const firstDayOfMonth = new Date(date.getFullYear(), date.getMonth(), 1);
	const dayOfWeek = firstDayOfMonth.getDay();
	const diff = dayOfWeek === 0 ? -6 : 1 - dayOfWeek;
	const firstMonday = new Date(firstDayOfMonth);
	firstMonday.setDate(firstDayOfMonth.getDate() + diff);

	// 주 오프셋을 적용하여 첫 번째 월요일을 계산
	const firstDayOfWeek = new Date(firstMonday.setDate(firstMonday.getDate() + (weekOffset * 7)));

	// UTC+9로 변환 (한국 표준시 예시)
	const utcOffset = firstDayOfWeek.getTimezoneOffset() * 60000; // 분 단위를 밀리초로 변환
	return new Date(firstDayOfWeek.getTime() + utcOffset); // 로컬 시간대에 맞춰 조정
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
	location.href = "/counselor/weekly-calendar";
}

function viewMonthly() {
	location.href = "/counselor/monthly-calendar";
}

function getAppointmentsForWeek(startDate,data) {
	const appointments = {};

	// allList 배열을 반복
	data.allList.forEach(item => {
	    // 상담 날짜와 시간을 분리
	    const [date, time] = item.consultationDate.split(' ');

	    // appointments에 해당 날짜가 없으면 빈 배열로 초기화
	    if (!appointments[date]) {
	        appointments[date] = [];
	    }

	    // 상담 정보 추가
	    appointments[date].push({ 
			time: time.substring(0, 5), 
			type: item.consultationCategory, 
			name: item.studentName,
			applyNo : item.applyNo,
			consultationDate : item.consultationDate,
			consultationWay : item.consultationWay,
			studentMajor : item.studentMajor,
			studentNo : item.studentNo,
			studentTelNo : item.studentTelNo
		});
	});

	return appointments;
}

function getStudentColor(studentName) {
	if (!studentColors[studentName]) {
		studentColors[studentName] = getRandomColor();
	}
	return studentColors[studentName];
}

function getRandomColor() {
	let r, g, b;

	do {
		r = Math.floor(Math.random() * 256);
		g = Math.floor(Math.random() * 256);
		b = Math.floor(Math.random() * 256);

		// Calculate brightness
	} while (r + g + b > 512); // Adjust this value to control brightness

	// Mix the color with white to reduce saturation
	const mixWithWhite = 0.5; // Change this value to adjust how muted the color is

	r = Math.floor(r * mixWithWhite + 255 * (1 - mixWithWhite));
	g = Math.floor(g * mixWithWhite + 255 * (1 - mixWithWhite));
	b = Math.floor(b * mixWithWhite + 255 * (1 - mixWithWhite));

	return `rgb(${r}, ${g}, ${b})`;
}

getInitialWeek();
updateCalendar();

newDate.addEventListener("change", function(){
	for(var f=1; f<newTime.children.length; f++){
		if(f==5){
			newTime.children[f].disabled="disabled";
		} else{		
			newTime.children[f].disabled=false;
		}
	}
	getTodaySchedule();
});

newTime.addEventListener("change", function(){
	if(newDate.value==""){
		alert("날짜를 먼저 선택해주셔야 합니다.");
		newTime.value="";
	}
});

function getTodaySchedule(){
	fetch("/counselor/todaySchedule",{
		method : "GET",
		headers : {
			"Content-Type" : "application/json"
		}
	}).then(response => {
		 if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
	}).then(data => {
		data.forEach((element, index) => {
			if(element.split(" ")[0]==newDate.value){
				for(var f=1; f<newTime.children.length; f++){
					const timeData=newDate.value+" "+newTime.children[f].value;
					if(element==timeData){
						newTime.children[f].disabled="disabled";
					} 
				}
			}
		});
	}).catch(error => {
		console.error('Fetch error:', error);
	});
}
