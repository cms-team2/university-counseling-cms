let inputId=document.querySelector("#input_id");
let counselorLogin=document.querySelector("#counselor_login");
let studentLogin=document.querySelector("#student_login");

studentLogin.addEventListener("click",function(){
	this.className="btn btn-success  btn-lg";
	counselorLogin.className="btn btn-outline-success  btn-lg";
	inputId.placeholder="학번";
});
counselorLogin.addEventListener("click",function(){
	this.className="btn btn-success  btn-lg";
	studentLogin.className="btn btn-outline-success  btn-lg";
	inputId.placeholder="사번";
});