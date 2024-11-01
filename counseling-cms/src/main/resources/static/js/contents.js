const noticeBtn=document.querySelector("#notice_btn");
const faqBtn=document.querySelector("#faq_btn");
const noticeView=document.querySelector("#notice_view");
const faqView=document.querySelector("#faq_view");
const moreNotice=document.querySelector("#more_notice");
const morefaq=document.querySelector("#more_faq");

function goNoticeView(noticeNo){
	location.href='/board/notice/view?pstNo='+noticeNo;
}

function toggleDetails(row) {
    const detailsRow = row.nextElementSibling; // 다음 형제 요소(세부정보 행)
    if (detailsRow.style.display === "none" || detailsRow.style.display === "") {
        detailsRow.style.display = "table-row"; // 세부정보 표시
    } else {
        detailsRow.style.display = "none"; // 세부정보 숨기기
    }
}

noticeBtn.addEventListener("click", function(){
	noticeView.style.display="block";
	faqView.style.display="none";
	moreNotice.style.display="block";
	moreNotice.style.textAlign="center";
	morefaq.style.display="none";
	noticeBtn.className="btn btn-lg btn-notice";
	faqBtn.className="btn btn-lg btn-outline-notice";
	
});

faqBtn.addEventListener("click", function(){
	noticeView.style.display="none";
	faqView.style.display="block";
	moreNotice.style.display="none";
	morefaq.style.display="block";
	morefaq.style.textAlign="center";
	noticeBtn.className="btn btn-lg btn-outline-notice";
	faqBtn.className="btn btn-lg btn-notice";
});