let today;
let tomorrow;
let currentYear = new Date().getFullYear();
let currentMonth = new Date().getMonth();
const modalBody = document.getElementById('modal-event-details');
const newDate = document.querySelector("#new_date");
const newTime=document.querySelector("#new_time");

function generateCalendar(year, month) {
    const lastDate = new Date(year, month + 1, 0).getDate();
    const title = document.getElementById('calendar-title');

    title.textContent = `${year}년 ${month + 1}월`;

	const date = year + "-" + (month + 1).toString().padStart(2, '0') + "-" + lastDate;
	
	fetch("/counselor/monthly-data", {
	    method: "POST",
	    headers: {
	        "Content-Type": "application/json"
	    },
		body: JSON.stringify({ date : date })
	})
	.then(response => response.json())
	.then(data => {
		today=data.today;
		tomorrow=today.split("-")[0]+"-"+today.split("-")[1]+"-"+(parseInt(today.split("-")[2], 10)+1);
		
		// input의 min 속성에 오늘 날짜를 설정합니다.
		newDate.setAttribute('min', tomorrow);
		
		const evt2 = {};
		data.allList.forEach(item => {
			
		    const date = item.consultationDate.split(' ')[0]; // '2024-09-10'
		    const time = item.consultationDate.split(' ')[1].substring(0, 5); // '10:00'
			const applyNo = item.applyNo;
		    const studentName = item.studentName;
			const studentNo = item.studentNo;
			const studentMajor = item.studentMajor;
			const studentTelNo = item.studentTelNo;
			const consultationDate = item.consultationDate;
			const consultationCategory = item.consultationCategory;
			const consultationWay = item.consultationWay;

		    // 앞자리 0 제거
		    const formattedDate = date.replace(/-0/g, '-'); // '2024-9-10'

		    if (!evt2[formattedDate]) {
		        evt2[formattedDate] = [];
		    }
		    
		    evt2[formattedDate].push({ 
				applyNo,time, studentName ,studentNo,studentMajor,studentTelNo,
				consultationDate,consultationCategory,consultationWay
			});
		});
		
		calendarView(year,month,lastDate,evt2)
	})
	.catch(error => {
	    console.error('Error:', error);
	});	
	
}

function calendarView(year,month,lastDate,evt){
	const calendarBody = document.getElementById('calendar-body');
	const firstDay = new Date(year, month, 1).getDay();
	const currentDate = new Date();
	let html = '<tr>';
   for (let i = 0; i < firstDay; i++) {
       html += '<td></td>';
   }

   // Dates
   for (let date = 1; date <= lastDate; date++) {
       if ((firstDay + date - 1) % 7 === 0 && date !== 1) {
           html += '</tr><tr>';
       }
       
       const isToday = (year === currentDate.getFullYear() && month === currentDate.getMonth() && date === currentDate.getDate());
       const events = getEventsForDate(year, month, date,evt);

		let eventHtml = '';
		events.forEach(event => {
			const color = getEventColor(event.studentName);
			eventHtml += `
	               <div class="event-box" style="background-color: ${color};" data-bs-toggle="modal" data-bs-target="#eventModal" data-event='${JSON.stringify(event)}'>
	                   <span>${event.time}</span><span> ${event.studentName}</span>
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

function getEventsForDate(year, month, date ,evt) {
    // Example event data (date, time, event studentName)
	
    const formattedDate = `${year}-${month + 1}-${date}`;
    const dateEvents = evt[formattedDate] || [];

    // Sort events by time in ascending order
    return dateEvents.sort((a, b) => new Date(`1970-01-01T${a.time}:00`) - new Date(`1970-01-01T${b.time}:00`));
}

const colorCache = {};

function getEventColor(studentName) {
    // Use cached color if available
    if (colorCache[studentName]) {
        return colorCache[studentName];
    }

    // Generate a new color and cache it
    const color = getRandomColor();
    colorCache[studentName] = color;
    return color;
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

function showEventDetails(event) {
    modalBody.querySelector('#studentName').value = event.studentName;
	modalBody.querySelector('#studentNo').value = event.studentNo;
	modalBody.querySelector('#studentMajor').value = event.studentMajor;
	modalBody.querySelector('#studentTelNo').value = event.studentTelNo;
	modalBody.querySelector('#consultationDate').value = event.consultationDate.slice(0, 16);
	modalBody.querySelector('#consultationCategory').value = event.consultationCategory;
	modalBody.querySelector('#consultationWay').value = event.consultationWay;
	
	const modalfooter = document.getElementById('modal-event-footers');
	const recordLink = modalfooter.querySelector("#record");
	const modifyLink = modalfooter.querySelector("#monthlyModify");
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
	            const now = today; // 현재 시간

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
	                        location.href = "/counselor/monthly-calendar"; // 필요 시 주석 해제
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

function viewWeekly() {
    location.href="/counselor/weekly-calendar";
}

function viewMonthly() {
	 location.href="/counselor/monthly-calendar";
}

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
		console.log(data);
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