const writeRecord=document.querySelector("#write_record");
const progress=document.querySelector("#progress");
const fileDown=document.querySelector(".file_down");

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const applyNo=urlParams.get('applyNo');

writeRecord.addEventListener("click", function(){
	console.log(applyNo);
	if(applyNo != null){
		location.href="/counselor/writeCounselingRecord?applyNo="+applyNo;
	}
});

document.addEventListener("DOMContentLoaded", function () {
	if(progress.value=='상담 완료'){
		writeRecord.innerText="상담일지 보기";
	} else if(progress.value=='상담 중지'){
		writeRecord.style.display="none";
	}
	
});

  function dviewFile(element) {
        const filePath = element.getAttribute('data-file-path'); // 클릭한 span의 데이터 속성 가져오기

        // a 태그를 생성하여 다운로드 트리거
        const link = document.createElement('a');
        link.href = "http://172.30.1.16:20080/"+filePath.split("CDN")[1];
        link.download =  element.getAttribute('file-name'); // 파일 이름을 설정하지 않으면 원래 파일 이름으로 다운로드
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }
    
    function downloadFile(element){
		const filePath = element.getAttribute('data-file-path');
		const fileName=element.getAttribute('file-name');
		fetch("/counselor/downloadFile?filePath="+filePath+"&fileName="+fileName,{
			method : "GET"
		}).then(response => {
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.blob(); // 응답을 Blob으로 변환
})
.then(blob => {
    const url = window.URL.createObjectURL(blob); // Blob URL 생성
    const a = document.createElement('a'); // a 태그 생성
    a.href = url; // Blob URL 설정
    a.download = fileName; // 다운로드할 파일 이름 설정
    document.body.appendChild(a); // a 태그를 DOM에 추가
    a.click(); // 클릭 이벤트 트리거
    a.remove(); // a 태그 제거
    window.URL.revokeObjectURL(url); // Blob URL 해제
})
.catch(error => {
    console.error('Fetch error:', error);
});
	}