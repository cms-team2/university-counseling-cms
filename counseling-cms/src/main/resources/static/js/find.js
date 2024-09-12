//이메일 인증
const inputName=document.querySelector("#input_name");									
const inputEmail=document.querySelector("#input_email");
const confirmBox=document.querySelector("#confirm_box");
const verificationConfirm=document.querySelector("#verification_confirm");
const emailCertification=document.querySelector("#email_certification");
const passwordChage=document.querySelector("#password_chage");
const InfoWarning=document.querySelector("#Info_warning");
const verificationCode=document.querySelector("#verification_code");
const verificationWarning=document.querySelector("#verification_warning");
let verificationComplete="N";

emailCertification.addEventListener("click",function(){
	if(inputName.value!="" && inputEmail.value!=""){

		fetch("/pw/email-confirm",{
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			body : JSON.stringify({
				"userName" : inputName.value,
				"userEmail" : inputEmail.value
			})
		}).then(response => {
			console.log(response);
			if(response.ok){	//사용자 인증 성공시
				alert("인증번호가 발송되었습니다.");
				InfoWarning.style.display="none";
				confirmBox.style.display="flex";
			} else if(response.status==704){
				InfoWarning.style.display="block";
			}
		}).catch(error => {
			console.error('Fetch error:', error);
			InfoWarning.style.display="block";
		});
		
		
	}	
});

verificationConfirm.addEventListener("click",function(){
	console.log(verificationCode.value);
	fetch("/pw/verification-confirm",{
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			body : JSON.stringify({
				"userName" : inputName.value,
				"userEmail" : inputEmail.value,
				"verificationCode" : verificationCode.value
			})
		}).then(response => {
			console.log(response);
			if(response.ok){	//이메일 인증 성공시
				verificationComplete="Y";
				verificationWarning.style.display="none";
				alert("인증 완료되었습니다.");
				location.href="/pw/change";
			} else if(response.status==704){
				verificationWarning.style.display="block";
				verificationCode.value="";
				verificationCode.focus();
			}
		}).catch(error => {
			console.error('Fetch error:', error);
			verificationWarning.style.display="block";
			
		});
});




