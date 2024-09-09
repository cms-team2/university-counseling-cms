const modifyBanner = function(idx){
	location.href = `/admin/banner-create?idx=${idx}`;
}

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
            	<h3>배너 내용</h3>
                <label>배너 번호</label>
                <input type="text" readonly value="${cells[0].textContent}" readonly>
                <label>제목</label>
                <input type="text" readonly value="${cells[1].textContent}" readonly>
                <label>게시순서</label>
                <input type="text" readonly value="${cells[2].textContent}">
                <label>게시 여부</label>
                <input type="text" readonly value="${cells[3].textContent}">
                <label>등록일</label>
				<input type="text" readonly value="${cells[4].textContent}">
				<label>내용</label>
                <textarea readonly>12312</textarea>
				
                <label>이미지</label>
				<div><img src="/images/face.jpg" style="max-width:100%;"></div>
                <button onclick="modifyBanner(1)">수정</button>
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