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
            const details = `
			    <h3>상담 배정 내역</h3>
			   	<label>상담 신청번호</label>
			    <input type="text" value="${cells[0].textContent}" readonly>
			    
			    <label>최초 상담일</label>
			    <input type="text" readonly>
			    
			    <label>최근 상담일</label>
			    <input type="text" readonly>
			    
			    <label>상담 예정일</label>
			    <input type="text" value="${cells[7].textContent}" readonly>
			    
			    <label>상담방식</label>
			    <input type="text" placeholder="방식 입력">
			    
			    <label>담당 상담사</label>
			    <input type="text" readonly>
			    
			    <label>담당 부서</label>
			    <input type="text" readonly>
			    
			    <label>배정 가능 상담사</label>
			    <select>
			    <option value="하현수">하현수</option>
			        <!-- 다른 상담사들 추가 -->
			    </select>
			    
			    <button>상담사 변경</button>
            `;
			closeSidebar.style.display="flex";
            modalContent.innerHTML = details;
            sidebarModal.classList.add("open");
        });
    });
    closeSidebar.addEventListener("click", function () {
		closeSidebar.style.display="none";
        sidebarModal.classList.remove("open");
    });
});


const scheduleButton = document.querySelector("#scheduleCalender");

scheduleButton.addEventListener("click", function() {
     window.location.href = "/counselor-schedule";
});
