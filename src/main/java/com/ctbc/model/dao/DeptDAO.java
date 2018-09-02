package com.ctbc.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ctbc.model.vo.DeptVO;

@Repository
public class DeptDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional(readOnly = true)
	public List<DeptVO> getAll(){
		Session currentSession = sessionFactory.getCurrentSession();
		List<DeptVO> deptList = currentSession.createQuery("from DeptVO", DeptVO.class).list();
		return deptList;
	}
	
	public void update(DeptVO deptVO){
		Session currentSession = sessionFactory.getCurrentSession();
//		currentSession.saveOrUpdate(deptVO);
		
		DeptVO persistDeptVO = currentSession.get(DeptVO.class, deptVO.getDeptId());
		persistDeptVO.setDeptName(deptVO.getDeptName());
		persistDeptVO.setDeptLoc(deptVO.getDeptLoc());
	}
	
}




