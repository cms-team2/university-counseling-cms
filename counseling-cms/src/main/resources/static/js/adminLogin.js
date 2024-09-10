const inputId=document.querySelector("#input_id");
const inputPassword=document.querySelector("#input_password");
const adminLogin=document.querySelector(".form-signin");
const warningText=document.querySelector("#warning_text");



adminLogin.addEventListener("submit",function(event){

	event.preventDefault();
	
	const userInfo = {
    	userId: inputId.value,
    	userPassword: inputPassword.value
	};
	
	fetch("/user/loginok", {
   		method: "POST",
   		headers: {
	
        	"Content-Type": "application/json",
        	
    	},
    	body: JSON.stringify(userInfo),
	})
	.then(response => {
    	if (response.ok) {
        	return response.text();
    	} else if(response.status==433 || response.status==434) {
			warningText.style.display="block";
		} else{
			warningText.style.display="block";
		}
    
	})
	.then(token=>{
		if(token){
			console.log(token);
			saveToken(token);
			alert("로그인 되었습니다.");
			location.href="/admin/apply-list";
		}
	})
	.catch(error => {
    	console.error('Fetch error:', error);
	});
});

//토큰 저장
function saveToken(token) {
    sessionStorage.setItem('accessToken', token);
}

// 토큰 get
function getToken() {
    return sessionStorage.getItem('accessToken');
}

// 토큰 삭제
function removeToken() {
    sessionStorage.removeItem('authToken');
}