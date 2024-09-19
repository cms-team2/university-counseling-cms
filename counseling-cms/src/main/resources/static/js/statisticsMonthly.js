// 월별 상담 통계 데이터 로드 및 드롭다운 메뉴 생성
fetch('/api/statistics/counseling-by-month')
    .then(response => response.json())
    .then(data => {
        // 드롭다운 카테고리를 최신순으로 정렬
        let dropdownCategories = [...new Set(data.map(item => item.applyMonth))];
        dropdownCategories.sort((a, b) => new Date(b) - new Date(a));

        // 차트 카테고리를 오래된 순으로 정렬
        let chartCategories = [...new Set(data.map(item => item.applyMonth))];
        chartCategories.sort((a, b) => new Date(a) - new Date(b));

        // 드롭다운 메뉴에 년도-월 동적으로 추가
        const monthSelect = document.getElementById('monthSelect');
        monthSelect.innerHTML = '';  // 기존 옵션 삭제
        dropdownCategories.forEach(month => {
            const option = document.createElement('option');
            option.value = month;
            option.textContent = month;
            monthSelect.appendChild(option);
        });

        // 가장 최신 월로 기본 선택 설정
        const latestMonth = dropdownCategories[0];
        monthSelect.value = latestMonth;

        // 월별 통계 차트 렌더링
        const counselTypes = [...new Set(data.map(item => item.counselName))];
        const seriesData = counselTypes.map(type => ({
            name: type,
            data: data.filter(item => item.counselName === type).map(item => item.counselCount)
        }));

        const chartData = {
            categories: chartCategories,  // 차트는 오래된 순으로 정렬된 카테고리
            series: seriesData
        };

        const options = {
            chart: { width: 700, height: 400 },
            xAxis: { title: '월' },
            yAxis: { title: '상담 횟수' },
            series: { stack: true }
        };

        // 차트 렌더링
        const chart = toastui.Chart.columnChart({
            el: document.getElementById('monthlyChart'),
            data: chartData,
            options: options
        });

        // 페이지 로드 시 최신 월의 일자별 통계 로드
        loadDailyData(latestMonth);  // 최신 월을 인자로 전달하여 일자별 통계를 로드
    });
