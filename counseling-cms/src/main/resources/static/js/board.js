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