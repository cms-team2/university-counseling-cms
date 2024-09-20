function application(code) {
	let loginCheck = getCookie('loginStatus');
	if(loginCheck != 'loginok'){
		let currentUrl = window.location.href;
		if(confirm('해당 서비스는 로그인이 필요한 서비스입니다.\n이동하시겠습니까?')){
			document.cookie = `currentUrl=${currentUrl}; path=/`;
			location.href = '/user/login';
		}else{
			return false;
		}
	}else{
		location.href="/user/application?counseling="+code;	
	}
}

function getCookie(name) {
    let cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        let cookie = cookies[i].trim(); // 공백 제거
        // 쿠키 이름이 일치하는지 확인
        if (cookie.startsWith(name + '=')) {
            return cookie.substring(name.length + 1); // 쿠키 값 반환
        }
    }
    return null; // 쿠키가 없을 때 null 반환
}