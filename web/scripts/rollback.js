//回收站笔记列表
function rollBack(){
	//$("#rollback_button").addClass(".checked");
	$("#noput_note_title").html("");
	$("#look_note_body").html("");
	$("#book_ul a").removeClass("checked");
	var $li = $("#back_note_ul li a.checked").parent();
	changeNoteListDiv(4);
	//$("#pc_part_4").show();
	$("#pc_part_5").show();//切换预览笔记
	$("#pc_part_3").hide();//隐藏编辑笔记
	$.ajax({
		url:url_path+"/backnote/loadbacknotes.do",
		type:"post",
		data:{"userId":userId},
		dataType:"json",
		success:function(result){
			if(result.status == 0){
				//清空笔记列表
				$("#note_ul").empty();
				$("#back_note_ul").empty();
				var notes = result.data;
				//循环笔记信息，生成列表
				for(var i=0;i<notes.length;i++){
					var noteTitle = notes[i].cn_note_title;
					var noteId = notes[i].cn_note_id;
					var sli = '<li class="disable">';
						sli+= '	<a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
						sli+= noteTitle;
						sli+= '		  <button type="button" class="btn btn-default btn-xs btn_position btn_delete">';
						sli+= '		    <i class="fa fa-times"></i>';
						sli+= '		  </button>';
						sli+= '		  <button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay">';
						sli+= '		    <i class="fa fa-reply"></i>';
						sli+= '		  </button></a>';
						sli+= '</li>';
					//绑定一个noteId
					var $li = $(sli);
					$li.data("notes",notes[i]);
					$li.data("noteId",noteId);
					$li.data("bookId",notes[i].cn_notebook_id);
					//添加到笔记ul中
					$("#back_note_ul").append($li);
				}
				//点击其他笔记本时清空编辑区内容
				$("#input_note_title").val("");
				um.setContent("");
			}
		},
		error:function(){
			alert("加载回收站笔记列表失败");
			}
	});
}
//预览回收站的笔记
function loadBackNote(){
	//将分享笔记li选中状态清除
	$("#back_note_ul li a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var note = $(this).data("notes");
	var noteTitle = note.cn_note_title;
	var noteBody = note.cn_note_body;
	$("#noput_note_title").html(noteTitle);
	$("#look_note_body").html(noteBody);
	$(this).data("notes",note);
}
//彻底删除笔记
function deleteBackNote(){
	//获取请求参数
	var $li = $("#back_note_ul li a.checked").parent();
	var noteId = $li.data("noteId");
	console.log(noteId);
	$.ajax({
		url:url_path+"/backnote/delbacknote.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//删除笔记列表项
				$li.remove();
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("删除笔记成功");
		}
	});
}
//还原笔记
function replayNote(){
	var $li = $("#back_note_ul li a.checked").parent();
	var noteId = $li.data("noteId");
	var bookId = $("#replaySelect").val();
	$.ajax({
		url:url_path+"/backnote/replay.do",
		type:"post",
		data:{"noteId":noteId,"newBookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				$li.remove();
				alert(result.msg);
			}else{
				alert(result.msg);
			}
		},
		error:function(){
			alert("还原笔记失败");
		}
	});
}
