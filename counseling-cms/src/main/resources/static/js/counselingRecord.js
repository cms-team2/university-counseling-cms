const saveRecord=document.querySelector("#save_record");
const modifyRecord=document.querySelector("#modify_record");
const saveContent=document.querySelector("#save_content");
const allContent=document.querySelector("#all_content");
const summaryContent=document.querySelector("#summary_content");
const viewAll=document.querySelector("#view_all");
const saveTitle=document.querySelector("#save_title");
const saveConsultationSchedule=document.querySelector("#save_consultation_schedule");
const newDate = document.querySelector("#new_date");
const newTime=document.querySelector("#new_time");

const recordCount=document.querySelector("#record_count").value;
const today=document.querySelector("#today").value;

//URL 파라미터값
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const applyNo=urlParams.get('applyNo');


if(recordCount=='0'){
	modifyRecord.style.display="none";
	saveRecord.style.display="inline-block";
} else{
	const recordDate=document.querySelector("#record_date").value;
	if(today==recordDate){
		saveTitle.readOnly=false;
		saveContent.readOnly=false;
		modifyRecord.style.display="inline-block";	
	} else{
		modifyRecord.style.display="none";
	}
	saveRecord.style.display="none";
}

viewAll.addEventListener("click", function(){
	if(this.textContent=="▼"){
		this.textContent="▲";
		summaryContent.style.display="none";
		allContent.style.display="block";
	}else if(this.textContent=="▲"){
				this.textContent="▼";
		summaryContent.style.display="block";
		allContent.style.display="none";
	}
});

saveRecord.addEventListener("click", function(){
	const recordTitle=document.querySelector("#record_title").value;
	const recordContent=document.querySelector("#record_content").value;
	const consultationCategory=document.querySelector("#consultation_category").value;
	const studentNo=document.querySelector("#student_no").value;
	const consultationDate=document.querySelector("#consultation_date").value;

	const record={
		recordTitle : recordTitle,
		recordContent : recordContent,
		applyNo : applyNo,
		consultationCategory : consultationCategory,
		studentNo : studentNo,
		consultationDate : consultationDate
	};
	
	if(recordTitle != "" && recordContent !=""){
		fetch("/counselor/counselingRecordSave",{
			method : "POST",
			headers : {
				"Content-Type": "application/json",
			},
			body : JSON.stringify(record),
		}).then(response => {
			if(response.ok){
				alert("상담일지가 저장되었습니다.");
				location.href="/counselor/counselingRecordList";
			} else{
				alert("오류가 발생하였습니다.");
			}
		}).catch(error => {
			console.error('Fetch error:', error);
		});
	}
});

modifyRecord.addEventListener("click", function(){
	const record={
		recordTitle : saveTitle.value,
		recordContent : saveContent.value,
		applyNo : applyNo
	};

	if(saveTitle.value != "" && saveContent.value !=""){
		fetch("/counselor/counselingRecordModify",{
			method : "POST",
			headers : {
				"Content-Type": "application/json",
			},
			body : JSON.stringify(record),
		}).then(response => {
			if(response.ok){
				alert("상담일지가 수정되었습니다.");
				location.href="/counselor/counselingRecordList";
			} else{
				alert("오류가 발생하였습니다.\n다시 시도해 주세요.");
			}
		}).catch(error => {
			console.error('Fetch error:', error);
		});
	}
});

function viewFile(element) {
        const filePath = element.getAttribute('data-file-path'); // 클릭한 span의 데이터 속성 가져오기
		const fileName=element.getAttribute('date-file-name');
		console.log(fileName.split(".")[1]);
		if(fileName.split(".")[1]=='hwp'){
			alert("한글 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		} else if(fileName.split(".")[1]=='csv'){
			alert("엑셀 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		} else if(fileName.split(".")[1]=='docx'){
			alert("워드 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		} else if(fileName.split(".")[1]=='pptx'){
			alert("pptx 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		}else if(fileName.split(".")[1]=='xls'){
			alert("xls 파일은 미리 보실 수 없습니다.\n다운로드 버튼을 눌러주세요.");
		} else{
	        window.open("http://172.30.1.16:20080/"+filePath.split("CDN")[1], '_blank');		
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
    
saveConsultationSchedule.addEventListener("click", function(){
	const studentNo=document.querySelector("#student_no");
	const newConsultationCategory=document.querySelector("#new_consultation_category");
	const newConsultationWay=document.querySelector("#new_consultation_way");
	const newApplyContent=document.querySelector("#new_apply_content");
	const applyInfo={
			"studentNo" : studentNo.value,
			"consultationDate" : newDate.value+" "+newTime.value,
			"consultationWay" : newConsultationWay.value,
			"consultationCategory" : newConsultationCategory.value,
			"applyContent" : newApplyContent.value
	};
	if(newDate.value.value != "" && newTime.value != ""){
		fetch("/counselor/addApply",{
			method : "POST",
			headers : {
				"content-type" : "application/json"
			},
			body : JSON.stringify(applyInfo)
		}).then(response => {
			if(response.ok){
				alert("상담 신청이 완료되었습니다.");
				location.href="/counselor/getCounseleeList";
			} else{
				alert("오류가 발생하였습니다.\n다시 시도해 주세요.");
			}
		}).catch(error => {
			console.error('Fetch error:', error);
		});
	} else{
		alert("날짜 및 시간을 선택해주세요.");
	}

});

document.addEventListener('DOMContentLoaded', function () {
	const tomorrow=document.querySelector("#tomorrow").value;

    // input의 min 속성에 오늘 날짜를 설정합니다.
    newDate.setAttribute('min', tomorrow);
});

newDate.addEventListener("change", function(){
	  const selectedDate = new Date(this.value);
      const dayOfWeek = selectedDate.getDay();
      
     if (dayOfWeek === 0 || dayOfWeek === 6) {
	 	alert("토요일과 일요일은 선택할 수 없습니다.");
		this.value = ""; // 선택 초기화
	}
	for(var f=1; f<newTime.children.length; f++){
		if(f==5){
			newTime.children[f].disabled="disabled";
		} else{		
			newTime.children[f].disabled=false;
		}
	}
	getTodaySchedule();
});

newTime.addEventListener("change", function(){
	if(newDate.value==""){
		alert("날짜를 먼저 선택해주셔야 합니다.");
		newTime.value="";
	}
});

function getTodaySchedule(){
	fetch("/counselor/todaySchedule",{
		method : "GET",
		headers : {
			"Content-Type" : "application/json"
		}
	}).then(response => {
		 if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
	}).then(data => {
		data.forEach((element, index) => {
			if(element.split(" ")[0]==newDate.value){
				for(var f=1; f<newTime.children.length; f++){
					const timeData=newDate.value+" "+newTime.children[f].value;
					if(element==timeData){
						newTime.children[f].disabled="disabled";
					} 
				}
			}
		});
	}).catch(error => {
		console.error('Fetch error:', error);
	});
}