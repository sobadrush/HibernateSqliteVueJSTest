package test.concurrency;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ctbc.model.dao.DeptDAO;
import com.ctbc.model.vo.DeptVO;

import _00_Config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@ActiveProfiles(profiles = { "mssql_itoa" })
//@Transactional 
public class TestConcurrencyUpdateMSSQL_ITOA {

	@Autowired
	public SessionFactory sessionFactory;

	@Autowired
	public DeptDAO deptDAO;

	@Test
//	@Ignore
//	@Rollback(true)
	public void test001() {
		try {
			System.out.println("============= test001 =============");

			DeptVO deptVO_A = new DeptVO();
			deptVO_A.setDeptId(1);

			DeptVO deptVO_B = new DeptVO();
			deptVO_B.setDeptId(1);

			Session sessionA = sessionFactory.openSession();
			Session sessionB = sessionFactory.openSession();

			Transaction txA = sessionA.beginTransaction();
			Transaction txB = sessionB.beginTransaction();

			DeptVO persistDeptVO_A = sessionA.get(DeptVO.class, 1);
			int rand = (int) (Math.random() * 10);

			DeptVO persistDeptVO_B = sessionB.get(DeptVO.class, 1);

			System.out.println(" >>> 111 persistDeptVO_A >>> " + persistDeptVO_A);
			System.out.println(" >>> 111 persistDeptVO_B >>> " + persistDeptVO_B);

			persistDeptVO_A.setDeptName("國防部_" + rand);
			persistDeptVO_A.setDeptLoc("中正區");
			
			System.out.println("修改的值：" + persistDeptVO_A.getDeptName()  + " , " +  persistDeptVO_A.getDeptLoc());
			
			txA.commit();

			System.out.println(" >>> 222 persistDeptVO_A >>> " + persistDeptVO_A);
			System.out.println(" >>> 222 persistDeptVO_B >>> " + persistDeptVO_B);

			persistDeptVO_B.setDeptName("內政部");
			persistDeptVO_B.setDeptLoc("萬華區");

			txB.commit();
			//------------------------------------------------------------
			
		} 
		catch (org.hibernate.StaleObjectStateException e) { // Catch 不到
			System.out.println("$$ exception: 1 ");
			System.out.println(e.getMessage());
		} 
//		catch (javax.persistence.OptimisticLockException e) { // Catch 得到
//			System.out.println("$$ exception: 2 ");
//			System.out.println(e.getMessage());
//		} 
		catch (Exception e) { // Catch 得到
			System.out.println("$$ exception: 3 ");
			// 捕獲 【org.hibernate.StaleObjectStateException】 
			 e.printStackTrace();
			// System.out.println(ExceptionUtils.getMessage(e));
			// System.out.println(ExceptionUtils.getStackTrace(e));
			System.out.println(ExceptionUtils.getRootCause(e)); // 根本原因
		}
	}
}
