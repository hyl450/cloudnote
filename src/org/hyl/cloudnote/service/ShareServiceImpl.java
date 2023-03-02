package org.hyl.cloudnote.service;

import java.util.List;

import javax.annotation.Resource;

import org.hyl.cloudnote.dao.NoteDao;
import org.hyl.cloudnote.dao.ShareDao;
import org.hyl.cloudnote.entity.Note;
import org.hyl.cloudnote.entity.ShareNote;
import org.hyl.cloudnote.util.NoteResult;
import org.hyl.cloudnote.util.NoteUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("shareService")
@Transactional
public class ShareServiceImpl implements ShareService {
	@Resource
	private ShareDao shareDao;
	@Resource
	private NoteDao noteDao;
	
	public NoteResult shareNote(String noteId) {
		NoteResult result = new NoteResult();
		//检查是否重复分享
		ShareNote has_share = shareDao.findByNoteId(noteId);
		if(has_share != null){
			result.setStatus(1);
			result.setMsg("该笔记已被分享过");
			return result;
		}
		//分享操作
		Note note = noteDao.findById(noteId);
		ShareNote shareNote = new ShareNote();
		String shareId = NoteUtil.createId();
		shareNote.setCn_share_id(shareId);
		shareNote.setCn_note_id(noteId);
		shareNote.setCn_share_title(note.getCn_note_title());
		shareNote.setCn_share_body(note.getCn_note_body());
		shareDao.save(shareNote);
		
		result.setStatus(0);
		result.setMsg("分享笔记成功");
		return result;
	}

	public NoteResult searchNote(String noteTitle) {
		String likeTitle = "%";//默认查找所有
		if(noteTitle != null && !"".equals(noteTitle)){
			likeTitle = "%"+noteTitle+"%";
		}
		List<ShareNote> notes = shareDao.search(likeTitle);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("查询分享的笔记成功");
		result.setData(notes);
		return result;
	}

	public NoteResult loadShareNote(String noteId) {
		NoteResult result = new NoteResult();
		ShareNote note = shareDao.findByNoteId(noteId);
		result.setStatus(0);
		result.setMsg("打开分享的笔记成功");
		result.setData(note);
		return result;
	}

}
