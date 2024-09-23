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
<<<<<<< HEAD
            
            // 상담사 이름 가져오기
            const counselorName = cells[1].textContent;
    
            //확장자추출
=======

            // Extract extension
>>>>>>> 411a1dd8370e403583ee90b18d1839573fc5669d
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
<<<<<<< HEAD
                <button id="scheduleBtn"
                onclick="location.href='/admin/counselor-schedule?name=' + encodeURIComponent('${counselorName}')">일정 관리</button>
=======
                <button id="scheduleBtn" onclick="location.href='/admin/counselor-schedule'">일정관리</button>
				<button id="scheduleBtn" class="chat" onclick="chatStart('${cells[2].textContent}')">채팅하기</button>
>>>>>>> 411a1dd8370e403583ee90b18d1839573fc5669d
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
	console.log("userNo:" + userNo)

	connect(userNo); // 연결
}
function checkEnter(event) {
    if (event.key === 'Enter') {
        sendMessage(); // 엔터 키가 눌리면 sendMessage 호출
    }
}

function chatStart(userNo) {
    // 모달 요소 가져오기
    let modal = document.getElementById("eventModal");
    let modalContent = modal.querySelector(".modal-content");

    // 모달 내용 초기화
    modalContent.innerHTML = `
        <div class="modal-header">
            <h5 class="modal-title" id="eventModalLabel">채팅</h5>
            <button type="button" class="btn-close" onclick="closeChat()"></button>
        </div>
        <div class="modal-body" id="modal-event-details">
            <div class="chat-content" id="chat-content">
                <!-- 여기에 채팅 내용이 추가됩니다 -->
            </div>
            <div class="chat-text">
                <input type="text" placeholder="텍스트를 입력해주세요" id="message" onkeydown="checkEnter(event)">
                <input type="button" class="btn btn-success" value="전송" onclick="sendMessage()">
            </div>
        </div>
        <div class="modal-footer" id="modal-event-footers">
            <input type="button" class="btn btn-lg btn-success show" id="start_chat" onclick="startChat()" value="채팅 시작하기">
            <input type="button" class="btn btn-lg btn-secondary" id="close_chat" onclick="leaveChat()" value="채팅 종료하기"> 
        </div>
    `;

    // 모달 표시 및 배경 스크롤 비활성화
    modal.style.display = "block";
    document.querySelector("body").style.overflow = "hidden";

    // userNo를 콘솔에 출력
    console.log(userNo);
}

var stompClient = null;

function connect(userNo) {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
	stompClient.connect({userId:userNo}, function (frame) {
	    console.log('Connected: ' + frame);
	    stompClient.subscribe('/counselor/messages', function (message) {
	        showMessage(message.body,"counselor");
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
	
	showMessage(message,"admin");
	document.getElementById("message").value = "";
}

function showMessage(message , who) {
    var chat = document.getElementById("chat-content");
	
	if(who == "admin"){
	    chat.innerHTML += "<div class='admin'><span>" + message + "</span></div>";
	}else{
		chat.innerHTML += "<div class='counselor'><span>" + message + "</span></div>";
	}
	
}

function startChat(){
	var chatinput = document.querySelector(".chat-text");
	var startbutton = document.getElementById("start_chat");
	
	chatinput.classList.add("show");
	startbutton.classList.remove("show");
	
	connect();
}

function closeChat() {
	var startbutton = document.getElementById("start_chat");
	let modal = document.getElementById("eventModal");
	let body = document.querySelector("body")
	if (startbutton.classList.contains("show")) {
	    console.log("start_chat 버튼은 show 클래스를 가지고 있습니다.");
		
		modal.style.display = "none";
		body.style.overflow = "auto"
		
	    return true; // 클래스가 있음
	} else {
	    alert("채팅을 먼저 종료해주셔야합니다.")
	    return false; // 클래스가 없음
	}
}

function leaveChat(){
	var startbutton = document.getElementById("start_chat");
	let modal = document.getElementById("eventModal");
	let body = document.querySelector("body")
	if(confirm("정말 종료하시겠습니까?")){
		modal.style.display = "none";
		body.style.overflow = "auto"
	}	
}


