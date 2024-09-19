const createApply = () => {
	const frmApply = document.querySelector("#frmApply");
	const formData = new FormData(frmApply);
	for (const [key, value] of formData.entries()) {
                console.log(`${key}: ${value}`);
            }
	fetch("/counseling-apply",{
		method : "POST",
		body : formData
	})
	.then(response => {
		console.log(response);
	})
	.catch(error => {
		console.log(error);
	})
	
}

document.querySelector("#btnApply").addEventListener("click", createApply);