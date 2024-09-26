document.addEventListener('DOMContentLoaded', () => {
    OpenView = function(row) {
        const pstNo = row.querySelector('input[type="hidden"]').value;
        window.location.href = `/board/${boardId}/view?pstNo=${pstNo}`;
    };

    document.querySelectorAll('#delete_inquiry').forEach(button => {
        button.addEventListener("click", function(event) {
            event.stopPropagation(); // 이벤트 전파 중지
            const pstNo = this.closest('tr').querySelector('input[type="hidden"]').value;
            if (confirm("정말 삭제하시겠습니까?")) {
                // 삭제 요청을 보내는 로직
                fetch(`/board/inquiry/delete?pstNo=` + pstNo, { method: 'DELETE' })
                    .then(response => {
                        if (response.ok) {
                            alert("삭제 완료되었습니다.");
                            window.location.reload(); // 페이지 새로 고침
                        } else {
                            alert("삭제에 실패하였습니다.");
                        }
                    })
                    .catch(err => {
                        alert("오류가 발생했습니다: " + err.message);
                    });
            }
        });
    });

    // 수정 버튼 클릭 이벤트
    document.querySelectorAll('#modify_inquiry').forEach(button => {
        button.addEventListener("click", function(event) {
            event.stopPropagation(); // 이벤트 전파 중지
            const pstNo = this.closest('tr').querySelector('input[type="hidden"]').value;
            window.location.href = `/board/inquiry/modify?pstNo=${pstNo}`;
        });
    });

    if (logins === "No") {
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

            // 현재 페이지와 같으면 버튼 비활성화
            if (i === currentPage) {
                button.disabled = true; 
                button.classList.add('disabled'); // 추가적인 스타일링을 위한 클래스
            } else {
                button.onclick = () => goToPage(i); // 클릭 이벤트 설정
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
