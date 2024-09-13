// 성별 상담 통계 API로부터 데이터 가져오기
fetch('/api/statistics/counseling-by-gender')
    .then(response => response.json())
    .then(data => {
        const categories = [...new Set(data.map(item => item.counselCategory))];  // 중복 제거 후 상담 유형 목록
        const maleData = data.filter(item => item.gender === 'M').map(item => item.counselCount);  // 남성 상담 횟수
        const femaleData = data.filter(item => item.gender === 'F').map(item => item.counselCount);  // 여성 상담 횟수

        const chartData = {
            categories: categories,
            series: [
                {
                    name: '남성',
                    data: maleData
                },
                {
                    name: '여성',
                    data: femaleData
                }
            ]
        };
        
        const options = {
            chart: { width: 700, height: 400 },
            xAxis: { title: '상담 유형' },
            yAxis: { title: '상담 횟수' },
            series: { stack: true }  // 스택형 막대 그래프
        };
        
        toastui.Chart.columnChart({ el: document.getElementById('chart'), data: chartData, options });
    });