const saveRecord=document.querySelector("#save_record");
const modifyRecord=document.querySelector("#modify_record");
const recordList=document.querySelector("#record_list");
const textArea=document.querySelector("#content");
const allContent=document.querySelector("#all_content");
const summaryContent=document.querySelector("#summary_content");
const viewAll=document.querySelector("#view_all");

const recordCount=document.querySelector("#record_count").value;
const recordDate=document.querySelector("#record_date").value;
const today=document.querySelector("#today").value;


if(recordCount=='0'){
	modifyRecord.style.display="none";
	saveRecord.style.display="inline-block";
} else{
	if(today==recordDate){
		textArea.readOnly=false;
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