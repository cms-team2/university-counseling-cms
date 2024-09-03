document.addEventListener('DOMContentLoaded', function () {
    const boardSelect = document.getElementById('boardSelect');
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    
    // 게시판 선택에 따른 페이지 변경
    boardSelect.addEventListener('change', function () {
        alert(boardSelect.value + '번 게시판이 선택되었습니다.');
        // 실제로는 선택된 게시판의 게시글을 로드하는 로직 필요
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
            alert(button.textContent + ' 페이지로 이동합니다.');
            // 실제 페이지 이동 로직 추가 필요
        });
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

            // 수정 에디터 초기화
            if (!editorInitialized) {
                const editor = new toastui.Editor({
                    el: document.querySelector('#editEditor'),
                    previewStyle: 'vertical',
                    height: '500px',
                    initialValue: '에디터 사용'
                });
                editorInitialized = true;
            }
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