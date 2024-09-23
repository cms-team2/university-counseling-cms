//이메일 인증
const inputId=document.querySelector("#input_id");									
const inputEmail=document.querySelector("#input_email");
const confirmBox=document.querySelector("#confirm_box");
const verificationConfirm=document.querySelector("#verification_confirm");
const emailCertification=document.querySelector("#email_certification");
const passwordChage=document.querySelector("#password_chage");
const InfoWarning=document.querySelector("#Info_warning");
const verificationCode=document.querySelector("#verification_code");
const verificationWarning=document.querySelector("#verification_warning");
const retransfer=document.querySelector("#retransfer");

let verificationComplete="N";

const queryParams = new URLSearchParams(window.location.search);
const user = queryParams.get('user');

//이메일 인증하기 버튼 클릭시
emailCertification.addEventListener("click",function(){
	if(inputId.value!="" && inputEmail.value!=""){

		fetch("/pw/email-confirm",{
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			body : JSON.stringify({
				"userId" : inputId.value,
				"userEmail" : inputEmail.value
			})
		}).then(response => {
			if(response.ok){	//사용자 인증 성공시
				alert("인증번호가 발송되었습니다.");
				InfoWarning.style.display="none";
				emailCertification.style.display="none";
				verificationConfirm.style.display="block";
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

//인증확인 클릭시
verificationConfirm.addEventListener("click",function(){
	fetch("/pw/verification-confirm",{
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			body : JSON.stringify({
				"userId" : inputId.value,
				"userEmail" : inputEmail.value,
				"verificationCode" : verificationCode.value
			})
		}).then(response => {
			if(response.ok){	//이메일 인증 성공시
				verificationComplete="Y";
				verificationWarning.style.display="none";
				alert("인증이 완료되었습니다.");
				if(user=="admin"){
					location.href="/pw/change?user=admin";
				} else{				
					location.href="/pw/change";
				}
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

//인증번호 재전송 클릭시
retransfer.addEventListener("click",function(){

	if(inputId.value!="" && inputEmail.value!=""){

		fetch("/pw/retransfer",{
			method : "POST",
			headers : {
				"Content-Type" : "application/json"
			},
			body : JSON.stringify({
				"userId" : inputId.value,
				"userEmail" : inputEmail.value
			})
		}).then(response => {
			if(response.ok){	//사용자 인증 성공시
				alert("인증번호가 재발송되었습니다.");
				InfoWarning.style.display="none";
				emailCertification.style.display="none";
				retransfer.style.display="block";
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



