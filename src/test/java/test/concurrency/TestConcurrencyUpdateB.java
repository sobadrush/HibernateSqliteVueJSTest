package test.concurrency;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ctbc.model.vo.DeptVO;

import _00_Config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@Transactional
//@ActiveProfiles(profiles = {"sqlite"})
@ActiveProfiles(profiles = {"mssql_itoa"})
public class TestConcurrencyUpdateB {

	@Autowired
	public SessionFactory sessionFactory;

	@Test
//	@Ignore
	@Rollback(false)
	public void test001() {
		
		int _deptId = 1;
		
		DeptVO deptVO_A = new DeptVO();
		deptVO_A.setDeptId(_deptId);
		
		DeptVO deptVO_B = new DeptVO();
		deptVO_B.setDeptId(_deptId);

		// 有使用1者開啟了一個session1
		Session session1 = sessionFactory.openSession();
		// 在這之後，馬上有另一個使用者2開啟了session2
		Session session2 = sessionFactory.openSession();
		
		Transaction tx1 = session1.beginTransaction();
		Transaction tx2 = session2.beginTransaction();
		
		// 使用者1查詢資料
		DeptVO deptVOAAA = session1.get(DeptVO.class, deptVO_A.getDeptId());
		// 使用者2查詢同一筆資料
		DeptVO deptVOBBB = session1.get(DeptVO.class, deptVO_B.getDeptId());
		
		// 此時userV1、userV2兩個版本號是相同的
		System.out.println("111 deptVOAAA.getVersion() >>> " + deptVOAAA.getVersion());
		System.out.println("111 deptVOBBB.getVersion() >>> " + deptVOBBB.getVersion());
		
		// 使用者1 , 進行更新
		deptVO_A.setDeptName("國防部");
		deptVO_A.setDeptLoc("中正區");
		
		// 使用者2 , 進行更新
		deptVO_B.setDeptName("交通部");
		deptVO_B.setDeptLoc("永和區");
		
		// 交易1進行commit
		tx1.commit();
		
		System.out.println("222 deptVOAAA.getVersion() >>> " + deptVOAAA.getVersion());
		System.out.println("222 deptVOBBB.getVersion() >>> " + deptVOBBB.getVersion());
		
		// 此時由於資料更新，資料庫中的版本號遞增了
		// 因版本號比資料庫中的舊
		// 交易2, 送出更新資料會失敗，丟出StableObjectStateException 例外
		tx2.commit();
		
		session1.close();
		session2.close();
	}

	public static void main(String[] args) {
		
		//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		System.setProperty("spring.profiles.active", "sqlite");
//		System.setProperty("spring.profiles.active", "mssql_itoa");
		//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(RootConfig.class);
		SessionFactory sessionFactory = ctx.getBean("sessionFactory", SessionFactory.class);
		//-------------------------------
		
		int _deptId = 1;
		
		DeptVO deptVO_A = new DeptVO();
		deptVO_A.setDeptId(_deptId);
		
		DeptVO deptVO_B = new DeptVO();
		deptVO_B.setDeptId(_deptId);

		// 有使用1者開啟了一個session1
		Session session1 = sessionFactory.openSession();
		// 在這之後，馬上有另一個使用者2開啟了session2
		Session session2 = sessionFactory.openSession();
		
		Transaction tx1 = session1.beginTransaction();
		Transaction tx2 = session2.beginTransaction();
		
		// 使用者1 , 查詢資料
		DeptVO deptVOAAA = session1.get(DeptVO.class, deptVO_A.getDeptId());
		System.out.println(" deptVOAAA >>> " + deptVOAAA);
		// 使用者2 , 查詢同一筆資料
		DeptVO deptVOBBB = session1.get(DeptVO.class, deptVO_B.getDeptId());
		
		// 此時deptVOAAA、deptVOBBB兩個版本號是相同的
		System.out.println("111 deptVOAAA.getVersion() >>> " + deptVOAAA.getVersion());
		System.out.println("111 deptVOBBB.getVersion() >>> " + deptVOBBB.getVersion());
		
		// 使用者1 , 進行更新
		deptVO_A.setDeptName("國防部");
		deptVO_A.setDeptLoc("中正區");
		
		// 使用者2 , 進行更新
		deptVO_B.setDeptName("交通部");
		deptVO_B.setDeptLoc("永和區");
		
		// 交易1進行commit
		tx1.commit();
		
		System.out.println("222 deptVOAAA.getVersion() >>> " + deptVOAAA.getVersion());
		System.out.println("222 deptVOBBB.getVersion() >>> " + deptVOBBB.getVersion());
		
		// 此時由於資料更新，資料庫中的版本號遞增了
		// 因版本號比資料庫中的舊
		// 交易2, 送出更新資料會失敗，丟出StableObjectStateException 例外
		tx2.commit();
		
		session1.close();
		session2.close();
		
		//-------------------------------
		ctx.close();
	}

}










