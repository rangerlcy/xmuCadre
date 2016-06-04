package com.cadre.service.appointment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cadre.dao.BaseDao;
import com.cadre.pojo.Position;
import com.cadre.pojo.PositionPostuser;
import com.cadre.pojo.PostUser;
import com.cadre.pojo.User;

@Service
public class PositionPostUserService {
	@Autowired
	BaseDao<PositionPostuser> PositionPostuserDao;
	@Autowired
	BaseDao<PostUser> PostUserDao;
	
	public PositionPostuser isExistPostUserId(int postUserId){
		String hql = " from PositionPostuser where postUserId = ?";
		List<PositionPostuser> positionPostusers = PositionPostuserDao.queryList(hql, new Object[]{postUserId});
		if (null == positionPostusers) 
			return null;
		else 
			return positionPostusers.get(0);
	}
	
	/**
	 * 根据userId查询在岗情况
	 * @param id
	 * @return
	 */
	public List<PositionPostuser> findByUserId(Integer id){
		
		if (null == id) return null;
		String hql = "from PositionPostuser where user.id = ?";
		List<PositionPostuser> positionPostusers = PositionPostuserDao.queryList(hql, new Object[]{id});
		if (null == positionPostusers || 0 == positionPostusers.size()) {
			return null;
		}
		return positionPostusers;
	}

	public void save(PositionPostuser positionPostuser) {
		// TODO Auto-generated method stub
		if (positionPostuser == null) return;
		PositionPostuserDao.save(positionPostuser);
	}
	/**
	 * 是否有在职人员在此岗
	 * @param position
	 * @return
	 */
	public boolean existUserByPosition(Position position){
		String hqlString = "select count(*) from PositionPostuser where position = ?";
		int count = PositionPostuserDao.findforCount(hqlString, new Object[]{position});
		if (count == 0) return false;
		return true;
	}

	public PositionPostuser findByUserAndPosition(User user, Position position) {
		// TODO Auto-generated method stub
		String hqlString = "from PositionPostuser where user = ? and position = ? and (delFlag is null or delFlag = 1)";
		List<PositionPostuser> positionPostusers = PositionPostuserDao.queryList(hqlString, new Object[]{user,position});
		if (null == positionPostusers || 0 == positionPostusers.size())
			return null;
		return positionPostusers.get(0);
	}

	public void update(PositionPostuser positionPostuser) {
		// TODO Auto-generated method stub
		if (null == positionPostuser) return;
		PositionPostuserDao.update(positionPostuser);
	}
}
