const loginIcon=document.querySelector("#login_icon");
const logoutIcon=document.querySelector("#logout_icon");

function getCookie(name) {
    // 쿠키 문자열을 `; name=value` 형식으로 분리
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    
    // 쿠키가 존재하는 경우 값 반환
    if (parts.length === 2) {
        return parts.pop().split(';').shift();
    }
    
    // 쿠키가 존재하지 않는 경우 null 반환
    return null;
}

document.addEventListener('DOMContentLoaded', () => {
	
	const loginStatus = getCookie('loginStatus');
	
	// 아이콘 표시를 제어합니다.
	if (loginStatus=="loginok") {
        logoutIcon.style.display = "block";
        loginIcon.style.display = "none";
	} else {
        loginIcon.style.display = "block";
        logoutIcon.style.display = "none";
	}
	
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
});



