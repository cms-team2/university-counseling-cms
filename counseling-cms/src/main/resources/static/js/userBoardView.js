function downloadFile(element){
		const fileSeq = element.getAttribute('data-file-seq');
		fetch("/user/downloadFile?fileSeq="+fileSeq,{
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