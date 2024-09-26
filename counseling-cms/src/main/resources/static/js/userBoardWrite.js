    
	function cancel(z){
		if(confirm("정말 취소하시겠습니까?")){
		window.location.href='/board/'+z+'/list';
		}
	}

	
	document.querySelector("#write").addEventListener("click", function(event) {
	    event.preventDefault(); 
	
	    const boardWriteForm = document.getElementById("boardWriteForm");
	
	
	    boardWriteForm.method = "POST"; 
	    boardWriteForm.action = `/user/${boardWriteForm.boardId.value}/write`;
	    boardWriteForm.enctype = "multipart/form-data";
		
	    const editorContent = editor.getMarkdown(); 
	    const contentInput = document.createElement("input");
	    contentInput.type = "hidden"; 
	    contentInput.name = "content"; 
	    contentInput.value = editorContent;
	    boardWriteForm.appendChild(contentInput); 
	
	    boardWriteForm.submit();
	});
	


