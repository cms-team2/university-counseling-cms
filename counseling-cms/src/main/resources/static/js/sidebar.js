document.addEventListener('DOMContentLoaded', function() {
    const navLinks = document.querySelectorAll('.nav-link');
    const submenuItems = document.querySelectorAll('.submenu-item'); // 서브메뉴를 위한 선택자 추가
   	const collapse = document.querySelectorAll('.collapse');
	const schedule = document.querySelector('.schedule');
    // 각 링크 클릭 시 active 클래스 추가 및 collapsed 처리
    navLinks.forEach(link => {
        link.addEventListener('click', function() {
			
			if(this.getAttribute('toggle')){
				schedule.classList.add('show');
			}
			
            console.log(`클릭된 메뉴: ${this.href}`);
			this.style.color = "white";
        });
    });


    // 현재 URL 기반 active 클래스 유지
    const currentPath = window.location.pathname;
    navLinks.forEach(link => {
        const linkPath = new URL(link.href).pathname;
		console.log("currentPath:" + currentPath);
		console.log("linkPath:" + linkPath);
        if (currentPath === linkPath) {
			console.log(link)
            link.classList.add('active');
            console.log(`현재 URL과 일치하는 링크: ${link.href}`);
			
			/*if(link.getAttribute('toggle')){
				console.log("test2222")
			
				document.querySelector('.'+link.getAttribute('toggle')).classList.add('show');
			}*/
			

            // 서브메뉴를 위해 부모 collapse를 열어줌
            const parentCollapse = link.closest('.collapse');
            if (parentCollapse) {
                new bootstrap.Collapse(parentCollapse, {
                    toggle: true
                });
            }
        }
    });

   
});
