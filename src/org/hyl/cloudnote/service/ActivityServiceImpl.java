package org.hyl.cloudnote.service;

import java.util.List;

import javax.annotation.Resource;
import org.hyl.cloudnote.dao.ActivityDao;
import org.hyl.cloudnote.entity.Activity;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Service;
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
	@Resource
	private ActivityDao activityDao;
	
	public NoteResult loadActivity() {
		NoteResult result = new NoteResult();
		List<Activity> activities = activityDao.loadActivity();
		result.setStatus(0);
		result.setData(activities);
		result.setMsg("加载活动成功");
		return result;
	}

}
