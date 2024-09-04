 
const createBanner = function(){
	fetch("/admin/banner/create",{
		method : "POST",
		headers : {"content-type":"application/json"},
		//body : this.mid
	})
	.then(response => response.json())
	.then(data => console.log(data))
	.catch(error => console.error('Error:', error));
}

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector("#banner_submit").addEventListener("click", createBanner);
});


