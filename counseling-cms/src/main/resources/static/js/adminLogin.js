const inputId=document.querySelector("#input_id");
const inputPassword=document.querySelector("#input_password");
const adminLogin=document.querySelector(".form-signin");
const warningText=document.querySelector("#warning_text");
const autoLogin=document.querySelector("#auto_login");
let clickPrevent=false;

adminLogin.addEventListener("submit",function(event){

	event.preventDefault();
		
	const userInfo = {
    	userId: inputId.value,
    	userPassword: inputPassword.value,
    	autoLogin : autoLogin.value,
    	loginPart : "admin"
	};
	if(clickPrevent==false){	
		fetch("/user/admin-loginok", {
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
     			 const waitTime = 5 * 60 * 1000; // 5분을 밀리초로 변환
     			 setTimeout(() => {
					clickCount=0;
       			 	updateFailCount(inputId.value);
       			 }, waitTime);
       			 
       			window.addEventListener('beforeunload', function(e) {
                    const confirmationMessage = '페이지를 떠나면 로그인 제한 시간이 초기화됩니다. 정말 떠나시겠습니까?';
                    e.preventDefault();
                    e.returnValue = confirmationMessage;
                    return confirmationMessage;
                });

				clickPrevent=true;
				loginTimer();

			} else{
				warningText.style.display="block";
			}
		}).then(token=>{
			if(token){
				console.log(token);
				alert("로그인 되었습니다.");
				location.href="/admin/apply-list";
			}
		})
		.catch(error => {
    		console.error('Fetch error:', error);
		});
	} else{
		alert('로그인 시도 횟수가 초과되었습니다.\n잠시 후에 다시 시도해주세요.');	
	}

});

<<<<<<< HEAD
function showCustomWarning() {
    alert('페이지를 떠나면 로그인 제한 시간이 초기화됩니다. 정말 떠나시겠습니까?');
}

//토큰 저장
function saveToken(token) {
    sessionStorage.setItem('accessToken', token);
}
=======
>>>>>>> f961a43e2eb46128816762f3a34cbc71883b4759

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
			
							} else if(response.status==435){
								updateFailCount(userId);
							}
							
						}).catch(error=>{
							console.error('Fetch error:', error);
						});
}

function loginTimer(){
    const waitTime = 1 * 60; // 5분을 초 단위로 설정
    
    function updateTimer(seconds) {
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = seconds % 60;
        warningText.style.display="block";
        warningText.textContent = `로그인 시도 횟수 초과 [${minutes}:${remainingSeconds.toString().padStart(2, '0')} 남음]`;
    }
    
    let timeRemaining = waitTime;
    const interval = setInterval(() => {
        updateTimer(timeRemaining);
        timeRemaining -= 1;
        
        if (timeRemaining < 0) {
            clearInterval(interval);
            clickPrevent=false;
       		updateFailCount(inputId.value);
       		warningText.style.display="none";
            warningText.textContent = '아이디 및 패스워드를 확인하세요.';
        }
    }, 1000); // 1초마다 업데이트
}

