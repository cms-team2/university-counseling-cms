const loginIcon = document.querySelector("#login_icon");
const logoutIcon = document.querySelector("#logout_icon");

function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) {
        return parts.pop().split(';').shift();
    }
    return null;
}

document.addEventListener('DOMContentLoaded', async () => {
    const loginStatus = getCookie('loginStatus');

    // 아이콘 표시 제어
    logoutIcon.style.display = loginStatus === "loginok" ? "block" : "none";
    loginIcon.style.display = loginStatus === "loginok" ? "none" : "block";

    const menuIcons = document.querySelectorAll('header .menu');
    const sideNav = document.querySelector('.side_area .shownav');

    // Toggle side navigation visibility on menu icon click
    menuIcons.forEach(menuIcon => {
        menuIcon.addEventListener('click', () => {
            sideNav.classList.toggle('show');
        });
    });

    // Toggle dropdown menu visibility on dropdown link click
    const dropdownLinks = document.querySelectorAll('.nav-item.dropdown > a');
    dropdownLinks.forEach(link => {
        link.addEventListener('click', (event) => {
            event.preventDefault();
            const dropdownMenu = link.nextElementSibling;
            if (dropdownMenu && dropdownMenu.classList.contains('dropdown-menu')) {
                dropdownMenu.classList.toggle('show');
            }
        });
    });

	// 권한 가져오기
	async function getAuth() {
	    const response = await fetch('/user/main-menu-auth', { method: 'GET' });
	    if (!response.ok) {
	        throw new Error('Failed to fetch user authority');
	    }
	    return await response.json();
	}

	// 메뉴 가져오기
	async function getMenu(code) {
	    const sendData = new URLSearchParams();
	    sendData.append("code", code);

	    const response = await fetch('/user/main-menu', {
	        method: 'POST',
	        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
	        body: sendData.toString()
	    });

	    if (!response.ok) {
	        throw new Error('Failed to fetch menu');
	    }

	    return await response.json();
	}

	let authority;
	if(loginStatus === "loginok"){
		let au = await getAuth();
		authority = au.userAuth;
	}
	else{
		authority = "N";
	}
	
	if(authority=="C"){
		// 새로운 p 요소 생성
		const chatElement = document.createElement('p');
		chatElement.id = "chat";
		chatElement.style.marginRight = "25px";
		chatElement.innerHTML = `<a href="javascript:;" onclick="newChat()"><img alt="채팅" src="/images/chat.svg"></a>`;

		// id가 login_icon인 요소 찾기
		const loginIcon = document.getElementById("login_icon");

		// login_icon 앞에 chatElement 삽입
		if (loginIcon) {
		    loginIcon.parentNode.insertBefore(chatElement, loginIcon);
		}
	}
	
    const insertMenu = document.getElementById("major_menu");
	const insertNav = document.getElementById("navmajor_menu");

    // 메뉴 가져오기
    let menu_category;
    try {
        menu_category = await getMenu(authority);
    } catch (error) {
        console.error('Error fetching menu:', error);
        return; // 에러 발생 시 종료
    }

    // 메뉴 생성
    menu_category.majorMenu.forEach(m => {
        const newLi = document.createElement('li');
        newLi.className = "nav-item";

        if (m.majorCategoryName === "자가진단" || m.majorCategoryName === "상담일정관리") {
            newLi.innerHTML = `<a class="nav-link" href="${m.majorUrlAddress}">${m.majorCategoryName}</a>`;
        } else if (m.majorCategoryName === "마이페이지") {
            newLi.innerHTML = `<a class="nav-link OnlyUser">${m.majorCategoryName}</a>`;
        } else {
            newLi.classList.add("dropdown");
            let details = `<a class="nav-link" href="${m.majorUrlAddress}">${m.majorCategoryName}</a>
                <ul class="dropdown-menu">`;
            menu_category.subMenu.forEach(s => {
                if (m.majorCategoryCode === s.majorCategoryCode) {
                    details += `<li><a class="dropdown-item" href="${s.submenuUrlAddress}">${s.submenuCategoryName}</a></li>`;
                }
            });
            details += `</ul>`;
            newLi.innerHTML = details;
        }
        insertMenu.appendChild(newLi);
		
		
		
		// 사이드 메뉴 생성
	    const newLi2 = document.createElement('li');
	    newLi2.className = "nav-item";

	    if (m.majorCategoryName === "자가진단" || m.majorCategoryName === "상담일정관리") {
	        newLi2.innerHTML = `<a class="nav-link" href="${m.majorUrlAddress}">${m.majorCategoryName}</a>`;
	    } else if (m.majorCategoryName === "마이페이지") {
	        newLi2.innerHTML = `<a class="nav-link OnlyUser">${m.majorCategoryName}</a>`;
	    } else {
	        newLi2.classList.add("dropdown");
	        let details2 = `<a class="nav-link" href="#">${m.majorCategoryName}</a>
	            <ul class="dropdown-menu">`;
	        menu_category.subMenu.forEach(s => {
	            if (m.majorCategoryCode === s.majorCategoryCode) {
	                details2 += `<li><a class="dropdown-item" href="${s.submenuUrlAddress}">${s.submenuCategoryName}</a></li>`;
	            }
	        });
	        details2 += `</ul>`;
	        newLi2.innerHTML = details2;
	    }
	    insertNav.appendChild(newLi2);
		
    });

    // 마이페이지 접근 처리
    const OnlyUser = document.getElementsByClassName("OnlyUser");
    if (OnlyUser) { // OnlyUser가 존재하는지 확인
		let w=0;
		while(w<OnlyUser.length){
	        OnlyUser[w].addEventListener("click", function () {
	            if (loginStatus !== "loginok") {
	                alert("마이페이지는 로그인 이후 사용가능합니다.");
	            } else {
	                window.location.href = "/user/mypage";
	            }
	        });
			w++;
		}
    }
});

function newChat(){
	const url = `/chat/chatRoom`;
	window.open(url, "", "width=500,height=620");
}
