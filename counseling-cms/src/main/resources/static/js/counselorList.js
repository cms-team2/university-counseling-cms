document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll("#counselorTable tbody tr");
    const sidebarModal = document.getElementById("sidebarModal");
    const modalContent = document.getElementById("modalContent");
    const closeSidebar = document.getElementById("closeSidebar");

    rows.forEach(row => {
        row.addEventListener("click", function () {
            rows.forEach(r => r.classList.remove("selected"));
            row.classList.add("selected");

            const cells = row.querySelectorAll("td");
            const fileSequence = row.getAttribute("data-filesequence");
            const fileName = row.getAttribute("data-filename");

            // Extract extension
            const extension = fileName ? fileName.substring(fileName.lastIndexOf('.')) : '';
            // Default image
            let imagePath = "http://172.30.1.16:20080/images/d67e894a-f5ab-4b4d-9436-1ac3a9ba423e.jpeg";

            // Attachment image
            if (fileSequence && fileName) {
                imagePath = `http://172.30.1.16:20080/images/${fileSequence}${extension}`;
            }

            const details = `
                <h3>상담사 세부 정보</h3>
                <img src="${imagePath}" alt="Counselor Image" width="200" height="200"><br><br>
                <label>이름</label>
                <input type="text" value="${cells[1].textContent}" readonly>
                <label>사번</label>
                <input type="text" value="${cells[2].textContent}" readonly>
                <label>소속</label>
                <input type="text" value="${cells[3].textContent}" readonly>
                <label>상담 분류</label>
                <input type="text" value="${cells[4].textContent}" readonly>
                <label>이메일</label>
                <input type="text" value="${cells[5].textContent}" readonly>
                <label>전화번호</label>
                <input type="text" value="${cells[6].textContent}" readonly>
                <label>임용일자</label>
                <input type="text" value="${cells[7].textContent}" readonly>
                <br>
                <button id="scheduleBtn" onclick="location.href='/admin/counselor-schedule'">일정관리</button>
				<button id="scheduleBtn" data-bs-toggle="modal" data-bs-target="#eventModal" class="chat" onclick="chatStart('${cells[2].textContent}')">채팅하기</button>
            `;
            closeSidebar.style.display = "flex";
            modalContent.innerHTML = details;
            sidebarModal.classList.add("open");
        });
    });

    // Close modal on close button click
    closeSidebar.addEventListener("click", function () {
        sidebarModal.classList.remove("open");
    });

    // Close modal on outside click
    window.addEventListener("click", function (event) {
        if (!sidebarModal.contains(event.target) && !event.target.closest("#counselorTable tbody tr")) {
            sidebarModal.classList.remove("open");
        }
    });
});

function chatStart(userNo){
	console.log(userNo)

	connect(); // 연결
}

var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
	    console.log('Connected: ' + frame);
	    stompClient.subscribe('/counselor/messages', function (message) {
	        showMessage(message.body);
	    });
	}, function (error) {
	    console.error('Error connecting to STOMP:', error);
	});
}

function sendMessage() {
    var message = document.getElementById("message").value;
    console.log("Sending message:", message); // 메시지 전송 로그 추가
    
    // 메시지를 JSON 형식으로 변환
    var messagePayload = JSON.stringify({ text: message });
    
    // STOMP 메시지 전송
    stompClient.send("/admin/sendMessage", {}, messagePayload);
}

function showMessage(message) {
    var chat = document.getElementById("chat-content");
    chat.innerHTML += "<div>" + message + "</div>";
}

