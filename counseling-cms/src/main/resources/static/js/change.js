//비밀번호 변경
let inputPassword=document.querySelector("#input_password");
let IdentifyPassword=document.querySelector("#Identify_password");
let dangerPassword=document.querySelector("#danger_password");
let passwordChange=document.querySelector("#password_change");

passwordChange.addEventListener("click",function(){
	if(inputPassword.value!=IdentifyPassword.value){
		dangerPassword.style.display="block";	
	} else if(inputPassword.value==IdentifyPassword.value){
		dangerPassword.style.display="none";	
	}
});