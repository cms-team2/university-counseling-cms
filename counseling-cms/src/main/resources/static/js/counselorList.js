document.addEventListener("DOMContentLoaded", function() {
	const rows = document.querySelectorAll("#counselorTable tbody tr");
	const sidebarModal = document.getElementById("sidebarModal");
	const modalContent = document.getElementById("modalContent");
	const closeSidebar = document.getElementById("closeSidebar");

	rows.forEach(row => {
		row.addEventListener("click", function() {
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
                <button id="scheduleBtn"
                onclick="location.href='/admin/counselor-schedule?name=' + encodeURIComponent('${counselorName}')">일정 관리</button>

				<button id="scheduleBtn" class="chat" onclick="chatStart('${cells[2].textContent}')">채팅하기</button>

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

let stompClient = null;
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
 <input type="text" placeholder="텍스트를 입력해주세요" id="message" name="txtmessage" onkeydown="checkEnter(event)" style="display : none;">
 <input type="button" class="btn btn-success" id="sendBtn" value="전송">
 </div>
 </div>
 <div class="modal-footer" id="modal-event-footers">
 <input type="button" class="btn btn-lg btn-success show" id="start_chat" value="채팅 시작하기">
 <input type="button" class="btn btn-lg btn-secondary" id="close_chat" value="채팅 종료하기"> 
 </div>

 `;

	// 모달 표시 및 배경 스크롤 비활성화
	modal.style.display = "block";
	document.querySelector("body").style.overflow = "hidden";

	// userNo를 콘솔에 출력
	console.log(userNo);
	
	

	document.querySelector("#start_chat").addEventListener('click', function() {
		
		
		
	    const socket = new SockJS('/ws'); // WebSocket 연결
	    stompClient = Stomp.over(socket);
	
	    stompClient.connect({}, function (frame) {
	        console.log('Connected: ' + frame);
	        var chatinput = document.querySelector(".chat-text");
			var startbutton = document.getElementById("start_chat");
			chatinput.classList.add("show");
			startbutton.classList.remove("show"); 
			
			document.querySelector("#sendBtn").style.display = "block";    
			document.querySelector("#message").style.display = "block"
			document.querySelector("#startBtn").style.display = "none";
			
	        stompClient.subscribe('/topic/messages', function (message) {
	            showMessage(message.body); // 메시지 수신
	        });
	    });
	});
	
	document.getElementById('sendButton').addEventListener('click', sendMessage);
	
	function sendMessage() {
	    const message = document.getElementById('message').value;
	    stompClient.send("/app/sendMessage", {}, message); // 메시지 전송
	    document.getElementById('message').value = ''; // 입력 필드 초기화
	}
	
	function showMessage(message) {
	    var chat = document.getElementById("chat-content");
	    chat.innerHTML += "<div class='admin'><span>" + message + "</span></div>";
	}
	
	
}



function closeChat() {
		let modal = document.getElementById("eventModal");
	    if (stompClient) {
	        stompClient.disconnect(); // 소켓 연결 종료
	    }
	    modal.style.display = "none";
	}