package test.concurrency;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctbc.model.dao.DeptDAO;
import com.ctbc.model.vo.DeptVO;

import _00_Config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
//@ActiveProfiles(profiles = { "sqlite" })
@ActiveProfiles(profiles = { "mssql_itoa" })
//@Transactional 
public class TestConcurrencyUpdateA {

	@Autowired
	public SessionFactory sessionFactory;

	@Autowired
	public DeptDAO deptDAO;

	@Test
	@Ignore
	@Rollback(false)
	public void test001() {
		try {
			System.out.println("============= test001 =============");
			DeptVO deptVO_A = new DeptVO();
			deptVO_A.setDeptId(1);
			deptVO_A.setDeptName("國防部B");
			deptVO_A.setDeptLoc("中正區B");

//			Session currenctSession = sessionFactory.getCurrentSession();
			Session currenctSession = sessionFactory.openSession();

			Transaction tx = currenctSession.beginTransaction();

			DeptVO persistDeptVO_A = currenctSession.get(DeptVO.class, deptVO_A.getDeptId());
			persistDeptVO_A.setDeptName(deptVO_A.getDeptName());
			persistDeptVO_A.setDeptLoc(deptVO_A.getDeptLoc());
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
//	@Rollback(true)
	public void test002() {
		try {
			System.out.println("============= test002 =============");
			DeptVO deptVO_B = new DeptVO();
			deptVO_B.setDeptId(20);
			deptVO_B.setDeptName("交通部");
			deptVO_B.setDeptLoc("永和區");

			Session currenctSession = sessionFactory.getCurrentSession();
//			Session currenctSession = sessionFactory.openSession();

			Transaction tx = currenctSession.beginTransaction();

			DeptVO persistDeptVO_B = currenctSession.get(DeptVO.class, deptVO_B.getDeptId());
			persistDeptVO_B.setDeptName(deptVO_B.getDeptName());
			persistDeptVO_B.setDeptLoc(deptVO_B.getDeptLoc());

			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

}
