// API로부터 데이터 가져오기
fetch('/api/statistics/counseling')
    .then(response => response.json())
    .then(data => {
        const categories = data.map(item => item.counselCategory);  // 상담 유형 목록
        const seriesData = data.map(item => item.counselCount);     // 상담 횟수 목록

        const chartData = {
            categories: categories,
            series: [
                {
                    name: '상담 횟수',
                    data: seriesData
                }
            ]
        };

        const options = {
            chart: { width: 700, height: 400 },
            xAxis: { title: '상담 유형' },
            yAxis: { title: '상담 횟수' }
        };

       //toastui.Chart.barChart({ el: document.getElementById('chart'), data: chartData, options }); //막대그래프 가로
       toastui.Chart.columnChart({ el: document.getElementById('chart'), data: chartData, options });	//막대그래프 세로  
       //toastui.Chart.lineChart({ el: document.getElementById('chart'), data: chartData, options });	//증감추세 그래프
       //toastui.Chart.areaChart({ el: document.getElementById('chart'), data: chartData, options: {} }); //증감추세 + 영역색칠 그래프
    });