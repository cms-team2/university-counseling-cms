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