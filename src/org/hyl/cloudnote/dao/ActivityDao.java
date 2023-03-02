package org.hyl.cloudnote.dao;

import java.util.List;

import org.hyl.cloudnote.entity.Activity;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityDao {
	public List<Activity> loadActivity();
}
