const today=document.getElementById("today").value;
const tomorrow=document.querySelector("#tomorrow").value;
// date input에 min 속성 설정
document.getElementById('applyDate').setAttribute('min',tomorrow);

const createApply = () => {
	const frmApply = document.querySelector("#frmApply");
	const formData = new FormData(frmApply);

	fetch("/counseling-apply",{
		method : "POST",
		body : formData
	})
	.then(response => {
		if(response.ok){
			alert('상담 신청이 완료되었습니다.\n상담사 배정 후 상담 일정을 안내해드리겠습니다.');
			location.href = '/';
		}else{
			alert('서버 오류로 인해 상담 신청에 실패하였습니다')
			return false;
		}
	})
	.catch(error => {
		alert('서버 오류로 인해 상담 신청에 실패하였습니다')
		return false;
	})
	
}

document.querySelector("#btnApply").addEventListener("click", createApply);

