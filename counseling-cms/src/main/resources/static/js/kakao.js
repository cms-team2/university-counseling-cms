Kakao.init('9c32499bc43a7e0678ac17938e7d56d3');

	document.getElementById('btn').onclick = function() {
	function sendMessage() {
	    Kakao.API.request({
	        url: '/v2/api/talk/memo/default/send',
	        data: {
	            template_object: {
	                object_type: 'text',
	                text: '채팅 상담을 원하시면 @cms_choongang 검색해보세요!',
					link: 'http://172.30.1.6:7777/user/message'
	            },
	        }
	    })
	    .then(function(response) {
	        console.log('Message sent successfully:', response);
			alert("메세지 전송 성공");
	    })
	    .catch(function(error) {
	        console.error('Error sending message:', error);
			alert("메세지 전송 실패");
	    });
	}
}
