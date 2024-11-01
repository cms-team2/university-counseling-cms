document.addEventListener('DOMContentLoaded', () => {
    // '나의 검사기록' 버튼 클릭 시 이벤트 처리
    const diagnosisButton = document.querySelector('.open-modal[data-target="#settings-modal"]');
    
    if (diagnosisButton) {
        diagnosisButton.addEventListener('click', async (event) => {
            event.preventDefault();

            try {
                // 서버에서 자가진단 기록 가져오기
                const response = await fetch("/user/my-diagnosis-history", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                if (!response.ok) {
                    throw new Error('진단 기록을 불러오는데 실패했습니다.');
                }

                const data = await response.json();

                // 진단 기록 표시할 리스트 요소
                const diagnosisList = document.querySelector('#diagnosisList');
                diagnosisList.innerHTML = ''; // 기존 내용 초기화

                if (data.length > 0) {
                    data.forEach((history,index) => {
						
						const listItem = document.createElement('tr'); // li 대신 tr로 변경
						listItem.innerHTML = `
							<td>${data.length - index}</td>
    						<td>${history.diagDate}</td>
    						<td>${history.TOT_SCR}점</td>
    						<td><button class="btn btn-sm btn-secondary" onclick="viewDetail(${history.INSP_PRGRS_NO})">상세보기</button></td>
						`;
						diagnosisList.appendChild(listItem);
					
                    });
                } else {
                    const noDataItem = document.createElement('li');
                    noDataItem.textContent = '자가진단 기록이 없습니다.';
                    diagnosisList.appendChild(noDataItem);
                }
            } catch (error) {
                console.error('Error fetching diagnosis history:', error);
            }
        });
    }
});

function viewDetail(prgrsNo){
	 const url = `/user/self-diagnosis-result/`+prgrsNo;
	 const width = 800;
     const height = 400;
	 const left = (window.innerWidth / 2) - (width / 2);
     const top = (window.innerHeight / 2) - (height / 2);
     window.open(url, '_blank', `width=${width},height=${height},top=${top},left=${left}`);
}
