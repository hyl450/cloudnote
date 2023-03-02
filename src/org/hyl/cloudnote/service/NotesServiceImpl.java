package org.hyl.cloudnote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.hyl.cloudnote.dao.NoteDao;
import org.hyl.cloudnote.dao.ShareDao;
import org.hyl.cloudnote.entity.Note;
import org.hyl.cloudnote.entity.ShareNote;
import org.hyl.cloudnote.util.NoteResult;
import org.hyl.cloudnote.util.NoteUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("notesService")
@Transactional
public class NotesServiceImpl implements NotesService {
	@Resource
	private NoteDao noteDao;
	@Resource
	private ShareDao shareDao;
	@Autowired
	private JedisClient jedisClient;

	public NoteResult loadBookNotes(String bookId) {
		NoteResult result = new NoteResult();
		List<Note> notes = noteDao.findByBookId(bookId);
		result.setStatus(0);
		result.setMsg("加载笔记列表成功");
		result.setData(notes);
		return result;
	}
	public NoteResult loadNote(String noteId) {
		NoteResult result = new NoteResult();

		String cn_note_title = jedisClient.get("cn_note_title:"+noteId);
		if(cn_note_title != null && !"".equals(cn_note_title)) {
			Note note = new Note();
			note.setCn_note_id(noteId);
			note.setCn_note_title(cn_note_title);
			String cn_note_body = jedisClient.get("cn_note_body:"+noteId);
			note.setCn_note_body(cn_note_body != null && !"".equals(cn_note_body) ? cn_note_body : "");
			result.setStatus(0);
			result.setMsg("打开笔记成功");
			result.setData(note);
			return result;
		}
		Note note = noteDao.findById(noteId);
		jedisClient.set("cn_note_title:"+noteId, note.getCn_note_title());
		jedisClient.set("cn_note_body:"+noteId, note.getCn_note_body());
		result.setStatus(0);
		result.setMsg("打开笔记成功");
		result.setData(note);
		return result;
	}
	
	public NoteResult addNote(String userId, String bookId, String noteName) {
		NoteResult result = new NoteResult();
		Note note = new Note();
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		note.setCn_user_id(userId);
		note.setCn_note_status_id("1");//normal
		note.setCn_note_type_id("1");//normal
		note.setCn_note_title(noteName);
		note.setCn_note_body("");
		Long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);
		note.setCn_note_last_modify_time(time);
		noteDao.save(note);
		//返回结果
		result.setStatus(0);
		result.setMsg("创建笔记成功");
		result.setData(note.getCn_note_id());
		return result;
	}
	
	public NoteResult saveNote(String noteId,String noteName,String noteDesc) {
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_title(noteName);
		note.setCn_note_body(noteDesc);
		Long time = System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);
		int rows = noteDao.saveNote(note);
		//更新cn_share表
		ShareNote share = new ShareNote();
		share.setCn_share_title(noteName);
		share.setCn_share_body(noteDesc);
		share.setCn_note_id(noteId);
		shareDao.updateShare(share);
		//返回结果
		NoteResult result = new NoteResult();
		if(rows==0){
			result.setStatus(1);
			result.setMsg("保存笔记失败");			
		}else{
			jedisClient.set("cn_note_title:"+noteId, noteName);
			jedisClient.set("cn_note_body:"+noteId, noteDesc);
			result.setStatus(0);
			result.setMsg("保存笔记成功");
		}
		return result;
	}
	
	public NoteResult recycleNote(String noteId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("noteId", noteId);
		map.put("statusId", "2");
		
		int rows = noteDao.updateStatus(map);
		//返回结果
		NoteResult result = new NoteResult();
		if(rows==0){
			result.setStatus(1);
			result.setMsg("删除笔记失败");
		}else{
			jedisClient.del("cn_note_title:"+noteId);
			jedisClient.del("cn_note_body:"+noteId);
			result.setStatus(0);
			result.setMsg("删除笔记成功");
		}
		//删除曾经共享过的笔记，共享笔记也被删除
		ShareNote has_share = shareDao.findByNoteId(noteId);
		if(has_share != null){
			shareDao.delShare(noteId);
		}
		return result;
	}
	
	public NoteResult moveNote(String noteId, String bookId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("noteId", noteId);
		map.put("bookId", bookId);
		int rows = noteDao.updateBookId(map);
		NoteResult result = new NoteResult();
		if(rows==0){
			result.setStatus(1);
			result.setMsg("移动笔记失败");
		}else{
			result.setStatus(0);
			result.setMsg("移动笔记成功");
		}
		return result;
	}
	
	public NoteResult loadBackNotes(String userId) {
		NoteResult result = new NoteResult();
		List<Note> notes= noteDao.findByUserId(userId);
		result.setStatus(0);
		result.setMsg("打开回收站笔记成功");
		result.setData(notes);
		return result;
	}
	@Override
	public NoteResult deleteBackNote(String noteId) {
		NoteResult result = new NoteResult();
		int rows = noteDao.deleteBackNote(noteId);
		if(rows!=0){
			result.setStatus(0);
			result.setMsg("删除回收站笔记成功");
		}else{
			result.setStatus(1);
			result.setMsg("删除回收站笔记失败");
		}
		return result;
	}

	public NoteResult replayNote(String newBookId, String noteId) {
		NoteResult result = new NoteResult();
		//此处也可以拆分两步来做
		//1.通过noteId修改bookId
		//2.通过noteId修改笔记状态
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("noteId", noteId);
		map1.put("bookId", newBookId);
		noteDao.updateBookId(map1);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("noteId", noteId);
		map2.put("statusId", "1");
		noteDao.updateStatus(map2);
		result.setStatus(0);
		result.setMsg("还原笔记成功");
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("newBookId", newBookId);
//		map.put("noteId", noteId);
//		int rows = noteDao.replayNote(map);
//		if(rows!=0){
//			result.setStatus(0);
//			result.setMsg("还原笔记成功");
//		}else{
//			result.setStatus(1);
//			result.setMsg("还原笔记失败");
//		}
		return result;
	}
}
