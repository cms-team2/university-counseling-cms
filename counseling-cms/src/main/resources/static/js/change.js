//비밀번호 변경
const inputPassword=document.querySelector("#input_password");
const IdentifyPassword=document.querySelector("#Identify_password");
const dangerPassword=document.querySelector("#danger_password");
const passwordChange=document.querySelector("#password_change");

passwordChange.addEventListener("click",function(){
	if(inputPassword.value!=IdentifyPassword.value){
		dangerPassword.style.display="block";	
	} else if(inputPassword.value==IdentifyPassword.value){
		dangerPassword.style.display="none";
		fetch("/pw/update",{
			method : "POST",
			headers : {
				"Content-Type" : "application/text"
			},
			body : inputPassword.value,
		}).then(response => {
			console.log(response);
			if(response.ok){	//이메일 인증 성공시
				alert("비밀번호가 변경되었습니다.");
				location.href="/admin/login";
			} else if(response.status==704){
				alert("오류가 발생하였습니다.\n다시 시도해주세요.");
			}
		}).catch(error => {
			console.error('Fetch error:', error);
			
		});
	}
});