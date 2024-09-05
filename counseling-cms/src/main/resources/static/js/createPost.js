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
	postData.append("postContent", postContent);
	
	fetch('/admin/createPost',{
		method : "post",
		headers: {
        	 [csrfHeader]: csrfToken
    	},
		body : postData,			
	})
	.then(response => {
		console.log(response);
	})
	.catch(error=>{
		console.log(error);
	})
	
}

document.querySelector("#btnSubmit").addEventListener("click", createPost);
});