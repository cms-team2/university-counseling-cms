function createBanner() {
	const editor = new toastui.Editor({
		el: document.querySelector('#editor'),
		previewStyle: 'vertical',
		initialEditType: 'markdown',
		height: '500px',
	});

	const createBanner = function() {
		const frm = document.querySelector('#bannerCreateForm');
		const markdownValue = editor.getMarkdown();
		const fileInput = document.querySelector("#fileInput");
		const file = fileInput.files[0];
		const fmdata = new FormData(frm);
		fmdata.append('bnr_cn', markdownValue);
		
		if (fmdata.get('bnr_ttl')==""){
			alert("제목을 입력해주세요.")
		}else if(fmdata.get('bnr_seq')==""){
			alert("게시 순서를 입력해주세요.")
		}else if(fmdata.get('fileInput').name == ""){
			alert("첨부파일은 필수값입니다.")
		}else{
			fetch("/admin/banner/create", {
				method: "POST",
				body: fmdata
			})
			.then(response => {
				console.log(response);
				if (response.ok) {
					alert("게시글 작성이 완료되었습니다.");
					location.href = "/admin/banner-list";
				} else if (response.status == 601 || response.status == 602) {
					alert("서버 오류로 인해 게시글 저장에 실패하였습니다.");
					return false;
				}
			})
			.catch(error => {
				console.error('Error:', error)
			});
		}

	}

	document.querySelector("#banner_submit").addEventListener("click", createBanner);
}

function modifyBannerPage() {
	var hiddenContents = document.getElementById("editor_contents").value;

	const editor = new toastui.Editor({
		el: document.querySelector('#editor'),
		previewStyle: 'vertical',
		initialEditType: 'markdown',
		height: '500px',
		initialValue: hiddenContents,
	});
	
	
	const modifyBannerSubmit = function() {
		const modifyCheck = document.getElementById("old_filename");
		
		const frm = document.querySelector('#bannerCreateForm');
		const markdownValue = editor.getMarkdown();
		const fileInput = document.querySelector("#fileInput");
		
		let file = null;
		if(modifyCheck.value=="Y"){
			file = fileInput.files[0];
		}else{
			file = document.getElementById("old_filepath").value;
		}
		
		const fmdata = new FormData(frm);
		fmdata.append('bnr_cn', markdownValue);

		// 파일 첨부 처리
		if (fileInput.style.display == "block") {
		    fmdata.append('fileInput', file);
		}else{
			fmdata.append('fileInput', '');
		}

		// 폼 필드 값 검사
		if (fmdata.get('bnr_ttl') === "") {
		    alert("제목을 입력해주세요.");
		    return; // 폼 제출을 중지
		}
		
		if (fmdata.get('bnr_seq') === "") {
			alert("게시순서를 입력해주세요.");
		    return; // 폼 제출을 중지	
		}

		// Check if file is required and not provided
		if (fmdata.get('old_filename') === "Y" && fileInput.files.length === 0) {
		    alert("첨부파일은 필수값입니다.");
		    return; // 폼 제출을 중지
		}

		// Fetch API를 사용한 폼 데이터 제출
		fetch("/admin/banner/modify", {
		    method: "POST",
		    body: fmdata
		})
		.then(response => {
			if (response.ok) {
				alert("게시글 수정이 완료되었습니다.");
				location.href = "/admin/banner-list";
			} else if (response.status == 601 || response.status == 602) {
				alert("서버 오류로 인해 게시글 저장에 실패하였습니다.");
				return false;
			}
		})
		.catch(error => {
		    console.error('Error:', error);
		});
	}
	
	document.querySelector("#modify_submit").addEventListener("click", modifyBannerSubmit);

}

function update_newfile(val){
	document.getElementById("old_filename").value="Y"; //바뀜
	const fileInput = val.parentElement.nextElementSibling;
	val.parentElement.style.display ="none";
	fileInput.style.display = "block";
}







