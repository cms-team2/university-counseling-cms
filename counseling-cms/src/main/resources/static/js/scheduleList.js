let apply_number;
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
			
			const sendData = new URLSearchParams();
			const student_id = cells[2].textContent;
			apply_number = cells[3].textContent;
			sendData.append('student_id',student_id);
			sendData.append('apply_number',apply_number);
			
			try {
                let response = await fetch('/admin/schedule-list-modal',{
					method:'POST',
					headers: {'Content-Type': 'application/x-www-form-urlencoded'},
					body: sendData.toString()
				})
				
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
				//var responseText = response;
                 const responseData = await response.json();

            let details = `
			    <h3>상담 배정 내역</h3>
			   	<label>상담 신청번호</label>
			    <input type="text" value="${cells[3].textContent}" readonly>
			    
			    <label>최초 상담일</label>
			    <input type="text" value="${responseData.oldestDate}" readonly>
			    
			    <label>최근 상담일</label>
			    <input type="text" value="${responseData.latestDate}" readonly>
			    
			    <label>상담 예정일</label>
			    <input type="text" value="${cells[7].textContent}" readonly>
			    
			    <label>상담방식</label>
			    <input type="text" value="${responseData.scheduleModal.counselingTypeName}" readonly>
			    
			    <label>담당 상담사</label>
			    <input type="text" value="${responseData.scheduleModal.employeeName}" readonly>
			    
			    <label>담당 부서</label>
			    <input type="text" value="${responseData.scheduleModal.departmentName}" readonly>
			    
			    <label>배정 가능 상담사</label>
				<select id='counselors'>
				<option value="">${responseData.scheduleModal.employeeName}</option>`;  
				var number=0;    
				while (number < responseData.counselors.length) {
				details += "<option value='"+responseData.counselors[number].employeeNumber+"'>"+responseData.counselors[number].employeeName+"</option>";
				number++;
				}
				details+="</select><button onclick='allotment()'>상담사 배정</button>";
				
			closeSidebar.style.display="flex";
            modalContent.innerHTML = details;
            sidebarModal.classList.add("open");
	} catch (error) {
	    console.error('There has been a problem with your fetch operation:', error);
	}
        });
    });
    closeSidebar.addEventListener("click", function () {
		closeSidebar.style.display="none";
        sidebarModal.classList.remove("open");
    });
});


const scheduleButton = document.querySelector("#scheduleCalender");

scheduleButton.addEventListener("click", function() {
     window.location.href = "./counselor-schedule";
});

function allotment(){
	const employee_number = document.getElementById("counselors").value;
	if(employee_number==""){
		alert("상담사를 변경하세요");
	}else if(employee_number!=""){
		 const formData = new URLSearchParams();
        formData.append('employee_number', employee_number);
        formData.append('apply_number', apply_number);
		fetch('/admin/schedules_allotment',{
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