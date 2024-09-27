const pageButtons=document.getElementsByName("pagination");
const searchInput=document.querySelector("#search_input");	
const searchButton=document.querySelector("#search_button");	
const categorySelect=document.querySelector("#category");	
const recordList=document.querySelector("#record_list");
const goLIst=document.querySelector("#go_list");
const prevButton=document.querySelector("#prev_button");
const nextButton=document.querySelector("#next_button");

//URL 파라미터값
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const searchValue=urlParams.get('searchValue');
const category=urlParams.get('category');
const paramPage=urlParams.get('page');

pageButtons.forEach(button => {
	button.addEventListener("click", function(){
		const page=button.textContent;

        if(searchValue != null && category != null){
			location.href="/counselor/counselingRecordList?page="+ page+"&searchValue="+searchValue+"&category="+category;
		} else if(searchValue != null && category == null){
			location.href="/counselor/counselingRecordList?page="+ page+"&searchValue="+searchValue;
		} else if(searchValue == null && category != null){
			location.href="/counselor/counselingRecordList?page="+ page+"&category="+category;
		} else{
	        location.href ="/counselor/counselingRecordList?page="+ page;			
		}
	});
});

searchButton.addEventListener("click",function(){
	const searchValue=searchInput.value;
	if(searchInput.value != ""){
		if(category != null){
			location.href="/counselor/counselingRecordList?searchValue="+searchValue+"&category="+category;
		} else{
			location.href="/counselor/counselingRecordList?searchValue="+searchValue;		
		}
	} else{
		alert("검색어를 입력해주세요.");
	}
});

categorySelect.addEventListener("change", function(){
	if(searchValue != null){
		location.href="/counselor/counselingRecordList?searchValue="+searchValue+"&category="+this.value;
	} else{
	   	location.href ="/counselor/counselingRecordList?category="+this.value;			
	}
});

recordList.addEventListener("click", event => {
	const applyNo=event.target.closest('tr').getAttribute('value');
	if(applyNo != null){
		location.href="/counselor/writeCounselingRecord?applyNo="+applyNo;	
	}
});

prevButton.addEventListener("click",function(){
		
		let prevPage;
	
		if(paramPage==null || paramPage==1){
			prevPage=1;
		} else{
			prevPage=paramPage-1;
		}
		
	    if(searchValue != null && category != null){
			location.href="/counselor/counselingRecordList?page="+ prevPage+"&searchValue="+searchValue+"&category="+category;
		} else if(searchValue != null && category == null){
			location.href="/counselor/counselingRecordList?page="+ prevPage+"&searchValue="+searchValue;
		} else if(searchValue == null && category != null){
			location.href="/counselor/counselingRecordList?page="+ prevPage+"&category="+category;
		} else{
	        location.href ="/counselor/counselingRecordList?page="+ prevPage;			
		}
});

nextButton.addEventListener("click",function(){
		
		let nextPage;
		const lsatPage=pageButtons.length;

		if(paramPage==null){
			nextPage=2;
		} 
		else if(paramPage==lsatPage){
			nextPage=lsatPage;
		} else{
			nextPage=parseInt(paramPage, 10)+1;
		}
		
	    if(searchValue != null && category != null){
			location.href="/counselor/counselingRecordList?page="+ nextPage+"&searchValue="+searchValue+"&category="+category;
		} else if(searchValue != null && category == null){
			location.href="/counselor/counselingRecordList?page="+ nextPage+"&searchValue="+searchValue;
		} else if(searchValue == null && category != null){
			location.href="/counselor/counselingRecordList?page="+ nextPage+"&category="+category;
		} else{
	        location.href ="/counselor/counselingRecordList?page="+ nextPage;			
		}

});

goLIst.addEventListener("click",function(){
	location.href="/counselor/counselingRecordList";
});

