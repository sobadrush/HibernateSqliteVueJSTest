package com.ctbc.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ctbc.model.vo.EmpVO;

@Repository
public class EmpDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional(readOnly = true)
	public List<EmpVO> getAll(){
		Session currentSession = sessionFactory.getCurrentSession();
		List<EmpVO> empList = currentSession.createQuery("from EmpVOFuck", EmpVO.class).list();
		return empList;
	}
	
}




