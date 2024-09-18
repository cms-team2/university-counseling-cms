// 선택된 월에 따른 일별 데이터 로드
function loadDailyData(selectedMonth) {
    // 만약 selectedMonth가 전달되지 않았을 경우, 드롭다운에서 선택된 값을 사용
    const month = selectedMonth || document.getElementById('monthSelect').value;

    fetch(`/api/statistics/counseling-by-day?month=${month}`)
        .then(response => response.json())
        .then(data => {
            console.log("일별 데이터:", data);
            displayDailyData(data);
        })
        .catch(error => {
            console.error("일별 데이터를 불러오는 중 오류 발생:", error);
        });
}

// 일별 데이터를 테이블로 표시
function displayDailyData(data) {
    const table = document.getElementById('dailyStatsTable');
    table.innerHTML = '';  // 기존 데이터를 삭제하고 새로운 데이터를 추가

    // 테이블 헤더 생성
    const headerRow = document.createElement('tr');
    headerRow.innerHTML = `
        <th>날짜</th>
        <th>심리상담</th>
        <th>학업상담</th>
        <th>기타상담</th>
    `;
    table.appendChild(headerRow);

    // 총합을 계산하기 위한 변수 초기화
    let totalPsychology = 0;
    let totalAcademic = 0;
    let totalOther = 0;

    // 데이터 행 추가
    data.forEach(day => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${day.applyDate}</td>
            <td>${day.psychologyCount}</td>
            <td>${day.academicCount}</td>
            <td>${day.otherCount}</td>
        `;
        table.appendChild(row);

        // 각 상담 유형별 총합을 계산
        totalPsychology += day.psychologyCount;
        totalAcademic += day.academicCount;
        totalOther += day.otherCount;
    });

    // 총합을 마지막 행에 추가
    const totalRow = document.createElement('tr');
    totalRow.innerHTML = `
        <td><strong>총합</strong></td>
        <td><strong>${totalPsychology}</strong></td>
        <td><strong>${totalAcademic}</strong></td>
        <td><strong>${totalOther}</strong></td>
    `;
    table.appendChild(totalRow);
}
