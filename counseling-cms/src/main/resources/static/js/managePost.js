document.addEventListener('DOMContentLoaded', function () {
    const boardSelect = document.getElementById('boardSelect');
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    const searchPart=document.getElementById('searchPart');
    
    // 게시판 선택에 따른 페이지 변경
    boardSelect.addEventListener('change', function () {
		const boardNumber = this.value;
       	location.href = '/admin/getPost?boardNumber=' + boardNumber;
    });
	
	//검색 창 placeholder 컨트롤
	searchPart .addEventListener('change',function(){
		console.log("test");
		if(this.value=="제목"){
			searchInput.placeholder="제목";
		} else if(this.value=="숨김 여부"){
			searchInput.placeholder="숨김 여부(N 또는 Y 입력)";
		} else if(this.value=="고정 여부"){
			searchInput.placeholder="고정 여부(N 또는 Y 입력)";
		}
	});
	
    // 검색 기능 (단순히 알림으로 검색 결과 표시)
    searchButton.addEventListener('click', function () {
        const searchValue = searchInput.value;
        console.log(searchPart.value+searchValue);
        alert(searchPart.value + searchValue + '을 검색합니다.');
        // 실제 검색 로직 추가 필요
        location.href="/admin/getPost?boardNumber="+boardSelect.value+"&searchPart="+searchPart.value+"&searchValue="+searchValue;
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

				document.querySelector("#editTitle").value = data.post.postTitle;
				document.querySelector("#editFixedUsable").checked = (data.post.fixedUsable === 'Y');
    			document.querySelector("#editPostUsable").checked = (data.post.postUsable === 'Y');
    			
    			const editCategory = document.querySelector("#editCategory");
			    Array.from(editCategory.options).forEach(option => {
			    option.selected = option.value === data.post.boardNumber.toString();
			 
	            if (!editorInitialized) {
	                const editor = new toastui.Editor({
	                    el: document.querySelector('#editEditor'),
	                    previewStyle: 'vertical',
	                    height: '500px',
	                    initialValue: data.post.postContent 
	            });
	            editorInitialized = true;
				
	            document.querySelector("#editAttachment").value = data.fileName;
				document.querySelector("#btnDeleteFile").addEventListener("click", function(){
					document.querySelector("#editAttachment").value = "";
				})	            
	           
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
    
    const checkAll = document.getElementById('checkAll');
    const checkBoxes = document.querySelectorAll('.checkBox');

    // 'checkAll' 체크박스 클릭 이벤트
    checkAll.addEventListener('change', function () {
        checkBoxes.forEach(box => {
            box.checked = checkAll.checked;
        });
    });

    // 개별 'checkBox' 체크박스 클릭 이벤트
    checkBoxes.forEach(box => {
        box.addEventListener('change', function () {
            // 모든 체크박스가 체크되었는지 확인
            const allChecked = Array.from(checkBoxes).every(box => box.checked);
            checkAll.checked = allChecked;

            // 일부 체크박스만 체크된 경우 'checkAll' 체크박스 상태 설정
            const someChecked = Array.from(checkBoxes).some(box => box.checked);
            checkAll.indeterminate = someChecked && !allChecked;
        });
    });
    
    
    const deleteButtons = document.querySelectorAll(".delete-button");
		
    
});





