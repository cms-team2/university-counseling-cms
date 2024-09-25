   document.addEventListener('DOMContentLoaded', () => {
  window.OpenView = function(row) {
    const pstNo = row.querySelector('input[type="hidden"]').value;
    window.location.href = `/board/${boardId}/view?pstNo=${pstNo}`;
};

	 if(logins=="No"){
        	alert("로그인 하셔야지만 이용하실수 있습니다.");
        	history.back();
        }

    function updatePagination() {
        const totalPages = Math.ceil(totalItems / size);
        const pageNumbersContainer = document.getElementById('pageNumbers');
        pageNumbersContainer.innerHTML = '';

        for (let i = 1; i <= totalPages; i++) {
            const button = document.createElement('button');
            button.className = 'pagination_button';
            button.innerText = i;
            button.onclick = () => goToPage(i);
            
            if (i === currentPage) {
                button.disabled = true; 
            }

            pageNumbersContainer.appendChild(button);
        }

        document.getElementById('prevButton').disabled = currentPage === 1;
        document.getElementById('nextButton').disabled = currentPage === totalPages || totalPages === 0;
    }

    function goToPage(page) {
        if (page < 1 || page > Math.ceil(totalItems / size)) return;
        window.location.href = `/board/${boardId}/list?page=${page}&size=${size}`;
        currentPage = page;
        updatePagination();
    }
	
	
    if (boardId === 'faq') {
        document.querySelectorAll('.expandable').forEach(row => {
            row.addEventListener('click', function() {
                const nextRow = this.nextElementSibling;
                if (nextRow && nextRow.classList.contains('details-row')) {
                    nextRow.classList.toggle('show');
                }
            });
        });
    }
	
    updatePagination();
     
    });