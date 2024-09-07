const inputId=document.querySelector("#input_id");
const inputPassword=document.querySelector("#input_password");
const adminLogin=document.querySelector(".form-signin");
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

adminLogin.addEventListener("submit",function(event){
	
	event.preventDefault();
	
	const userInfo = {
    	userId: inputId.value,
    	userPassword: inputPassword.value
	};
	
	fetch("/admin/loginok", {
   		method: "POST",
   		headers: {
        	"Content-Type": "application/json",
        	[csrfHeader]: csrfToken,
    	},
    	body: JSON.stringify(userInfo),
	})
	.then(response => {
    	if (!response.ok) {
        	throw new Error('Network response was not ok');
    	}
    	// 응답이 빈 경우도 처리할 수 있도록
    	return response.text().then(text => text ? JSON.parse(text) : {});
	})
	.then(data => {
    	console.log(data);
	})
	.catch(error => {
    	console.error('Fetch error:', error);
	});
});