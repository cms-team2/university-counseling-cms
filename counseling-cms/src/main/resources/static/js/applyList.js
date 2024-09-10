document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll("#counselorTable tbody tr");
    const sidebarModal = document.getElementById("sidebarModal");
    const modalContent = document.getElementById("modalContent");
    const closeSidebar = document.getElementById("closeSidebar");

    rows.forEach(row => {
        row.addEventListener("click", async function () {
            // 모든 행에서 선택 상태 제거
            rows.forEach(r => r.classList.remove("selected"));
            row.classList.add("selected");

            const cells = row.querySelectorAll("td");
            const studentId = cells[3].textContent;

            try {
                let response = await fetch('/admin/apply_list_api_data/'+studentId,{
					credentials: 'include'
				})
				
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
				//var responseText = response;
                 const responseData = await response.json();
                const details = `
                    <h3>상담 신청 세부내역</h3>
                    <label>상담 신청번호</label>
                    <input type="text" value="${cells[0].textContent}" readonly>
                    <label>상담 신청일</label>
                    <input type="text" value="${cells[8].textContent}" readonly>
                    <label>상담 희망일</label>
                    <input type="text"  value="${responseData.dscsnRsvtYmd}" readonly>
                    <label>상담 방식</label>
                    <input type="text"  value=${responseData.csclsfNm} readonly>
                    <label>상담 신청 내용</label>
                    <textarea placeholder="상담 내용을 입력하세요" readonly>${responseData.dscsnAplyCn} </textarea>
                    <label>배정 가능 상담사</label>
                    <select>
                    	<option value="">상담사를 선택해주세요.</option>
                        <option value="하현수">하현수</option>
                        <!-- 다른 상담사들 추가 -->
                    </select>
                    <button>상담사 배정</button>
                `;

                // 모달에 내용 설정
                modalContent.innerHTML = details;
                sidebarModal.classList.add("open");
                closeSidebar.style.display = "flex";

            } catch (error) {
                console.error('There has been a problem with your fetch operation:', error);
            }
        });
    });

    closeSidebar.addEventListener("click", function () {
        closeSidebar.style.display = "none";
        sidebarModal.classList.remove("open");
    });
});

function search_stdnt() {
    // admin_apply_frm.action="./admin/apply-list";
    admin_apply_frm.submit();
}
