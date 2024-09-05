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
            	<img src="/images/face.jpg" style="width:200px; height:200px"><br><br>
                <p><strong>이름:</strong> ${cells[1].innerText}</p>
                <p><strong>사번:</strong> ${cells[2].innerText}</p>
                <p><strong>소속:</strong> ${cells[3].innerText}</p>
                <p><strong>상담분류:</strong> ${cells[4].innerText}</p>
                <p><strong>이메일:</strong> ${cells[5].innerText}</p>
                <p><strong>전화번호:</strong> ${cells[6].innerText}</p>
                <p><strong>임용일자:</strong> ${cells[7].innerText}</p>
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
