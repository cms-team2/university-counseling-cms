let inputName=document.getElementById("input_name");
let inputEmail=document.getElementById("input_email");
let confirmBox=document.getElementById("confirm_box");
let emailConfirm=document.getElementById("eamil_confirm");
let emailCertification=document.getElementById("email_certification");


document.querySelector("#email_certification").addEventListener("click",function(){
	if(inputName.value!="" && inputEmail.value!=""){
		confirmBox.style.display="";
		this.className="btn btn-lg btn btn-success  btn-block";
		emailCertification.value="비밀번호 변경";
	}	
});