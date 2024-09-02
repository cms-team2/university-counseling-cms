// 검색 기능
function searchPosts() {
    const searchBy = document.getElementById('searchBy').value;
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const rows = document.querySelectorAll('#postTableBody tr');

    rows.forEach(row => {
        const cellValue = row.cells[searchBy === 'postId' ? 0 : 1].textContent.toLowerCase();
        if (cellValue.includes(searchInput)) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    });
}