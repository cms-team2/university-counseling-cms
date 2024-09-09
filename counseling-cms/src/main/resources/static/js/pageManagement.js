document.addEventListener('DOMContentLoaded', () => {
	const editor = new toastui.Editor({
	    el: document.querySelector('#editor'),
	    previewStyle: 'vertical',
		initialEditType: 'markdown',
	    height: '500px',
	});
	
	const createBanner = function(){
		const csrfToken = document.querySelector('meta[name="_csrf"]').content;
		const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
		
		const frm = document.querySelector('#bannerCreateForm');
		const markdownValue = editor.getMarkdown();	
		const fileInput= document.querySelector("#fileInput");
		const file = fileInput.files[0];
		const fmdata = new FormData(frm);
		fmdata.append('BNR_CN',markdownValue);
			
		fetch("/admin/banner/create",{
			method : "POST",
			headers: {
				[csrfHeader]: csrfToken
			},
			body: fmdata
		})
		.then(response => {
			console.log(response); 
			if(response.ok){
				alert("게시글 작성이 완료되었습니다.");
				location.href = "/admin/banner-list";
			}else if(response.status == 601 || response.status == 602){
				alert("서버 오류로 인해 게시글 저장에 실패하였습니다.");
				return false;
			}
		})
		.catch(error => {
			console.error('Error:', error)
		});
	}
	
	document.querySelector("#banner_submit").addEventListener("click", createBanner);

});



