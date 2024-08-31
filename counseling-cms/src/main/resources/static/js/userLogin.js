let inputId=document.getElementById("input_id");
let counselorLogin=document.getElementById("counselor_login");
let studentLogin=document.getElementById("student_login");

document.querySelector("#student_login").addEventListener("click",function(){
	this.className="btn btn-success  btn-lg";
	counselorLogin.className="btn btn-outline-success  btn-lg";
	inputId.placeholder="학번";
});
document.querySelector("#counselor_login").addEventListener("click",function(){
	this.className="btn btn-success  btn-lg";
	studentLogin.className="btn btn-outline-success  btn-lg";
	inputId.placeholder="사번";
});