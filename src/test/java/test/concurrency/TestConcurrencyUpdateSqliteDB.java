package test.concurrency;

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
@ActiveProfiles(profiles = { "sqlite" })
//@Transactional 
public class TestConcurrencyUpdateSqliteDB {

	@Autowired
	public SessionFactory sessionFactory;

	@Autowired
	public DeptDAO deptDAO;

	@Test
//	@Ignore
//	@Rollback(true)
	public void test001() {
		/**
		 * 【SQLite測不出來，StaleObjectStateException】
		 */
		try {
			System.out.println("============= test001 =============");

			Session sessionA = sessionFactory.openSession();
			Session sessionB = sessionFactory.openSession();

			Transaction txA = sessionA.beginTransaction();
			Transaction txB = sessionB.beginTransaction();

			DeptVO persistDeptVO_A = sessionA.get(DeptVO.class, 10);
			DeptVO persistDeptVO_B = sessionB.get(DeptVO.class, 10);
			
			System.out.println(" >>> 111 persistDeptVO_A >>> " + persistDeptVO_A);
			System.out.println(" >>> 111 persistDeptVO_B >>> " + persistDeptVO_B);

			persistDeptVO_A.setDeptName("國防部AAA");
			persistDeptVO_A.setDeptLoc("中正區AAA");
			System.out.println("修改的值：" + persistDeptVO_A.getDeptName()  + " , " +  persistDeptVO_A.getDeptLoc());
			
			txA.commit();

			System.out.println(" >>> 222 persistDeptVO_A >>> " + persistDeptVO_A);
			System.out.println(" >>> 222 persistDeptVO_B >>> " + persistDeptVO_B);

			persistDeptVO_B.setDeptName("內政部BB");
			persistDeptVO_B.setDeptLoc("萬華區BB");

			txB.commit();
			//------------------------------------------------------------

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
