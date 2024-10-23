document.addEventListener("DOMContentLoaded", function() {

    const editor = new toastui.Editor({
        el: document.querySelector('#editor'),
        height: '400px',
        initialEditType: 'markdown',
        previewStyle: 'vertical'
    });


    editor.setMarkdown(originalContent);

 
   document.querySelector("#modify").addEventListener("click", function(event) {
    event.preventDefault(); 
    const boardModifyForm = document.getElementById("boardModifyForm");

    const formData = new FormData(boardModifyForm); 
    const editorContent = editor.getMarkdown(); 
    formData.append("content", editorContent); 


    fetch(`/user/${boardModifyForm.boardId.value}/modifyok`, {
        method: "POST",
        body: formData,
    })
    .then(response => {
        if (response.ok) {
            return response.text(); 
        } else {
            throw new Error("수정에 실패했습니다.");
        }
    })
    .then(data => {
        alert(data);
      	location.href=`/board/${boardModifyForm.boardId.value}/list`;
    })
    .catch(error => {
        alert(error.message); // 에러 메시지 출력
    });
});

});
