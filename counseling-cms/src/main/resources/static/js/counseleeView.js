const writeRecord=document.querySelector("#write_record");

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const applyNo=urlParams.get('applyNo');

writeRecord.addEventListener("click", function(){
	console.log(applyNo);
	if(applyNo != null){
		location.href="/counselor/writeCounselingRecord?applyNo="+applyNo;
	}
});