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

            
            // 상담사 이름 가져오기
            const counselorName = cells[1].textContent;

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
				<button id="chatBtn" class="chat" onclick="chatStart('${cells[2].textContent}')">채팅하기</button>

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
                <input type="text" placeholder="텍스트를 입력해주세요" id="message" name="txtmessage" onkeydown="checkEnter(event)">
                <input type="button" class="btn btn-success" id="startBtn" value="수락" onclick="sendAcceptRequest(${userNo})">
				<input type="button" class="btn btn-success" id="sendBtn" value="전송" onclick="sendMessage(${userNo})">
            </div>
        </div>
        <div class="modal-footer" id="modal-event-footers">
            <input type="button" class="btn btn-lg btn-success show" id="start_chat" onclick="startChat(${userNo})" value="채팅 시작하기">
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


var uri = "";
var socket = "";

function connect(userNo) {
    console.log(userNo)
	
	uri = "ws://localhost:7777/chat/rooms?id="+userNo;
	socket = new WebSocket(uri);
	
	socket.onopen = function(e){
		console.log("서버오픈 성공 ㅠ")
	}
	
	socket.onmessage = function(event) {
        const message = event.data;
        showMessage(message, "counselor"); // 받은 메시지를 화면에 표시
    };
}

function sendAcceptRequest(targetUserId) {
    const requesterId = "seeun"; // 요청자 ID를 정의
    const message = `수락 요청 ${targetUserId}`; // 메시지 형식 정의
    socket.send(message); // WebSocket을 통해 메시지 전송
	
	document.getElementById("startBtn").style.display = "none";
	document.getElementById("sendBtn").style.display = "block"; 
	
}

function sendMessage() {
    const messageInput = document.getElementById("message");
    const message = messageInput.value;

    if (message.trim() !== "") {
        socket.send(message);
        console.log("보낸 메시지: ", message);
		
		showMessage(message,"admin");
        messageInput.value = ""; // 입력 필드 초기화
    } else {
        console.log("빈 메시지는 전송할 수 없습니다.");
    }
}

function showMessage(message , who) {
    var chat = document.getElementById("chat-content");
	
	if(who == "admin"){
	    chat.innerHTML += "<div class='admin'><span>" + message + "</span></div>";
	}else{
		chat.innerHTML += "<div class='counselor'><span>" + message + "</span></div>";
	}
	
}

function startChat(userNo){
	var chatinput = document.querySelector(".chat-text");
	var startbutton = document.getElementById("start_chat");
	
	chatinput.classList.add("show");
	startbutton.classList.remove("show");
	
	connect(userNo);
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


