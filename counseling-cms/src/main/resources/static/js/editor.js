
document.addEventListener('DOMContentLoaded', function () {
    let editor;

    const modal = document.getElementById("modal");
    const openModalButton = document.getElementById("openModal");

    // 모달 열기 시 에디터 초기화
    openModalButton.addEventListener('click', function(event) {
        event.preventDefault(); // 링크 기본 동작 방지
        modal.style.display = "block";

        // 에디터가 초기화되지 않았다면 초기화
        if (!editor) {
            editor = new toastui.Editor({
                el: document.querySelector('#editor'),
                previewStyle: 'vertical',
                height: '500px',
                initialValue: '에디터 테스트',
            });
        }
    });
});

const editor = new toastui.Editor({
	el: document.querySelector('#editor'),
	previewStyle: 'vertical',
	height: '500px',
	initialValue: '에디터 테스트'
});

>>>>>>> 9160e783f5b3df624d76df3ad5abdd30dd70dd53
