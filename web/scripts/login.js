$(function(){//body载入完毕
	$("#login").click(function(){
		//清除原有的提示信息
		$("#count_span").html("");
		$("#password_span").html("");
		//获取请求参数
		var name = $("#count").val().trim();
		var password = $("#password").val().trim();
		//检查参数格式
		var ischeck = true;
		if(name==""){
			$("#count_span").html("用户名为空");
			ischeck = false;
		}
		if(password==""){
			$("#password_span").html("密码为空");
			ischeck = false;
		}
		//检查正确发送Ajax请求
		if(ischeck){
			addCookie("userName",name,2);
			$.ajax({
				url:url_path+"/user/login.do",
				type:"post",
				data:{"name":name,"password":password},
				dataType:"json",
				success:function(res){
					alert(res.msg);
					//正常回调
					if(res.status == 0){
						//获取用户ID，写入cookie						
						var userId = res.data;
						addCookie("userId",userId,2);					
						window.location.href="edit.html";//成功
					}else if(res.status == 1){
						$("#count_span").html(res.msg);
					}else if(res.status == 2){
						$("#password_span").html(res.msg);
					}
				},
				error:function(){
					alert("登录失败");
				}
			});
		}
	});
	
});