document.addEventListener('DOMContentLoaded', () => {
    const modals = document.querySelectorAll('.modal');
    const openModalButtons = document.querySelectorAll('.open-modal');
    const closeButtons = document.querySelectorAll('.close');
    const activityList = document.getElementById("activity-list");
    const detailModal = document.getElementById("detail-modal");
    const detailContent = document.getElementById("detail-content");
    const myActivitiesModal = document.getElementById("my-activities-modal");

    // Open modal
    openModalButtons.forEach(button => {
        button.addEventListener('click', async (event) => {
            event.preventDefault();
            const target = document.querySelector(button.getAttribute('data-target'));
            if (target.id == "my-activities-modal") {
                try {
                    const response = await fetch("/user/getMyActivity", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        }
                    });

                    if (!response.ok) {
                        throw new Error('네트워크 응답이 좋지 않습니다.');
                    }

                    // CSS 스타일 추가
                    const style = document.createElement('style');
                    style.textContent = `
                        table {
                            border-collapse: collapse;
                            width: 100%;
                            margin-top: 10px;
                        }
                        th, td {
                            border: 1px solid #ccc;
                            padding: 10px;
                            text-align: left;
                        }
                        th {
                            background-color: #f2f2f2;
                        }
                    `;
                    document.head.appendChild(style);
                    const data = await response.json();
                    activityList.innerHTML = ""; // 기존 목록 초기화

                    // 테이블 생성
                    const table = document.createElement('table');
                    const headerRow = document.createElement('tr');

                    // 테이블 헤더 생성
                    const headers = ['상담자', '상담사', '상담내용', '상담일시'];
                    headers.forEach(headerText => {
                        const th = document.createElement('th');
                        th.textContent = headerText;
                        headerRow.appendChild(th);
                    });
                    table.appendChild(headerRow);

                    if (data.length > 0) {
                        data.forEach(activity => {
                            const row = document.createElement('tr');

                            const studentCell = document.createElement('td');
                            studentCell.textContent = activity.studentName;
                            row.appendChild(studentCell);

                            const employeeCell = document.createElement('td');
                            employeeCell.textContent = activity.employeeName;
                            row.appendChild(employeeCell);

                            const contentCell = document.createElement('td');
                            contentCell.textContent = activity.dscsnCn.length > 10 ? activity.dscsnCn.substring(0, 10) + '..' : activity.dscsnCn;
                            contentCell.style.cursor = 'pointer'; // 커서 변경
                            contentCell.addEventListener('click', () => {
                                myActivitiesModal.style.display = 'none'; // 원래 모달 닫기
                                detailContent.textContent = activity.dscsnCn; // 상세 내용 설정
                                detailModal.style.display = 'block'; // 상세 모달 표시
                            });
                            row.appendChild(contentCell);

                            const dateCell = document.createElement('td');
                            dateCell.textContent = activity.dscsnDt;
                            row.appendChild(dateCell);

                            table.appendChild(row);
                        });
                    } else {
                        const row = document.createElement('tr');
                        const cell = document.createElement('td');
                        cell.colSpan = headers.length; // 모든 열을 차지하도록 설정
                        cell.textContent = "현재 활동내역이 없습니다.";
                        row.appendChild(cell);
                        table.appendChild(row);
                    }

                    // 테이블을 activityList에 추가
                    activityList.appendChild(table);

                } catch (error) {
                    console.error('Fetch 에러:', error);
                }
            }
            target.style.display = 'block';
        });
    });

    // Close modal
    closeButtons.forEach(button => {
        button.addEventListener('click', () => {
            button.closest('.modal').style.display = 'none';
        });
    });

    // Close modal when clicking outside of modal
    window.addEventListener('click', (event) => {
        if (event.target.classList.contains('modal')) {
            event.target.style.display = 'none';
        }
    });

    // Close detail modal when clicking the close button
    const detailCloseButton = detailModal.querySelector('.close');
    detailCloseButton.addEventListener('click', () => {
        detailModal.style.display = 'none'; // 상세 모달 닫기
        myActivitiesModal.style.display = 'block'; // 원래 상담활동 모달 열기
    });
});
