document.addEventListener('DOMContentLoaded', function() {
    const navLinks = document.querySelectorAll('.nav-link');
    const submenuItems = document.querySelectorAll('.submenu-item');
    const collapsibles = document.querySelectorAll('.collapse');

    // 각 링크 클릭 시 active 클래스 추가 및 스타일 변경
    navLinks.forEach(link => {
        link.addEventListener('click', function() {
            navLinks.forEach(link => link.classList.remove('active')); // 다른 메뉴의 active 제거
            this.classList.add('active');
            this.style.color = "white";  // 클릭된 메뉴를 하얀 글씨로 변경

            // 클릭된 메뉴의 상태 저장
            sessionStorage.setItem('activeLink', this.getAttribute('href'));
        });
    });

    submenuItems.forEach(item => {
        item.addEventListener('click', function() {
            submenuItems.forEach(subitem => subitem.classList.remove('active')); // 다른 서브메뉴의 active 제거
            this.classList.add('active');
            this.style.color = "white";  // 클릭된 서브메뉴를 하얀 글씨로 변경

            // 부모 collapse가 닫히지 않도록 상태 저장
            const parentCollapse = this.closest('.collapse');
            if (parentCollapse) {
                sessionStorage.setItem('openCollapse', parentCollapse.id);
            }
        });
    });

    // 페이지 로드 시 열린 토글 메뉴와 active 메뉴 복원
    const activeLink = sessionStorage.getItem('activeLink');
    if (activeLink) {
        const activeNavLink = document.querySelector(`a[href='${activeLink}']`);
        if (activeNavLink) {
            activeNavLink.classList.add('active');
            activeNavLink.style.color = "white";  // 페이지가 로드될 때도 하얀 글씨로 표시
        }
    }

    const openCollapse = sessionStorage.getItem('openCollapse');
    if (openCollapse) {
        const collapseElement = document.getElementById(openCollapse);
        if (collapseElement) {
            const collapseInstance = new bootstrap.Collapse(collapseElement, {
                toggle: false
            });
            collapseInstance.show(); // 토글 메뉴를 열어둔 상태로 유지
        }
    }

    // 현재 URL 기반 active 클래스 유지
    const currentPath = window.location.pathname;
    navLinks.forEach(link => {
        const linkPath = new URL(link.href).pathname;
        if (currentPath === linkPath) {
            link.classList.add('active');
            link.style.color = "white";  // 현재 페이지에 있는 메뉴 하얀색으로 표시
        }
    });
});
