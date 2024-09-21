const saveRecord=document.querySelector("#save_record");
const modifyRecord=document.querySelector("#modify_record");
const recordList=document.querySelector("#record_list");
const saveContent=document.querySelector("#save_content");
const allContent=document.querySelector("#all_content");
const summaryContent=document.querySelector("#summary_content");
const viewAll=document.querySelector("#view_all");
const saveTitle=document.querySelector("#save_title");

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

recordList.addEventListener("click",function(){
	location.href="/counselor/counselingRecordList";
})

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
	console.log(record);
	
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

  function downloadFile(element) {
        const filePath = element.getAttribute('data-file-path'); // 클릭한 span의 데이터 속성 가져오기

        // a 태그를 생성하여 다운로드 트리거
        const link = document.createElement('a');
        link.href = "http://172.30.1.16:20080/"+filePath.split("CDN")[1];
        link.download =  element.getAttribute('file-name'); // 파일 이름을 설정하지 않으면 원래 파일 이름으로 다운로드
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    }