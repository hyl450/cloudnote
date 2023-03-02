function likeNotes(){
	$("#noput_note_title").html("");
	$("#look_note_body").html("");
	$("#book_ul a").removeClass("checked");
	var $li = $("#back_note_ul li a.checked").parent();
	changeNoteListDiv(7);
	$("#pc_part_5").show();//切换预览笔记
	$("#pc_part_3").hide();//隐藏编辑笔记
}