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

            // 행의 셀을 가져옴
            const cells = row.querySelectorAll("td");
            const studentId = cells[3].textContent;

            try {
                let response = await fetch('/admin/apply-list/api_data', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    body: new URLSearchParams({ data: studentId }).toString()
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }

                // 응답을 텍스트로 변환
                const responseText = await response.json();
                
                // 응답 텍스트를 필요한 형식으로 변환 (예: JSON)
                const url = responseText;  // 텍스트 응답을 필요한 데이터로 사용

                // 모달 내용 구성
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
                    <h4>CND 주소</h4>
                    <p>${url}</p>
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
