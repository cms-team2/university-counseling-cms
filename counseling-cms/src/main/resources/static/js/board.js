

document.addEventListener('DOMContentLoaded', () => {
 


    const currentPath = window.location.pathname;
    const boardMenuItems = document.querySelectorAll('.side_nav > nav a');
    boardMenuItems.forEach(item => {
        const href = item.getAttribute('href').split("/")[2];
        if (currentPath.includes(href)) {
            item.parentElement.classList.add('selected');
        }
    });

    const counselorMenuItems = document.querySelectorAll('.side_nav ul.submenu li a');
    counselorMenuItems.forEach(item => {
        const href = item.getAttribute('href').split("/")[2];
        if (currentPath.includes(href)) {
            const grandparentLi = item.closest('li').parentElement.closest('div');
            if (grandparentLi) {
                grandparentLi.classList.add('active');
            }
            item.parentElement.classList.add('selected');
        }
    });

    const menuToggles = document.querySelectorAll('.menu-toggle');
    menuToggles.forEach(toggle => {
        toggle.addEventListener('click', function(event) {
            event.preventDefault();
            
            const submenu = this.nextElementSibling;
            if (submenu) {
                submenu.classList.toggle('active');

                menuToggles.forEach(otherToggle => {
                    if (otherToggle !== this) {
                        const otherSubmenu = otherToggle.nextElementSibling;
                        if (otherSubmenu && otherSubmenu.classList.contains('submenu')) {
                            otherSubmenu.classList.remove('active');
                        }
                    }
                });
            }
        });
    });

});