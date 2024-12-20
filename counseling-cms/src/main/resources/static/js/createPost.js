document.addEventListener('DOMContentLoaded', () => {
const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    previewStyle: 'vertical',
    height: '500px',
    initialValue: '에디터 테스트',
});

const createPost = function(){
	console.log("test")
	const fixUsableCheckbox = document.querySelector('#fiexUsable');
	const postUsableCheckbox = document.querySelector('#postUsable');	
	const formElement = document.querySelector("#postForm");
	const postData = new FormData(formElement);
	const postContent = editor.getMarkdown();
	
	// 체크박스 상태에 따라 값 설정
	const fixUsableValue = fixUsableCheckbox.checked ? "Y" : "N";
	const postUsableValue = postUsableCheckbox.checked ? "Y" : "N";
	postData.append("fixedUsable", fixUsableValue);
	postData.append("postUsable", postUsableValue);
	postData.append("postContent", postContent);

	fetch('/admin/createPost',{
		method : "post",
		headers: {
        	 credentials: 'include'
    	},
		body : postData,			
	})
	.then(response => {
		if(response.ok){
			alert("게시글 작성이 완료되었습니다.");
			location.reload();
		}else if(response.status == 701 || response.status == 702){
			alert("서버 오류로 인해 게시글 저장에 실패하였습니다.");
			return false;
		}
	})
	.catch(error=>{
		console.log(error);
	})
	
}

const createFaq = function(){
	const postUsableCheckbox = document.querySelector('#postUsable');
	const postUsableValue = postUsableCheckbox.checked ? "Y" : "N";
	const faq = {
		"postTitle" : document.querySelector("#postTitle").value,
		"postUsable" : postUsableValue,
		"postContent" : editor.getMarkdown()
	}
	fetch('/admin/createFaq',{
		method : "POST",
		headers : {
			"content-Type" : "application/json"
		},
		body : JSON.stringify(faq)
	})
	.then(response=>{
		if(response.ok){
			alert('FAQ 등록이 완료되었습니다')
			location.reload();
		}else{
			alert('서버 오류로 FAQ 등록에 실패하였습니다');
			return false;
		}
	})
	.catch(error=>{
		console.log(error)
	})
	
}

const btnSubmitFaq = document.querySelector("#btnSubmitFaq");
if(btnSubmitFaq){
	btnSubmitFaq.addEventListener('click', createFaq);
}
document.querySelector("#btnSubmit").addEventListener("click", createPost);
});