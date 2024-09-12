document.addEventListener('DOMContentLoaded', function () {
    const boardSelect = document.getElementById('boardSelect');
    const searchButton = document.getElementById('searchButton');
    const searchInput = document.getElementById('searchInput');
    const searchPart=document.getElementById('searchPart');
 	let searchValue ="";
    
    // 게시판 선택에 따른 페이지 변경
    boardSelect.addEventListener('change', function () {
		const boardNumber = this.value;
       	location.href = '/admin/getPost?boardNumber=' + boardNumber;
    });
	
	//검색 창 placeholder 컨트롤
	searchPart .addEventListener('change',function(){
		if(this.value=="제목"){
			searchInput.placeholder="제목";
		} else if(this.value=="숨김 여부"){
			searchInput.placeholder="숨김 여부(N 또는 Y 입력)";
		} else if(this.value=="고정 여부"){
			searchInput.placeholder="고정 여부(N 또는 Y 입력)";
		}
	});
	
    // 검색 입력창에서 엔터 키 눌림 감지
	searchInput.addEventListener('keydown', function (event) {
	    if (event.key === 'Enter') {
	        event.preventDefault(); // 기본 폼 제출 동작 방지
	        var searchValue = searchInput.value;
	        console.log(searchValue);
	        if (searchValue === "") {
	            alert("검색어를 입력해주세요.");
	        } else {
	            location.href = "/admin/getPost?boardNumber=" + boardSelect.value + "&searchPart=" + searchPart.value + "&searchValue=" + searchValue;
	        }
	    }
	});


    // 페이지네이션 버튼 기능 (예시로 알림 표시)
    const pageButtons = document.querySelectorAll('.page-button');
    pageButtons.forEach(button => {
        button.addEventListener('click', function () {
            const page = button.textContent;
            const queryString = window.location.search;
            const urlParams = new URLSearchParams(queryString);
            const boardNumber = urlParams.get('boardNumber') || 1;
            const searchPart=urlParams.get('searchPart');
            const searchValue=urlParams.get('searchValue');
            if(urlParams){
				location.href="/admin/getPost?boardNumber="+boardNumber + '&page=' + page+"&searchPart="+searchPart+"&searchValue="+searchValue;
			} else{
	        	location.href = '/admin/getPost?boardNumber=' + boardNumber + '&page=' + page;			
			}
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
    const openModalButton = document.getElementById("openModal") || null;
    const closeModalButton = document.getElementById("closeModal");
        
 	const editModal = document.getElementById("editModal");
    const editButtons = document.querySelectorAll(".edit-button");
    const closeEditModalButton = document.getElementById("closeEditModal");
    
    let editorInitialized = false;

    // 게시글작성 모달 열기
    if(openModalButton != null){
		openModalButton.addEventListener('click', function(event) {
        event.preventDefault(); // 링크 기본 동작 방지
        modal.style.display = "block";
    });
	}
    

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
				const attachment = document.querySelector("#editAttachment");
				if(attachment){
					document.querySelector("#editAttachment").value = data.fileName;
				}
				document.querySelector("#editTitle").value = data.post.postTitle;
				const editFixedUsableElement = document.querySelector("#editFixedUsable");
				if (editFixedUsableElement) {
				    editFixedUsableElement.checked = (data.post.fixedUsable === 'Y');
				}
    			document.querySelector("#editPostUsable").checked = (data.post.postUsable === 'Y');
    			
    			const editCategory = document.querySelector("#editCategory");
			    Array.from(editCategory.options).forEach(option => {
				    option.selected = option.value === data.post.boardNumber.toString();
				});
				
				const inquiryContent = document.querySelector("#inquiryContent");
				if(inquiryContent){
					inquiryContent.value = data.post.postContent;
				}
				
				let editor = null;
				let editor2 = null;	
				
	            if (!editorInitialized) {
					if(document.querySelector('#editEditor')){
						editor = new toastui.Editor({
		                    el: document.querySelector('#editEditor'),
		                    previewStyle: 'vertical',
		                    height: '500px',
		                    initialValue: data.post.postContent 
		            	});
					}
	                
		            if(document.querySelector('#editEditor2')){
						editor2 = new toastui.Editor({
		                    el: document.querySelector('#editEditor2'),
		                    previewStyle: 'vertical',
		                    height: '500px',
		                    initialValue: data.replyContent
			            });
					}

		            editorInitialized = true;
		          
		            const deleteFileButton = document.querySelector("#btnDeleteFile");
					if (deleteFileButton) {
					    deleteFileButton.addEventListener("click", () => {
					        if (confirm("파일을 삭제하시겠습니까?")) {
					            document.querySelector("#editAttachment").value = "";
					        }
					    });
					}
	        	
	        		if(document.querySelector("#btnModifyPost")){
						document.querySelector("#btnModifyPost").addEventListener("click", ()=>{
						const fixUsableCheckbox = document.querySelector('#editFixedUsable');
						const postUsableCheckbox = document.querySelector('#editPostUsable');
						const postContent = editor.getMarkdown();
						const form = document.querySelector("#editForm");
						const formData = new FormData(form);
						const fixUsableValue = fixUsableCheckbox.checked ? "Y" : "N";
						const postUsableValue = postUsableCheckbox.checked ? "Y" : "N";
						formData.append("fixedUsable", fixUsableValue);
						formData.append("postUsable", postUsableValue);
						formData.append("postContent", postContent);
						formData.append("fileNumber", data.post.fileNumber);
						formData.append("postNumber",data.post.postNumber);
						
						if(document.querySelector("#editAttachment").value == ""){
							formData.append("fileDelete", "Y");
						}else formData.append("fileDelete", "N")
						
						fetch("/admin/modifyPost",{
							method : "POST",
							body : formData
						})
						.then(response => {
							if(response.ok){
								alert('수정이 완료되었습니다.');
								location.reload();
							}else{
								alert('서버 오류로 인해 수정에 실패하였습니다.')
								return false;
							}
						})	

						})
					}
					
					
					const btnReply = document.querySelector("#btnReply");
					if(btnReply){
						btnReply.addEventListener('click',()=>{
						const reply = {
							"postNumber" : data.post.postNumber,
							"replyContent" : editor2.getMarkdown()
						}
							fetch('/admin/createReply',{
								method : "POST",
								headers : {
									"content-Type" : "application/json"
								},
								body : JSON.stringify(reply)
							})
							.then(response => {
								if(response.ok){
									alert('문의 글 답변이 완료되었습니다')
									location.reload();
								}else{
									alert('서버 오류로 인해 답변에 실패하였습니다');
									return false;
								}
								
							})
							.catch(error => {
								console.log(error);
							})
						})
					}
					
					
					
					const btnModifyFaq = document.querySelector("#btnModifyFaq");
					if(btnModifyFaq){
						btnModifyFaq.addEventListener('click',()=>{
							const postUsableCheckbox = document.querySelector('#editPostUsable');
							const postUsableValue = postUsableCheckbox.checked ? "Y" : "N";
							const faq = {
								"postNumber" : data.post.postNumber,
								"postTitle" : document.querySelector("#editTitle").value,
								"postUsable" : postUsableValue,
								"postContent" : editor.getMarkdown()
							}
							
							fetch('/admin/modifyFaq',{
								method : "POST",
								headers : {
									"content-Type" : "application/json"
								},
								body : JSON.stringify(faq)
							})
							.then(response=>{
								console.log(response);
							})
							.catch(error => {
								console.log(error);
							})
						})
					}
				
		        	
	        	}
					
		        	
  			
				
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
    
    document.querySelector("#btnCheckDelete").addEventListener("click",()=>{
		
		const postNumberArray = new Array();
		let count=0;
		checkBoxes.forEach(box => {
			if(box.checked){
				count++;
				postNumberArray.push(box.value);
			}
		});
		
		if(confirm(count+"개의 게시글을 삭제하시겠습니까?")){
			fetch('/admin/deleteCheckedPost',{
				method : "POST",
				headers : {
					"Content-Type" : "application/json",
				},
				body : JSON.stringify(postNumberArray)
			})
			.then(response => {
				if(response.ok){
					alert('선택하신 게시글을 삭제하였습니다')
					location.reload();
				}else{
					alert("서버 오류로 게시글 삭제에 실패하였습니다")
					return false;
				}
			})
			.catch(error => {
				console.log(error);
			})
 		}
	})
	
	document.querySelectorAll(".btnPostDelete").forEach(button=>{
		button.addEventListener("click",function(){
			if(confirm("정말 삭제하시겠습니까?")){
	
				fetch('/admin/deletePost?postNumber='+this.value)
				.then(response => {
					if(response.ok){
						alert('게시글을 삭제하였습니다')
						location.reload();
					}else{
						alert("서버 오류로 게시글 삭제에 실패하였습니다")
						return false;
					}
				})
				.catch(error => {
					console.log(error);
				})
				
	 		}
		})
	})
	
		
    
});




