package test.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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
import org.springframework.transaction.annotation.Transactional;

import com.ctbc.model.dao.DeptDAO;
import com.ctbc.model.vo.DeptVO;
import com.ctbc.model.vo.EmpVO;

import _00_Config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@Transactional
@ActiveProfiles(profiles = { "sqlite" })
//@ActiveProfiles(profiles = { "mssql_itoa" })
public class TestDeptDAO {

	@Autowired
	public DeptDAO deptDAO;

	@Test
//	@Ignore
	@Rollback(true)
	public void test001() throws SQLException {
		List<DeptVO> deptList = deptDAO.getAll();
		for (DeptVO dVO : deptList) {
			System.out.println(" @@@ " + dVO);

			Set<EmpVO> empList = dVO.getEmps();
			for (EmpVO empVO : empList) {
				System.out.println(" >>> " + empVO);
			}
		}
	}

	@Test
	@Ignore
	@Rollback(true)
	public void test002() throws SQLException {
		DeptVO deptVO = new DeptVO();
		deptVO.setDeptId(10);
		deptVO.setDeptName("國防部");
		deptVO.setDeptLoc("中正區");
		deptDAO.update(deptVO);
		//----------------------
		this.printAllData();
	}

	private void printAllData() {
		System.out.println("=============== printAllData() ================");
		List<DeptVO> deptList = deptDAO.getAll();
		for (DeptVO dVO : deptList) {
			System.out.println(dVO);
		}
	}

}
