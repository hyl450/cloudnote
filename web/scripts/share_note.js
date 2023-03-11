//分享笔记
function shareNote(){
	var $li = $("#note_ul li a.checked").parent();
	var noteId = $li.data("noteId");
	$.ajax({
		url:url_path+"/share/add.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){			
			alert(result.msg);
		},
		error:function(){
			alert("分享笔记失败");
		}
	});
	//阻止冒泡
	return false;
}
//按Enter键搜索
//function enterPress(e){ //传入 event
//    if(e.keyCode == 13){   
//    	searchNote();  
//    }
//}
//搜索分享笔记
function searchNote(e){
	if(e.keyCode == 13){   
		//清空预览笔记区
		$("#noput_note_title").html("");
		//在打开分享笔记时，取消选中的笔记本
		$("#book_ul li a.checked").removeClass("checked");
		$("#look_note_body").html("");
		var noteTitle = $("#search_note").val().trim();
		//默认查找所有共享笔记
	//	if(noteTitle==""){
	//		alert("搜索分享笔记内容为空");
	//		return;
	//	}
		$.ajax({
			url:url_path+"/share/search.do",
			type:"post",
			data:{"noteTitle":noteTitle},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					var shareNotes = result.data;
	//				$("#pc_part_2").hide();//全部笔记
	//				$("#pc_part_3").hide();//隐藏编辑笔记
	//				$("#pc_part_4").hide();//回收站
	//				$("#pc_part_7").hide();//收藏笔记
	//				$("#pc_part_8").hide();//活动笔记
	//				$("#pc_part_6").show();//全部共享笔记			
					changeNoteListDiv(6);
					$("#pc_part_3").hide();//隐藏编辑笔记
					$("#pc_part_5").show();//切换预览笔记
	
					$("#share_ul").empty();
					//循环笔记信息，生成列表
					for(var i=0;i<shareNotes.length;i++){
						var shareTitle = shareNotes[i].cn_share_title;
						var noteId = shareNotes[i].cn_note_id;
						var sli = '<li class="disable">';
							sli+= '	<a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
							sli+= shareTitle;					
							sli+= '</a>';
							sli+= '</li>';
						//绑定一个noteId
						var $li = $(sli);
						$li.data("shareNotes",shareNotes[i]);
						//添加到分享笔记ul中
						$("#share_ul").append($li);
					}
				}else{
					alert(result.msg);
				}
			},
			error:function(){
				alert("搜索分享笔记失败");
			}
		});
	}
}
//预览分享的笔记
function previewShareNote(){
	//将回收站笔记li选中状态清除
//	$("#back_note_ul a").removeClass("checked");
	//将分享笔记li选中状态清除
	$("#share_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var note = $(this).data("shareNotes");
	var noteTitle = note.cn_share_title;
	var noteBody = note.cn_share_body;
	$("#noput_note_title").html(noteTitle);
	$("#look_note_body").html(noteBody);
}