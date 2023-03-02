$(function(){
	$("#regist_button").click(function(){
		//清除原有提示信息
		$("#warning_1").hide();
		$("#warning_2").hide();
		$("#warning_3").hide();
		$("#warning_4").hide();
		
		//获取请求参数
		var name = $("#regist_username").val().trim();
		var nick = $("#nickname").val().trim();
		var password = $("#regist_password").val().trim();
		var final_password = $("#final_password").val().trim();
		//检查格式
		var ischecked = true;
		if(name==""){
			$("#warning_1 span").html("用户名为空");
			$("#warning_1").show();
			ischecked = false;
		}
		if(nick==""){
			$("#warning_4 span").html("昵称为空");
			$("#warning_4").show();
			ischecked = false;
		}
		if(password==""){
			$("#warning_2 span").html("密码为空");
			$("#warning_2").show();
			ischecked = false;
		}else{
			if(password.length<6||password.length>20){
				$("#warning_2 span").html("密码位数6-20");
				$("#warning_2").show();
				ischecked = false;
			}
		}
		if(final_password==""){
			$("#warning_3 span").html("确认密码为空");
			$("#warning_3").show();
			ischecked = false;
		}else{
			if(final_password != password){
				$("#warning_3 span").html("与密码不一致");
				$("#warning_3").show();
				ischecked = false;
			}
		}
		//发送Ajax请求
		if(ischecked){
			$.ajax({
				url:url_path+"/user/regist.do",
				type:"post",
				data:{"name":name,"password":password,"nick":nick},
				dataType:"json",
				success:function(result){
					if(result.status==0){
						alert(result.msg);
						$("#back").click();//触发单击处理
					}else if(result.status==1){//用户名被占用
						$("#warning_1 span").html(result.msg);
						$("#warning_1").show();
					}
				},
				error:function(){
					alert("注册失败");
				}
			});
		}
	});
});