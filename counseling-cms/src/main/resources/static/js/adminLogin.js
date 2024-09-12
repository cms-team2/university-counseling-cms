const inputId=document.querySelector("#input_id");
const inputPassword=document.querySelector("#input_password");
const adminLogin=document.querySelector(".form-signin");
const warningText=document.querySelector("#warning_text");
const autoLogin=document.querySelector("#auto_login");
let clickCount=0;

adminLogin.addEventListener("submit",function(event){

	event.preventDefault();
	
	clickCount++;
	
	const userInfo = {
    	userId: inputId.value,
    	userPassword: inputPassword.value,
    	autoLogin : autoLogin.value
	};

	if(clickCount<7){
		
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
			} else if(response.status==435){
				 // 5분 타이머 설정
     		 	alert('로그인 시도 횟수가 초과되었습니다.\n잠시후 다시 시도해주세요.');

      			// 5분 후에 다시 시도 가능 알림
     			 const waitTime = 0.5* 60 * 1000; // 5분을 밀리초로 변환
     			 setTimeout(() => {
					clickCount=0;
       			 	updateFailCount(inputId.value);
       			 }, waitTime);
			} else{
				warningText.style.display="block";
			}
		}).then(token=>{
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
	} else{
		alert('로그인 시도 횟수가 초과되었습니다.\n잠시후 다시 시도해주세요.');
	}

});

//토큰 저장
function saveToken(token) {
    sessionStorage.setItem('accessToken', token);
}

function updateFailCount(userId){
	
	const userInfo = {
   		userId: userId
	};
	
	fetch("/pw/update-pw-fail",{
						method : "POST",
						headers: {
        					"Content-Type": "application/json",
    					},
    					body: JSON.stringify(userInfo),
						}).then(response => {
							if(response.ok){
	       					 	alert('로그인이 가능합니다.');							
							} else if(response.status==435){
								updateFailCount(userId);
							}
							
						}).catch(error=>{
							console.error('Fetch error:', error);
						});
}