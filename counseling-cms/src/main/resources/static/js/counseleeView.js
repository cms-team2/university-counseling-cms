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

function viewFile(element) {
        const filePath = element.getAttribute('data-file-path'); // 클릭한 span의 데이터 속성 가져오기
    	const fileName=element.getAttribute('date-file-name');

		if(fileName.split(".")[1]=='hwp'){
			alert("hwp 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		} else if(fileName.split(".")[1]=='csv'){
			alert("csv 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		} else if(fileName.split(".")[1]=='docx'){
			alert("docx 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		} else if(fileName.split(".")[1]=='pptx'){
			alert("pptx 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		} else if(fileName.split(".")[1]=='xls'){
			alert("xls 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		}  else{
	        window.open("http://www.citcc.site/"+filePath.split("CDN")[1], '_blank');		
		}
		
    }
    
 function downloadFile(element){
		const fileSeq = element.getAttribute('data-file-seq');
		fetch("/counselor/downloadFile?fileSeq="+fileSeq,{
			 method: 'GET',
        	 headers: {
            // 필요한 경우 추가 헤더를 설정
            'Content-Type': 'application/json'
        }
   		 }).then(response => {
        	if (!response.ok) {
            	throw new Error('파일 다운로드에 실패했습니다.');
        	}
        	return response.blob(); // Blob 형식으로 변환
    	}).then(blob => {
			
        	const url = window.URL.createObjectURL(blob); // Blob URL 생성
        	const a = document.createElement('a'); // 앵커 요소 생성
        	a.href = url; // 생성된 Blob URL 설정
        	a.download = element.getAttribute('date-file-name'); // 다운로드 파일명 설정 (빈 문자열로 두면 서버의 파일명 사용)
        	document.body.appendChild(a); // DOM에 추가
        	a.click(); // 클릭 이벤트 발생
        	a.remove(); // 앵커 요소 제거
        	window.URL.revokeObjectURL(url); // Blob URL 해제
    	}).catch(error => {
        console.error('Error:', error);
    });
}