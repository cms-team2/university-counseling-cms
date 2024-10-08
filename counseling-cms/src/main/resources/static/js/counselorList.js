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
            
    
            //확장자추출
            const extension = fileName ? fileName.substring(fileName.lastIndexOf('.')) : '';
    		
    		//기본이미지
    		let imagePath = "http://172.30.1.16:20080/images/d67e894a-f5ab-4b4d-9436-1ac3a9ba423e.jpeg";
    		
    		//첨부이미지
    		if (fileSequence && fileName) {
                const extension = fileName.substring(fileName.lastIndexOf('.'));
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
                onclick="location.href='/admin/counselor-schedule'">일정관리</button>
            `;
            closeSidebar.style.display="flex";
            modalContent.innerHTML = details;
            sidebarModal.classList.add("open");
        });      
    });
 
    // 닫기 버튼 클릭 시 모달 닫기
    closeSidebar.addEventListener("click", function () {
        sidebarModal.classList.remove("open");  // 모달 닫기
    });

    // 모달 외부 클릭 시 모달 닫기
    window.addEventListener("click", function (event) {
        if (!sidebarModal.contains(event.target) && !event.target.closest("#counselorTable tbody tr")) {
            sidebarModal.classList.remove("open"); // 모달 닫기
        }
    });
});