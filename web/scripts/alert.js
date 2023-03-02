//弹出"添加笔记本"对话框
function alertAddBookWindow(){
	//加载alert_notebook.html内容
	$("#can").load("alert/alert_notebook.html");
	//背景色div显示
	$(".opacity_bg").show();
}
//关闭对话框
function closeAlertWindow(){
	$("#can").empty();//清空，隐藏
	$(".opacity_bg").hide();//背景色隐藏
}
function alertNewBookWindow(){
	var bookName = $("#input_notebook").val().trim();
	var ischeck = true;
	if(bookName==""){
		alert("笔记本名称为空");
		ischeck = false;
	}
	if(ischeck){
		$.ajax({
			url:url_path+"/notebook/add.do",
			type:"post",
			data:{"userId":userId,"bookName":bookName},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//关闭对话框
					closeAlertWindow();
					//添加笔记本列表项
					var bookId = result.data;
					//方式一：loadBookNotes();
					//创建book li
					createBookLi(bookId,bookName);
					alert(result.msg);
				}
			},
			error:function(){
				alert("创建笔记本失败");
			}
		});
	}
}
//弹出"添加笔记"对话框
function alertAddNoteWindow(){
	//检测是否选中笔记本(jQuery对象就是数组)
	var $a = $("#book_ul li a.checked");//选择带有
	if($a.length==0){
		alert("请选择笔记本");
		return;
	}
	//加载alert_notebook.html内容
	$("#can").load("alert/alert_note.html");
	//背景色div显示
	$(".opacity_bg").show();
}
function alertNewNoteWindow(){
	var noteTitle = $("#input_note").val().trim();
	var $li = $("#book_ul li a.checked").parent();
	var bookId = $li.data("bookId");
	var ischeck = true;
	if(noteTitle==""){
		alert("笔记名称为空");
		ischeck = false;
	}
	if(ischeck){
		$.ajax({
			url:url_path+"/note/add.do",
			type:"post",
			data:{"userId":userId,"bookId":bookId,"noteName":noteTitle},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//关闭对话框
					closeAlertWindow();
					//添加笔记列表项
					var noteId = result.data;
					//方式一：loadNotes();
					//创建note li
					createNoteLi(noteId,noteTitle);
					alert(result.msg);
				}
			},
			error:function(){
				alert("创建笔记失败");
			}
		});
	}
}

//弹出确认删除笔记对话框
function alertRecycleNoteWindow(){
	$("#can").load("alert/alert_delete_note.html");
	//背景色div显示
	$(".opacity_bg").show();
}

//弹出移动笔记的对话框
function alertMoveNoteWindow(){
	$("#can").load("alert/alert_move.html",function(){
		var id = "#moveSelect";
		bookOpts(id);
	});
	$(".opacity_bg").show();
	//阻止li冒泡
	return false;
}
function bookOpts(id){
	//获取笔记本信息，生成select的option选项
	var $lis = $("#book_ul li");//获取所有笔记本
	for(var i=0;i<$lis.length;i++){
		var $li = $($lis[i]);//获取li元素的jQuery
		var bookId = $li.data("bookId");
		var bookName = $li.text().trim();//获取文本内容
		//拼一个option，添加到select下拉单
		var sopt = '<option value="'+bookId+'">- '+bookName+' -</option>';
		$(id).append(sopt);
	}
}
//弹出删除笔记本对话框
function alertRecycleBookWindow(){
	$("#can").load("alert/alert_delete_notebook.html");
	$(".opacity_bg").show();
}
//恢复笔记对话框
function alertReplayWindow(){
	$("#can").load("alert/alert_replay.html",function(){
		var id = "#replaySelect";
		var bookId = $("#back_note_ul li a.checked").parent().data("bookId");
		var opt = '<option value="'+bookId+'">- 默认笔记本 -</option>';
		$("#replaySelect").append(opt);
		bookOpts(id);
//		var $lis = $("#book_ul li");//获取所有笔记本
//		for(var i=0;i<$lis.length;i++){
//			var $li = $($lis[i]);//获取li元素的jQuery
//			var bookId = $li.data("bookId");
//			var bookName = $li.text().trim();//获取文本内容
//			//拼一个option，添加到select下拉单
//			var sopt = '<option value="'+bookId+'">- '+bookName+' -</option>';
//			$("#replaySelect").append(sopt);
//		}
	});
	$(".opacity_bg").show();
}
//彻底删除笔记对话框
function alertDeleteNoteWindow(){
	$("#can").load("alert/alert_delete_rollback.html");
	$(".opacity_bg").show();
}
//笔记本重命名
function alertBookRenameWindow(){
	$("#can").load("alert/alert_rename.html");
	$(".opacity_bg").show();
}