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
            	<h3>상담 신청 세부내역</h3>
                <label>상담 신청번호</label>
                <input type="text" value="${cells[0].textContent}" readonly>
                <label>상담 신청일</label>
                <input type="text" value="${cells[8].textContent}" readonly>
                <label>상담 희망일</label>
                <input type="text" placeholder="희망일 입력">
                <label>상담 방식</label>
                <input type="text" id="kkk" placeholder="방식 입력">
                <label>상담 신청 내용</label>
                <textarea placeholder="상담 내용을 입력하세요"></textarea>
                <label>배정 가능 상담사</label>
                <select>
                    <option value="하현수">하현수</option>
                    <!-- 다른 상담사들 추가 -->
                </select>
                <button>상담사 배정</button>
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