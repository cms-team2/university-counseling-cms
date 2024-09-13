document.addEventListener('DOMContentLoaded', function () {
    const deleteButton = document.getElementById('deleteSelected');
    const selectAllCheckbox = document.getElementById('selectAll');
    const checkboxes = document.querySelectorAll('tbody input[type="checkbox"]');
    
    // '전체 선택' 체크박스 기능
    selectAllCheckbox.addEventListener('change', function () {
        checkboxes.forEach(checkbox => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });

    // 삭제 버튼 클릭 시 실제 기능은 제외, 퍼블리싱 단계에서 제거
    deleteButton.addEventListener('click', function () {
        alert('삭제 버튼이 클릭되었습니다. 실제 삭제 기능은 구현되지 않았습니다.');
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