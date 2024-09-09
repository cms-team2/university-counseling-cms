document.addEventListener('DOMContentLoaded', () => {
const editor = new toastui.Editor({
    el: document.querySelector('#editor'),
    previewStyle: 'vertical',
    height: '500px',
    initialValue: '에디터 테스트',
});

const createPost = function(){
	const csrfToken = document.querySelector('meta[name="_csrf"]').content;
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
	const formElement = document.querySelector("#postForm");
	const postData = new FormData(formElement);
	const postContent = editor.getMarkdown();
	const userName = "하현수";
	
	postData.append("userName", userName); // 임시
	postData.append("postContent", postContent);
	
	fetch('/admin/createPost',{
		method : "post",
		headers: {
        	 [csrfHeader]: csrfToken
    	},
		body : postData,			
	})
	.then(response => {
		if(response.ok){
			alert("게시글 작성이 완료되었습니다.");
			location.href = "/admin/getPost";
		}else if(response.status == 701 || response.status == 702){
			alert("서버 오류로 인해 게시글 저장에 실패하였습니다.");
			return false;
		}
	})
	.catch(error=>{
		console.log(error);
	})
	
}

document.querySelector("#btnSubmit").addEventListener("click", createPost);
});