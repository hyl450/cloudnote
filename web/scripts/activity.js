//显示用户名
function showUserName(userId){
	$.ajax({
		url:url_path+"/user/lookName.do",
		type:"post",
		data:{"userId":userId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var user = result.data;
				var userName = user.cn_user_name;
				$("#user_name").html(userName);
			}
		},
		error:function(){
			alert("查询用户名失败");
		}
	});
}
//显示活动列表
function showActivities(){
	$.ajax({
		url:url_path+"/activity/load.do",
		type:"post",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var activities = result.data;
				for(var i=0;i<activities.length;i++){
					var title = activities[i].cn_activity_title;
					var body = activities[i].cn_activity_body;
					var time =  activities[i].cn_activity_end_time;
					var sdiv = '<div class="col-sm-4" id="col_0">';
						sdiv+= title;				
						sdiv+= '</div>';
						sdiv+= '<div class="col-sm-4" id="col_1">';
						sdiv+= body;				
						sdiv+= '</div>';
						sdiv+= '<div class="col-sm-4" id="col_2">';
						sdiv+= time;			
						sdiv+= '</div>';
					$("#activity_intro").append(sdiv);
				}
			}
		},
		error:function(){
			alert("显示活动失败");
		}
	});
}