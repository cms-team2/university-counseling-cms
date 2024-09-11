// API로부터 데이터 가져오기
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

       //toastui.Chart.barChart({ el: document.getElementById('chart'), data: chartData, options }); //막대그래프 가로
       //toastui.Chart.columnChart({ el: document.getElementById('chart'), data: chartData, options });	//막대그래프 세로  
       //toastui.Chart.lineChart({ el: document.getElementById('chart'), data: chartData, options });	//증감추세 그래프
       //toastui.Chart.areaChart({ el: document.getElementById('chart'), data: chartData, options: {} }); //증감추세 + 영역색칠 그래프

    });
    
  // API로부터 년도별 상담 데이터 가져오기
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
