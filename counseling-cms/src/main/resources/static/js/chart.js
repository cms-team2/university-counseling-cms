const Chart = toastui.Chart;
const el = document.querySelector('#chart1');
const data = {
  categories: ['심리상담', '익명상담', '위기상담', '진로상담', '취업상담', '교수상담', '학습컨설팅'],  //fetch로 소메뉴 get
  series: [
    {
      name: '여성',
      data: [50, 31, 4, 42, 62, 23, 12], //fetch로 상담 통계 get
    },
    
    {
      name: '남성',
      data: [21, 42, 2, 23, 51, 32, 15],
    },
  ],
};
const options = {
  chart: { width: 700, height: 400 },
};

const chart = Chart.areaChart({ el, data, options });   // * Chart.barChart로 바꾸면 막대그래프 ,Chart.lineChart는 선 그래프
