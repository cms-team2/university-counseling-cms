document.addEventListener('DOMContentLoaded', function () {
    const boardSelect = document.getElementById('boardSelect');
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    
    // 게시판 선택에 따른 페이지 변경
    boardSelect.addEventListener('change', function () {
		const boardNumber = this.value;
       	location.href = '/admin/getPost?boardNumber=' + boardNumber;
    });

    // 검색 기능 (단순히 알림으로 검색 결과 표시)
    searchButton.addEventListener('click', function () {
        const searchValue = searchInput.value;
        alert('게시글 번호 ' + searchValue + '을 검색합니다.');
        // 실제 검색 로직 추가 필요
    });

    // 페이지네이션 버튼 기능 (예시로 알림 표시)
    const pageButtons = document.querySelectorAll('.page-button');
    pageButtons.forEach(button => {
        button.addEventListener('click', function () {
            const page = button.textContent;
            const queryString = window.location.search;
            const urlParams = new URLSearchParams(queryString);
            const boardNumber = urlParams.get('boardNumber') || 1;
        	location.href = '/admin/getPost?boardNumber=' + boardNumber + '&page=' + page;
        });
    });
	
	//페이징 번호 CSS
	const currentPage = new URLSearchParams(window.location.search).get('page') || 1;
    pageButtons.forEach(button => {
        if (button.textContent == currentPage) {
            button.classList.add('active');
        }
    }); 
	
    // 모달 팝업 기능
    const modal = document.getElementById("modal");
    const openModalButton = document.getElementById("openModal");
    const closeModalButton = document.getElementById("closeModal");
        
 	const editModal = document.getElementById("editModal");
    const editButtons = document.querySelectorAll(".edit-button");
    const closeEditModalButton = document.getElementById("closeEditModal");
    
    let editorInitialized = false;

    // 게시글작성 모달 열기
    openModalButton.addEventListener('click', function(event) {
        event.preventDefault(); // 링크 기본 동작 방지
        modal.style.display = "block";
    });

    // 게시글작성 모달 닫기
    closeModalButton.addEventListener('click', function() {
        modal.style.display = "none";
    });
    
    // 수정 모달 열기
    editButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault(); // 버튼 기본 동작 방지
            editModal.style.display = "block";
			
			fetch("/admin/getOnePost?postNumber="+this.value)
			.then(response=>{
				console.log(response)
				if(response.ok){
					return response.json();					
				}else return false;
			})
			.then(data=>{
				console.log(data);
				document.querySelector("#editTitle").value = data.postTitle;
				document.querySelector("#editFixedUsable").checked = (data.fixedUsable === 'Y');
    			document.querySelector("#editPostUsable").checked = (data.postUsable === 'Y');
    			
    			const editCategory = document.querySelector("#editCategory");
			    Array.from(editCategory.options).forEach(option => {
			    option.selected = option.value === data.boardNumber.toString();
			 
	            if (!editorInitialized) {
	                const editor = new toastui.Editor({
	                    el: document.querySelector('#editEditor'),
	                    previewStyle: 'vertical',
	                    height: '500px',
	                    initialValue: data.postContent 
	            });
	            editorInitialized = true;
            }
    });
				
			})
			.catch(error =>{
				console.log(error);
			})
			
            
        });
    });
    
    // 수정 모달 닫기
    closeEditModalButton.addEventListener('click', function() {
        editModal.style.display = "none";
    });
    
    

    // 모달 바깥 영역 클릭 시 닫기
    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });    
});





