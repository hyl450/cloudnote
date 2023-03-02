function loadBookNotes(){
	changeNoteListDiv(2);
	$("#pc_part_3").show();//切换编辑笔记
	$("#pc_part_5").hide();//隐藏预览笔记
	
	//将当前笔记本li设置为选中状态
	$("#book_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var bookId = $(this).data("bookId");
	//发送Ajax请求
	$.ajax({
		url:url_path+"/note/loadnotes.do",
		type:"post",
		data:{"bookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){											
				//清空笔记列表
				$("#note_ul").empty();
				var notes = result.data;
				//循环笔记信息，生成列表
				for(var i=0;i<notes.length;i++){
					var noteTitle = notes[i].cn_note_title;
					var noteId = notes[i].cn_note_id;
					createNoteLi(noteId,noteTitle);
				}
				//点击其他笔记本时清空编辑区内容
				$("#input_note_title").val("");
				um.setContent("");		
			}
		},
		error:function(){
			alert("加载笔记列表失败");
			}
	});
}
function loadNote(){
	//将当前笔记li设置为选中状态
	$("#note_ul a").removeClass("checked");	
	//将回收站笔记li选中状态清除
	$("#back_note_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var noteId = $(this).data("noteId");
	//发送Ajax请求
	$.ajax({
		url:url_path+"/note/load.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var note = result.data;
				var title = note.cn_note_title;
				var noteBody = note.cn_note_body;
				$("#input_note_title").val(title);
				um.setContent(noteBody);
			}
		},
		error:function(){
			alert("笔记内容加载失败");
		}
	});
}

function createNoteLi(noteId,noteTitle){
	//拼一个笔记的li
	var sli ='<li class="online">';
		sli+='	<a>';
		sli+=' 		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
		sli+=noteTitle;
		sli+=' 		<i class="fa fa-star" style="font-size:20px;line-height:31px;"></i>';	
		sli+='		<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
		sli+='	</a>';
		sli+='	<div class="note_menu" tabindex="-1">';
		sli+='		<dl>';
		sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
		sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
		sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
		sli+='		</dl>';
		sli+='	</div>';
		sli+='</li>';
	//绑定一个noteId
	var $li = $(sli);
	$li.data("noteId",noteId);
	//添加到笔记ul中
	$("#note_ul").append($li);
}

function saveNote(){
	//当未选笔记时，提示
	var $a = $("#note_ul li a.checked");//选择带有
	if($a.length==0){
		alert("请选择要保存的笔记");
		return;
	}
	var $li = $a.parent();
	var noteId = $li.data("noteId");
	var noteName = $("#input_note_title").val().trim();
	var noteBody = um.getContent(noteBody);
	var ischeck = true;
	if(noteBody==""){
		alert("笔记为空，不能保存");
		ischeck = false;
	}
	if(ischeck){
		$.ajax({
			url:url_path+"/note/save.do",
			type:"post",
			data:{"noteId":noteId,"noteName":noteName,"noteDesc":noteBody},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//$("#book_ul li a.checked").parent().click();
					//刷新列表标题
					var sli =' 		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
						sli+=noteName;
						sli+='		<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
					//替换a元素中的内容
					$a.html(sli);
					alert(result.msg);					
				}else{
					alert(result.msg);
				}
			},
			error:function(){
				alert("保存笔记失败");
			}
		});
	}
}

//笔记菜单
function showNoteMenu(){
	$("#note_ul").on("click",".btn_slide_down",function(){//e
		//将所有菜单隐藏
		$("#note_ul .note_menu").hide();
		//显示点击的笔记的菜单div
		var $li = $(this).parents("li");
		var $menu = $li.find(".note_menu");
		$menu.slideDown(500);//显示菜单
		//设置当前li选中模式
		$("#note_ul li a").removeClass("checked");
		$li.find("a").addClass("checked");
		//$li.click();
		//防止冒泡
		//e.stopPropagation();
		return false;
	});
	//当鼠标在div菜单移动时保存显示状态
	$("#note_ul").on("mouseover",".note_menu",function(){
		$(this).show();
	});
	//当鼠标在div菜单移开时将菜单隐藏
	$("#note_ul").on("mouseout",".note_menu",function(){
		$(this).hide();
	});
	//点击body任意位置，隐藏弹出来的菜单
	$("body").click(function(){
		$("#note_ul .note_menu").hide();
	});
}
//删除笔记到回收站
function recycleNote(){
	//获取请求参数
	var $li = $("#note_ul li a.checked").parent();
	var noteId = $li.data("noteId");
	//方式二
	//$(this).parents("li").find("a.checked");
	$.ajax({
		url:url_path+"/note/recycle.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//删除笔记列表项
				$li.remove();
				//$("#input_note_title").empty();
				//um.setContent("");
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("删除笔记成功");
		}
	});
}

//移动笔记
function moveNote(){
	var $li = $("#note_ul li a.checked").parent();
	var noteId = $li.data("noteId");
	var bookId = $("#moveSelect").val();
	//发送Ajax请求
	if(bookId!='none'){
		$.ajax({
			url:url_path+"/note/move.do",
			type:"post",
			data:{"noteId":noteId,"bookId":bookId},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					$li.remove();
					alert(result.msg);			
				}
			},
			error:function(){
				alert("移动笔记失败");
			}
		});
	}else{
		alert("请选择要移动的笔记本");
	}
}

//切换笔记列表区显示
function changeNoteListDiv(i){
	$(".col-xs-3:not('#button_save')").hide();
	$("#pc_part_"+i).show();
}
