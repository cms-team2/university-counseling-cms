Kakao.init('9c32499bc43a7e0678ac17938e7d56d3');

function kakao_chat_login() {
    Kakao.Auth.login({
        success: function(authObj) {
            // 로그인 성공 시 인가 코드로 액세스 토큰 요청
            getAccessToken(authObj);
            alert("로그인 성공");
        },
        fail: function(err) {
            console.error('로그인 오류:', err);
            alert("로그인 실패: " + err.message);
        },
    });
}

function getAccessToken(authObj) {
    const code = authObj.code; // 인가 코드

    fetch('https://kauth.kakao.com/oauth/token', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams({
            grant_type: 'authorization_code',
            client_id: '9c32499bc43a7e0678ac17938e7d56d3', // 카카오 개발자에서 발급받은 앱 키
            redirect_uri: 'http://172.30.1.6:7777/user/kakaook', // 리디렉션 URI
            code: code,
        })
    })
    .then(response => response.json())
    .then(data => {
        const accessToken = data.access_token;
        console.log('액세스 토큰:', accessToken);
        // 액세스 토큰으로 메시지 전송
        sendMessage(accessToken);
    })
    .catch(error => {
        console.error('액세스 토큰 요청 오류:', error);
    });
}

function sendMessage(accessToken) {
    Kakao.API.request({
        url: '/v2/api/talk/memo/default/send',
        data: {
            template_object: {
                object_type: 'text',
                text: '채팅 상담을 원하시면 @cms_choongang 검색해보세요!',
                link: {
                    web_url: 'http://172.30.1.6:7777/user/message'
                }
            }
        },
        headers: {
            'Authorization': 'Bearer ' + accessToken // 액세스 토큰 추가
        }
    })
    .then(function(response) {
        console.log('메시지 전송 성공:', response);
        alert("메시지 전송 성공");
    })
    .catch(function(error) {
        console.error('메시지 전송 오류:', error);
        alert("메시지 전송 실패");
    });
}


function gofaq(){
	const newSection = document.createElement('section');
	newSection.className = 'chatbot';
	newSection.innerHTML = `
	<div class="text">
		<p>faq</p>
	</div>
	<div class="chatbot-btn">
	<button type="button" class="btn gohome">처음으로</button>
	<button type="button" class="btn chat_kakao">카카오톡 상담사 연결</button>
	</div>
	`;
	const chatbotContents = document.getElementById('chatbot_contents');
	chatbotContents.appendChild(newSection);
}

function godirections(){
	const newSection = document.createElement('section');
	newSection.className = 'chatbot';
	newSection.innerHTML = `
	<div class="text">
		<p>오시는 길은 다음과 같습니다.</p>
		<p>주소</p>
	</div>
	<div class="chatbot-btn">
	<button type="button" class="btn gohome">처음으로</button>
	<button type="button" class="btn chat_kakao">카카오톡 상담사 연결</button>
	</div>
	`;
	const chatbotContents = document.getElementById('chatbot_contents');
	chatbotContents.appendChild(newSection);
}

function gohour(){
	const newSection = document.createElement('section');
	newSection.className = 'chatbot';
	newSection.innerHTML = `
	<div class="text">
		<p>운영 시간은 아래와 같습니다.</p>
		<p>평일 09:00 ~ 18:00</p>
		<p>주말,공휴일 휴무</p>
	</div>
	<div class="chatbot-btn">
		<button type="button" class="btn gohome">처음으로</button>
		<button type="button" class="btn chat_kakao">카카오톡 상담사 연결</button>
	</div>
	`;
	const chatbotContents = document.getElementById('chatbot_contents');
	chatbotContents.appendChild(newSection);
}

function gohome(){
	const newSection = document.createElement('section');
	newSection.className = 'home-chatbot';

	// 새로운 내용 설정
	newSection.innerHTML = `
	<section class="home-chatbot">
		<div class="home-chatbot-ment">
			<h4>환영합니다!</h4>
			<span>무엇이 궁금하신가요?</span>
		</div>
		<div class="home-chatbot-btn">
			<button type="button" class="btn business_hour">운영 시간</button>
			<button type="button" class="btn directions">오시는 길</button>
			<button type="button" class="btn faq">FAQ</button>
			<button type="button" class="btn chat_kakao">카카오톡 상담사 연결</button>
		</div>
	</section>
	`;

	// 기존의 chatbot_contents에 추가
	const chatbotContents = document.getElementById('chatbot_contents');
	chatbotContents.appendChild(newSection);
}

document.addEventListener("DOMContentLoaded", function () {
	
	let go_home_img = document.getElementById('chatbot_home');
	go_home_img.addEventListener('click', function(){
		gohome();
	});

	let chatbotContents = document.getElementById('chatbot_contents');
	chatbotContents.addEventListener('click', function(event) {
	    const target = event.target;
	    if(target.matches('.btn')) {
	        const newSection = document.createElement('section');
	        newSection.className = 'customer';
	        newSection.innerHTML = `
	            <div class="ment-customer">
	                <h5>${target.textContent}</h5>
	            </div>`;
	        chatbotContents.appendChild(newSection);
		}
	    if (target.matches('#chatbot_home')) {
	        gohome();
		} else if (target.matches('.business_hour')) {
	        gohour();
	    } else if (target.matches('.directions')) {
	        godirections();
	    } else if (target.matches('.faq')) {
	        gofaq();
	    } else if (target.matches('.chat_kakao')) {
	        kakao_chat_login();
	    } else if (target.matches('.gohome')) {
	        gohome();
	    }
	});
});