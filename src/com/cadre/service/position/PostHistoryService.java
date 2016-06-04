package com.cadre.service.position;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.Position;
import com.cadre.pojo.PostHistory;
import com.cadre.pojo.User;

@Service
public class PostHistoryService {

	@Autowired
	BaseDao<PostHistory> postHistorydDao;
	
	public boolean existUserByPosition(Position position){
		String hqlString = "select count(*) from PostHistory where position = ?";
		int count = postHistorydDao.findforCount(hqlString, new Object[]{position});
		if (count == 0) return false;
		return true;
	}

	public void save(PostHistory postHistory) {
		// TODO Auto-generated method stub
		postHistorydDao.save(postHistory);
	}

	public PostHistory findByPositionAndUser(Position position, User user) {
		// TODO Auto-generated method stub
		String hqlString = "from PostHistory where position = ? and postUser.user = ? order by beginWorkDay desc";
		List<PostHistory> postHistories = postHistorydDao.queryList(hqlString, new Object[]{position,user});
		if (null == postHistories || 0 == postHistories.size()) return null;
		return postHistories.get(0);
	}

	public void saveOrUpdate(PostHistory postHistory) {
		// TODO Auto-generated method stub
		if (null == postHistory) return;
		postHistorydDao.saveOrUpdate(postHistory);
	}
	
	/**
	 * 查询时任岗位
	 * @param id
	 * @param trainBeginDay
	 * @return
	 */
	public PostHistory searchWhenPost(Integer id,Date trainBeginDay){
		String hql = " from PostHistory where postUser.user.id = ? and beginWorkDay <= ? and endWorkDay >= ?";
		List<PostHistory> postHistories = postHistorydDao.queryList(hql, new Object[]{id, trainBeginDay, trainBeginDay});
		if (postHistories.size() == 0) return null;
		else return postHistories.get(0);
	}
}
