document.addEventListener('DOMContentLoaded', () => {
    const modals = document.querySelectorAll('.modal');
    const openModalButtons = document.querySelectorAll('.open-modal');
    const closeButtons = document.querySelectorAll('.close');
    const activityList = document.getElementById("activity-list");
    const detailModal = document.getElementById("detail-modal");
    const detailContent = document.getElementById("detail-content");
    const myActivitiesModal = document.getElementById("my-activities-modal");

    openModalButtons.forEach(button => {
        button.addEventListener('click', async (event) => {
            event.preventDefault();
            const target = document.querySelector(button.getAttribute('data-target'));
            if (target.id === "my-activities-modal") {
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

                    const data = await response.json();
                    activityList.innerHTML = ""; 

                    const table = document.createElement('table');
                   table.className="table";
                    const headerRow = document.createElement('tr');

                    const headers = ['상담 분류', '학생 번호', '상담 날짜', '상담 내용', '학생 이름', '직원 이름', '진행 상태', '상담리뷰'];
                    headers.forEach(headerText => {
                        const th = document.createElement('th');
                        th.textContent = headerText;
                        th.style.width = '100px';
                        th.style.backgroundColor=' #343a40'; 
                        th.style.borderColor=' #454d55';
                        th.style.color='white';
                        headerRow.appendChild(th);
                    });
                    table.appendChild(headerRow);

                    if (data.length > 0) {
                        data.forEach(activity => {
                            const row = document.createElement('tr');

                            const categoryCell = document.createElement('td');
                            categoryCell.textContent = activity.ccdClsfNm || ''; // 공백 처리
                            row.appendChild(categoryCell);

                            const studentNoCell = document.createElement('td');
                            studentNoCell.textContent = activity.stdntNo || ''; // 공백 처리
                            row.appendChild(studentNoCell);

                            const dateCell = document.createElement('td');
                            dateCell.textContent = activity.dscsnDt || ''; // 공백 처리
                            row.appendChild(dateCell);

                            const contentCell = document.createElement('td');
                            contentCell.textContent = activity.dscsnCn ? (activity.dscsnCn.length > 10 ? activity.dscsnCn.substring(0, 10) + '..' : activity.dscsnCn) : ''; // 공백 처리
                            row.appendChild(contentCell);

                            const studentNameCell = document.createElement('td');
                            studentNameCell.textContent = activity.studentFlNm || '';
                            row.appendChild(studentNameCell);

                            const employeeNameCell = document.createElement('td');
                            employeeNameCell.textContent = activity.empFlNm || '';
                            row.appendChild(employeeNameCell);

                            const progressStatusCell = document.createElement('td');
                            if (activity.cprgrsYn === 'B') {
                                const cancelButton = document.createElement('button');
                                cancelButton.textContent = '취소하기';
                                cancelButton.className="btn btn-sm btn-danger";
                                cancelButton.addEventListener('click', async (e) => {
                                    e.stopPropagation(); 
                                    const confirmCancel = confirm('정말로 취소하시겠습니까?');
                                    if (confirmCancel) {
                                        try {
                                            const cancelResponse = await fetch(`/user/cancelActivity/${activity.aplyNo}`, {
                                                method: "POST",
                                                headers: {
                                                    "Content-Type": "application/json"
                                                }
                                            });
                                            if (cancelResponse.ok) {
                                                alert('상담이 취소되었습니다.');
                                                location.reload(); 
                                            } else {
                                                alert('취소에 실패했습니다. 다시 시도해주세요.');
                                            }
                                        } catch (error) {
                                            console.error('취소 요청 에러:', error);
                                        }
                                    }
                                });
                                progressStatusCell.appendChild(cancelButton);
                            } else {
                                progressStatusCell.textContent = '상담 종료'; 
                            }
                            row.appendChild(progressStatusCell);

                            // 상담 종료된 경우 상담 리뷰 버튼 추가
                            const reviewCell = document.createElement('td');
                            if (activity.cprgrsYn !== 'B') {  
                                const reviewButton = document.createElement('button');
                                reviewButton.textContent = '상담리뷰작성하기';
                                reviewButton.className="btn btn-sm btn-secondary";
                                reviewButton.addEventListener('click', () => {
                                    const boardName = "review"; 
                                    window.location.href = `/board/${boardName}/write?aplyNo=${activity.aplyNo}`;
                                });
                                reviewCell.appendChild(reviewButton);
                            } else {
                                reviewCell.textContent = '-';
                            }
                            row.appendChild(reviewCell);

                         
                            row.addEventListener('click', () => {
                                myActivitiesModal.style.display = 'none';
                                detailContent.innerHTML = `
                                    <table style="width: 100%; border-collapse: collapse;">
                                        <thead>
                                            <tr>
                                                <th style="text-align: left; padding: 10px; border: 1px solid #454d55; width: 150px; background-color:#343a40; color:white;">상담 분류</th>
                                                <td style="padding: 10px; border: 1px solid #ccc;">${activity.ccdClsfNm || ''}</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <th style="text-align: left; padding: 10px; border: 1px solid #454d55; width: 150px; background-color:#343a40; color:white;">학생 번호</th>
                                                <td style="padding: 10px; border: 1px solid #ccc;">${activity.stdntNo || ''}</td>
                                            </tr>
                                            <tr>
                                                <th style="text-align: left; padding: 10px; border: 1px solid #454d55; width: 150px; background-color:#343a40; color:white;">상담 날짜</th>
                                                <td style="padding: 10px; border: 1px solid #ccc;">${activity.dscsnDt || ''}</td>
                                            </tr>
                                            <tr>
                                                <th style="text-align: left; padding: 10px; border: 1px solid #454d55; width: 150px; background-color:#343a40; color:white;">상담 내용</th>
                                                <td style="padding: 10px; border: 1px solid #ccc;">${activity.dscsnCn || ''}</td>
                                            </tr>
                                            <tr>
                                                <th style="text-align: left; padding: 10px; border: 1px solid #454d55; width: 150px; background-color:#343a40; color:white;">학생 이름</th>
                                                <td style="padding: 10px; border: 1px solid #ccc;">${activity.studentFlNm || ''}</td>
                                            </tr>
                                            <tr>
                                                <th style="text-align: left; padding: 10px; border: 1px solid #454d55; width: 150px; background-color:#343a40; color:white;">직원 이름</th>
                                                <td style="padding: 10px; border: 1px solid #ccc;">${activity.empFlNm || ''}</td>
                                            </tr>
                                            <tr>
                                                <th style="text-align: left; padding: 10px; border: 1px solid #454d55; width: 150px; background-color:#343a40; color:white;">진행 상태</th>
                                                <td style="padding: 10px; border: 1px solid #ccc;">${activity.cprgrsYn === 'B' ? '상담 진행 중' : '상담 종료'}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                `;
                                detailModal.style.display = 'block'; // 상세 모달 표시
                            });

                            table.appendChild(row);
                        });
                    } else {
                        const row = document.createElement('tr');
                        const cell = document.createElement('td');
                        cell.colSpan = headers.length;
                        cell.textContent = "현재 활동내역이 없습니다.";
                        row.appendChild(cell);
                        table.appendChild(row);
                    }

                    activityList.appendChild(table);

                } catch (error) {
                    console.error('Fetch 에러:', error);
                }
            }
            target.style.display = 'block';
        });
    });

    closeButtons.forEach(button => {
        button.addEventListener('click', () => {
            button.closest('.modal').style.display = 'none';
        });
    });

    window.addEventListener('click', (event) => {
        if (event.target.classList.contains('modal')) {
            event.target.style.display = 'none';
        }
    });

    const detailCloseButton = detailModal.querySelector('.close');
    detailCloseButton.addEventListener('click', () => {
        detailModal.style.display = 'none';
        myActivitiesModal.style.display = 'block';
    });
});
