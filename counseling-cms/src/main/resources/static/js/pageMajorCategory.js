const menuSeq=document.querySelector("#m_menu_order");
const warningText=document.querySelector("#warning_text");
let seqCheck=false;

/* 소메뉴 카테고리 리스트 페이지로 이동 */
function smallMenuCatgory(param){
	var param = param.replaceAll('"','');
	
	location.href = "/admin/menu-list2?code="+param;
}

/* 대메뉴 수정  */
function openModal(param) {
	var param = param.replaceAll('"','');
	
	fetch("/admin/major/modify", {
	    method: "POST",
	    headers: {
	        "Content-Type": "application/json"
	    },
	    body: JSON.stringify({ menuCode : param })
	})
	.then(response => {return response.json()})
	.then(data => {
		let menuSection =""
		if(data.majorSelectOne.menuSection=="3"){
			menuSection ="메인 페이지"
		}else{
			menuSection ="상담사 페이지"
		}
		
		let menuYn =""
		if(data.majorSelectOne.menuYn=="Y"){
			menuYn = "checked"
		}
		
		let menuUrl ="";
		if(data.majorSelectOne.menuUrl != null){
			menuUrl = data.majorSelectOne.menuUrl;
		}
		
		let deleteCode = "'" + data.majorSelectOne.menuCode +"'";
		
		// 모달과 오버레이 HTML을 생성
		const overlayHTML = '<div id="overlay" class="overlay" style="display: block;"></div>';
		const modalHTML = `
		    <div id="modal" class="catecory modal" style="display : block;">
		        <span class="close">&times;</span>
		        <h3 style="text-align: center; margin-bottom: 40px;">대메뉴 수정</h3>
		        <table id="menu_modify_table">
		            <tr>
		                <th style="width: 200px;">페이지 구분</th>
		                <td id='menu_section'>
							${menuSection}
		                </td>
		            </tr>
		            <tr>
		                <th>대메뉴 코드</th>
		                <td class="search-bar">
		                    <input type="text" id="m_menu_code_modify" name="menuCode" value=${data.majorSelectOne.menuCode} readonly>
		                </td>
		            </tr>
		            <tr>
		                <th>대메뉴명</th>
		                <td><input type="text" id="m_menu_name" name="menuName" value=${data.majorSelectOne.menuName}></td>
		            </tr>
		            <tr>
		                <th>노출 순서</th>
		                <td>
		                <input type="hidden" id="origin_menu_order" name="menuSequence" value=${data.majorSelectOne.menuSequence}>
		                <input type="number" id="modify_menu_order" name="menuSequence" value=${data.majorSelectOne.menuSequence}>
		                <span class="text-danger" id="modify_warning_text" style="display : none; text-align: left;"><small>이미 사용 중인 노출 순서입니다.</small></span>
		                </td>
		            </tr>
		            <tr>
		                <th>노출 여부</th>
		                <td><input type="checkbox" name="menuYn" value="N" ${menuYn}>숨기기</td>
		            </tr>
		            <tr>
		                <th>연결 Url</th>
		                <td><input type="text" id="m_menu_order" name="menuUrl" value=${menuUrl}></td>
		            </tr>
		        </table>
		        <div class="button-container">
		            <button type="button" id="btn_menuRegistor" onclick="modifySubmitMajorCatgory(${deleteCode})" class="button">수정</button>
					<button type="button" id="btn_menuRegistor" style="background-color:#6c757d;" onclick="deleteMajorCatgory(${deleteCode})" class="button">삭제</button>
		        </div>
		    </div>
		`;

		// 오버레이와 모달 HTML을 새로운 div 요소로 래핑
		const overlayContainer = document.createElement('div');
		overlayContainer.innerHTML = overlayHTML;
		const modalContainer = document.createElement('div');
		modalContainer.innerHTML = modalHTML;

		// 'counselorTable' 요소를 찾습니다.
		const referenceElement = document.getElementById('counselorTable');

		if (referenceElement) {
		    // 'counselorTable' 요소 뒤에 오버레이와 모달을 삽입합니다.
		    referenceElement.insertAdjacentElement('afterend', overlayContainer.firstElementChild);
		    referenceElement.insertAdjacentElement('afterend', modalContainer.firstElementChild);

		    // 인자로 전달된 값을 대메뉴 코드 필드에 설정
		    const menuCodeInput = document.getElementById('m_menu_code');
		    if (menuCodeInput) {
		        menuCodeInput.value = param;
		    }
		    
		    const originMenuSeq=document.querySelector("#origin_menu_order");
		    const modifyMenuSeq=document.querySelector("#modify_menu_order");
			const modifyWarningText=document.querySelector("#modify_warning_text");
			
		    modifyMenuSeq.addEventListener("input",function(event){
				if(originMenuSeq.value==modifyMenuSeq.value){
						seqCheck=true;
				}else{
					fetch("/admin/seqCheck?seq="+event.target.value+"&page=majorMenu&code="+data.majorSelectOne.menuSection,{
						method : "GET",
					}).then(response => {
						if(response.ok){
							modifyWarningText.style.display="none";
							seqCheck=true;
						} else if(event.target.value==""){
							modifyWarningText.style.display="none";
							seqCheck=false;
						} else{
							modifyWarningText.style.display="block";
							seqCheck=false;
						}
					}).catch(error => {
						console.error('Error:', error);
					});
				}
			});
	

		    // 닫기 버튼 이벤트 리스너 추가
		    const closeButton = document.querySelector('#modal .close');
		    if (closeButton) {
		        closeButton.addEventListener('click', () => {
		            const modal = document.getElementById('modal');
		            const overlay = document.getElementById('overlay');
		            if (modal) {
		                modal.remove(); // 모달 창을 제거
		            }
		            if (overlay) {
		                overlay.remove(); // 오버레이를 제거
		            }
		        });
		    }
		}
	})
	.catch(error => {
	    console.error('Error:', error);
	});	
	
}

// 예시: 버튼 클릭 시 모달 창 열기
function onButtonClick(param) {
    openModal(param);
}

// 예시: 버튼 클릭 시 모달 창 열기
function modifyMajorCatgory(param,pageCode) {
    openModal(param);
	pageCode=pageCode;
}

/*-- 수정 submit --*/
function modifySubmitMajorCatgory(param){
	
	const originMenuSeq=document.querySelector("#origin_menu_order");
	const modifyMenuSeq=document.querySelector("#modify_menu_order");
	
	if(originMenuSeq.value==modifyMenuSeq.value){
		seqCheck=true;
	}
	
	var param = param.replaceAll('"','');
	
	// 테이블 요소 선택
	const table = document.getElementById('menu_modify_table');

	// 테이블의 각 행을 순회
	const rows = table.querySelectorAll('tr');
	const data = {};

	rows.forEach(row => {
	    const cells = row.querySelectorAll('th, td');
	    
	    if (cells.length > 1) {
	        const key = cells[0].innerText.trim(); // 첫 번째 셀의 텍스트를 키로 사용
	        const input = cells[1].querySelector('input, select'); // 입력 필드 또는 셀렉트 요소 선택
	        
	        if (input) {
	            const name = input.name; // input 또는 select 요소의 name 속성
	            if (input.type === 'checkbox') {
	                // 체크박스의 경우 checked 속성 사용
	                data[name] = input.checked;
	            } else {
	                // 다른 입력 필드의 경우 value 속성 사용
	                data[name] = input.value;
	            }
	        }
	    }
	});
	

	
	if(data.menuCode == ""){
		alert("메뉴 코드를 입력해주세요.")
	}else if(data.menuName == ""){
		alert("메뉴명을 입력해주세요.")
	}else if(data.menuSequence == ""){
		alert("메뉴순서를 입력해주세요.")
	}else if(seqCheck == false){
		alert("사용할 수 없는 노출 순서입니다.")
	}else{
		fetch("/admin/major/update", {
	  	  	method: "POST",
	  	  	headers: {
	   	     	"Content-Type": "application/json"
	  	 	 },
	   		 body: JSON.stringify(data)
		})
		.then(response => {
			if (response.ok) {
				alert("대메뉴 수정이 완료되었습니다.");
				location.href = "/admin/menu-list1";
			}else if (response.status == 607) {
				alert("이미 사용중인 메뉴 코드입니다.");
				return false;
			}else if (response.status == 606) {
				alert("서버 오류로 등록에 실패했습니다.");
				return false;
			}
		})
		.catch(error => {
	    	console.error('Error:', error);
		});	
	}
}

/*-- 리스트 --*/
function handlePageChange(selectElement) {
    const selectedValue = selectElement.value;
    const newParam = `sec=${selectedValue}`;
    
    const currentUrl = window.location.href;
    const urlParts = currentUrl.split('?');
    const baseUrl = urlParts[0];
    const existingParams = urlParts[1] ? urlParts[1].split('&').filter(param => !param.startsWith('sec=')) : [];

    const updatedParams = [...existingParams, newParam].join('&');
    const newUrl = `${baseUrl}?${updatedParams}`;
    
    window.location.href = newUrl;
}

/*-- 대메뉴 등록 - 모달 --*/
function generateRandomNumber() {
    const randomNumber = Math.floor(Math.random() * 1000);
    
    return randomNumber.toString().padStart(3, '0');
}

function makeRandomCodeReady(){	
	const selectElement = document.getElementById('menusection');
	const codeInut = document.getElementById("m_menu_code");
	
	if (selectElement) {
	    selectElement.addEventListener('change', () => {
			codeInut.value = makeRandomCode(selectElement.value);
	    });
		codeInut.value = makeRandomCode(selectElement.value);
	}
	
	menuSeq.addEventListener("input",function(event){
	fetch("/admin/seqCheck?seq="+event.target.value+"&page=majorMenu&code="+selectElement.value,{
		method : "GET",
	}).then(response => {
		if(response.ok){
			warningText.style.display="none";
			seqCheck=true;
		} else if(event.target.value==""){
			warningText.style.display="none";
			seqCheck=false;
		} else{
			warningText.style.display="block";
			seqCheck=false;
		}
	}).catch(error => {
		console.error('Error:', error);
	});
});
	
	
}

function makeRandomCode(menuSection){
	const randomResult = "M" + menuSection + generateRandomNumber();
	
	return randomResult;
}

/*-- 대메뉴 등록 submit --*/
const submitmajorCategory = function(){
	// 테이블 요소 선택
	const table = document.getElementById('menu_create_table');

	// 테이블의 각 행을 순회
	const rows = table.querySelectorAll('tr');
	const data = {};

	rows.forEach(row => {
	    const cells = row.querySelectorAll('th, td');
	    
	    if (cells.length > 1) {
	        const key = cells[0].innerText.trim(); // 첫 번째 셀의 텍스트를 키로 사용
	        const input = cells[1].querySelector('input, select'); // 입력 필드 또는 셀렉트 요소 선택
	        
	        if (input) {
	            const name = input.name; // input 또는 select 요소의 name 속성
	            if (input.type === 'checkbox') {
	                // 체크박스의 경우 checked 속성 사용
	                data[name] = input.checked;
	            } else {
	                // 다른 입력 필드의 경우 value 속성 사용
	                data[name] = input.value;
	            }
	        }
	    }
	});
	console.log(data);

	if(data.menuCode == ""){
		alert("메뉴 코드를 입력해주세요.")
	}else if(data.menuName == ""){
		alert("메뉴명을 입력해주세요.")
	}else if(data.menuSequence == ""){
		alert("메뉴순서를 입력해주세요.")
	}else if(seqCheck == false){
		alert("사용할 수 없는 노출 순서입니다.")
	}else{
		fetch("/admin/major/create", {
			method: "POST",
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(data)
		})
		.then(response => {
			console.log(response);
			if (response.ok) {
				alert("게시글 작성이 완료되었습니다.");
				location.href = "/admin/menu-list1";
			}else if (response.status == 607) {
				alert("이미 사용중인 메뉴 코드입니다.");
				return false;
			}else if (response.status == 606) {
				alert("서버 오류로 등록에 실패했습니다.");
				return false;
			}
		})
		.catch(error => {
			console.error('Error:', error)
		});
	}
	
}
document.querySelector("#btn_menuRegistor").addEventListener("click", submitmajorCategory);


/* 대메뉴 삭제 */
const deleteMajorCatgory = function(code) {
	var code = code.replaceAll('"','');
	if(confirm("정말 삭제하시겠습니까?")){
		fetch("/admin/major/delete", {
		    method: "POST",
		    headers: {
		        "Content-Type": "application/json"
		    },
		    body: JSON.stringify({ menuCode : code })
		})
		.then(response => {
			console.log(response);
			if (response.ok) {
				alert("삭제 완료되었습니다.");
				location.href = "/admin/menu-list1";
			}else if (response.status == 605) {
				alert("하위 메뉴를 먼제 삭제한 뒤 삭제해주세요.");
				return false;
			}
			else if (response.status == 604) {
				alert("서버 오류로 인해 삭제에 실패하였습니다.");
				return false;
			}
		})
		.catch(error => {
		    console.error('Error:', error);
		});	
	}
}



