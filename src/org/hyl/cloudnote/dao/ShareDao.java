package org.hyl.cloudnote.dao;

import java.util.List;
import java.util.Map;

import org.hyl.cloudnote.entity.ShareNote;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareDao {
	public void save(ShareNote share);
	public ShareNote findByNoteId(String noteId);
	public List<ShareNote> search(String noteTitle);
	public int delShare(String noteId);
	public int updateShare(ShareNote share);
}
