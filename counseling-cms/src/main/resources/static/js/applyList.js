var STDNT_NO=[];
document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll("#counselorTable tbody tr");
    const sidebarModal = document.getElementById("sidebarModal");
    const modalContent = document.getElementById("modalContent");
    const closeSidebar = document.getElementById("closeSidebar");

    rows.forEach(row => {
        row.addEventListener("click", async function () {
            // 모든 행에서 선택 상태 제거
            rows.forEach(r => r.classList.remove("selected"));
            row.classList.add("selected");

            const cells = row.querySelectorAll("td");
            const studentId = cells[3].textContent;
            try {
                let response = await fetch('/admin/apply_list_api_data/'+studentId,{
					credentials: 'include'
				})
				
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
				//var responseText = response;
                 const responseData = await response.json();
                 STDNT_NO=[cells[3].textContent,responseData.details.dscsnRsvtYmd];
                let details = `
                    <h3>상담 신청 세부내역</h3>
                    <label>상담 신청번호</label>
                    <input type="text" value="${cells[0].textContent}" readonly>
                    <label>상담 신청일</label>
                    <input type="text" value="${cells[8].textContent}" readonly>
                    <label>상담 희망일</label>
                    <input type="text"  value="${responseData.details.dscsnRsvtYmd}" readonly>
                    <label>상담 방식</label>
                    <input type="text"  value=${responseData.details.csclsfNm} readonly>
                    <label>상담 신청 내용</label>
                    <textarea placeholder="상담 내용을 입력하세요" readonly>${responseData.details.dscsnAplyCn} </textarea>
                    <label>배정 가능 상담사</label>
                    <select id='counsler'>
                    	<option value="">상담사를 선택해주세요.</option>`;  
                var number=0;    
               	while (number < responseData.counsler.length) {
    				details += "<option value='"+responseData.counsler[number].empNo+"'>"+responseData.counsler[number].flnm+"</option>";
    				number++;
					}
                details+="</select><button onclick='allotment()'>상담사 배정</button>";

                // 모달에 내용 설정
                modalContent.innerHTML = details;
                sidebarModal.classList.add("open");
                closeSidebar.style.display = "flex";

            } catch (error) {
                console.error('There has been a problem with your fetch operation:', error);
            }
        });
    });

    closeSidebar.addEventListener("click", function () {
        closeSidebar.style.display = "none";
        sidebarModal.classList.remove("open");
    });
});

function search_stdnt() {
    // admin_apply_frm.action="./admin/apply-list";
    admin_apply_frm.submit();
}

function allotment(){
	const EMP_NO = document.getElementById("counsler").value;
	if(EMP_NO==""){
		alert("상담사를 선택해주세요");
	}else if(EMP_NO!=""){
		 const formData = new URLSearchParams();
        formData.append('EMP_NO', EMP_NO);
        formData.append('STDNT_NO', STDNT_NO[0]);
        formData.append('DSCSNRSVTYMD', STDNT_NO[1]);
		fetch('/admin/apply_allotment',{
			method:"post",
			headers : {"content-type":"application/x-www-form-urlencoded"},
			body: formData.toString()
		})
		.then(api_req=>api_req.text())
		.then(api_res=>{
			if(api_res=="ok"){
				alert("상담사를 배정하였습니다.");
				window.location.reload();
			}else if(api_res=="no"){
				alert("데이터 오류로 인하여 상담사를 배정하지 못하였습니다.");
				window.location.reload();
			}
		})
		.catch(error=>alert("시스템 오류로인해 상담사를 배정하지 못하였습니다."))
	}
}
