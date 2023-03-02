//加载用户笔记本列表
function loadUserBooks(){
	$.ajax({
		url:url_path+"/notebook/loadbooks.do",
		type:"post",
		data:{"userId":userId},
		dataType:"json",
		success:function(result){
			if(result.status == 0){
				var books = result.data;
				//循环解析生成li
				for(var i=0;i<books.length;i++){
					var bookName = books[i].cn_notebook_name;
					var bookId = books[i].cn_notebook_id;
					//创建book li
					createBookLi(bookId,bookName);
				}
			}
		},
		error:function(){
			alert("加载笔记本列表失败");
			}
	});
}

//创建添加一个笔记本book LI项
function createBookLi(bookId,bookName){
	var sli = '<li class="online">';
		sli +=' <a>';
		sli +='		<i class="fa fa-book" title="online" rel="tooltip-bottom">';
		sli +='		</i> ';
		sli +=bookName;
		sli +='<button type="button" class="btn btn-default btn_position btn-xs btn_delete del_book" title="删除"><i class="fa fa-times"></i></button>';
		sli +='	</a>';
		sli +='</li>';
	//将sli字符串转成jQuery对象
	var $li = $(sli);
	$li.data("bookId",bookId);//绑定bookId值
	//添加到ul元素中
	$("#book_ul").append($li);
}

//删除笔记本
function deleteBook(){
	var $li = $("#book_ul li a.checked").parent();
	var bookId = $li.data("bookId");
	$.ajax({
		url:url_path+"/notebook/delbook.do",
		type:"post",
		data:{"bookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				$li.remove();
				//笔记列表清空
				$("#note_ul").remove();
				alert(result.msg);
			}else{
				alert(result.msg);
			}
		},
		error:function(){
			alert("删除笔记本失败");
		}
	});
	return false;
}

//修改笔记本名称
function renameBook(){
	var bookName = $("#input_notebook_rename").val().trim();
	var $a = $("#book_ul li a.checked");
	var bookId = $a.parent().data("bookId");
	if(bookName == ""){
		alert("笔记本名称为空");
		return;
	}
	$.ajax({
		url:url_path+"/notebook/rename.do",
		type:"post",
		data:{"bookName":bookName,"bookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var sli ='		<i class="fa fa-book" title="online" rel="tooltip-bottom">';
					sli +='		</i> ';
					sli +=bookName;
					sli +='<button type="button" class="btn btn-default btn_position btn-xs btn_delete del_book" title="删除"><i class="fa fa-times"></i></button>';
				//替换a元素中的内容
				$a.html(sli);	
				alert(result.msg);
			}
		},
		error:function(){
			alert("笔记本重命名失败");
		}
	});
}