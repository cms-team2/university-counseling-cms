//비밀번호 변경
const inputPassword=document.querySelector("#input_password");
const IdentifyPassword=document.querySelector("#Identify_password");
const dangerPassword=document.querySelector("#danger_password");
const passwordChange=document.querySelector(".form-signin");


const queryParams = new URLSearchParams(window.location.search);
const user = queryParams.get('user');

IdentifyPassword.addEventListener("input",function(){
		if(inputPassword.value!=IdentifyPassword.value){
			dangerPassword.style.display="block";	
		} else if(inputPassword.value==IdentifyPassword.value){
			dangerPassword.style.display="none";
		}
});

passwordChange.addEventListener("click",function(event){
	event.preventDefault();
	
	if(inputPassword.value==IdentifyPassword.value){
		dangerPassword.style.display="none";
		fetch("/pw/update",{
			method : "POST",
			headers : {
				"Content-Type" : "application/text"
			},
			body : inputPassword.value,
		}).then(response => {
			if(response.ok){	//이메일 인증 성공시
			console.log(user);
				alert("비밀번호가 변경되었습니다.");
				if(user=="admin"){
					location.href="/admin/login";				
				} else{
					location.href="/user/login";	
				}
			} else if(response.status==704){
				alert("오류가 발생하였습니다.\n다시 시도해주세요.");
			}
		}).catch(error => {
			console.error('Fetch error:', error);
			
		});
	}
});