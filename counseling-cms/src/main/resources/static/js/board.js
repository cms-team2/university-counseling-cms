
function boardLnb(){
	document.addEventListener('DOMContentLoaded', () => {
	    const currentPath = window.location.pathname;
	    const menuItems = document.querySelectorAll('.side_navbar > ul li a');
	    menuItems.forEach(item => {
			const href = item.getAttribute('href').split("/")[2];
	        if (currentPath.includes(href)) {
	            item.parentElement.classList.add('selected');
	        }
	    });
	});	
}

function counselorLnb(){
	document.addEventListener('DOMContentLoaded', () => {
	    const currentPath = window.location.pathname;
	    const menuItems = document.querySelectorAll('.side_navbar ul.submenu li a');
	    menuItems.forEach(item => {
			const href = item.getAttribute('href').split("/")[2];
	        if (currentPath.includes(href)) {
				const grandparentLi = item.closest('li').parentElement.closest('div');
				grandparentLi.classList.add('active');
				item.parentElement.classList.add('selected');
	        }
	    });
	});
	
	document.addEventListener('DOMContentLoaded', function() {
	    const menuToggles = document.querySelectorAll('.menu-toggle');

	    menuToggles.forEach(toggle => {
	        toggle.addEventListener('click', function(event) {
	            event.preventDefault(); // Prevent default link behavior
	            
	            const submenu = this.nextElementSibling;
				console.log(submenu)

				submenu.classList.toggle('active');

				// Optional: Close other open submenus
				menuToggles.forEach(otherToggle => {
				    if (otherToggle !== this) {
				        const otherSubmenu = otherToggle.nextElementSibling;
				        if (otherSubmenu && otherSubmenu.classList.contains('submenu')) {
				            otherSubmenu.classList.remove('active');
				        }
				    }
				});
	        });
	    });
	});
		
}


function faqList(){
	document.querySelectorAll('.expandable').forEach(row => {
	    row.addEventListener('click', function() {
	        const nextRow = this.nextElementSibling;
	        if (nextRow && nextRow.classList.contains('details-row')) {
	            // Toggle the 'show' class to animate height
	            nextRow.classList.toggle('show');
	        }
	    });
	});
}