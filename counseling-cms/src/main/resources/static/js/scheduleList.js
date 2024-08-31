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
 				<!-- 디테일 입력란 -->
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