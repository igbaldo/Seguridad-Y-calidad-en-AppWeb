$(document).ready(function(){
// 				$("#eye").click(function(){
// 					if($("#password2").attr("type") === "password")
// 						$("#password2").removeAttr("type");
// 					else $("#password2").attr("type","password");
// 				});
	$("#eyelogin").mousedown(function(){
		$("#password").removeAttr("type");
	});
	$("#eyelogin").mouseup(function(){
		$("#password").attr("type","password");
	});
	
	$("#eye").mousedown(function(){
		$("#password2").removeAttr("type");
	});
	$("#eye").mouseup(function(){
		$("#password2").attr("type","password");
	});
})