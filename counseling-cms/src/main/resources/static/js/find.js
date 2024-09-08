//이메일 인증
let inputName=document.querySelector("#input_name");									
let inputEmail=document.querySelector("#input_email");
let confirmBox=document.querySelector("#confirm_box");
let emailConfirm=document.querySelector("#eamil_confirm");
let emailCertification=document.querySelector("#email_certification");


emailCertification.addEventListener("click",function(){
	if(inputName.value!="" && inputEmail.value!=""){
		confirmBox.style.display="";
		this.className="btn btn-lg btn btn-success  btn-block";
		this.value="비밀번호 변경";
	}	
});


