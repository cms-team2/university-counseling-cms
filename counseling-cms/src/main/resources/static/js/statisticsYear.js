// 년도별 상담 통계 API로부터 데이터 가져오기
fetch('/api/statistics/counseling-by-year')
    .then(response => response.json())
    .then(data => {
        const categories = [...new Set(data.map(item => item.year))];  // 년도 목록
        const counselTypes = [...new Set(data.map(item => item.counselCategory))];  // 상담 유형 목록

        const seriesData = counselTypes.map(type => {
            return {
                name: type,
                data: data.filter(item => item.counselCategory === type).map(item => item.counselCount)
            };
        });

        const chartData = {
            categories: categories,
            series: seriesData
        };

        const options = {
            chart: { width: 700, height: 400 },
            xAxis: { title: '년도' },
            yAxis: { title: '상담 횟수' },
            series: { showDot: true }  // 선형 그래프에 포인트 표시
        };

        toastui.Chart.lineChart({ el: document.getElementById('yearlyChart'), data: chartData, options });
    });