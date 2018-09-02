package test.dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ctbc.model.dao.EmpDAO;
import com.ctbc.model.vo.DeptVO;
import com.ctbc.model.vo.EmpVO;
import com.google.gson.Gson;

import _00_Config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@Transactional
@ActiveProfiles(profiles = { "sqlite" })
//@ActiveProfiles(profiles = { "mssql_itoa" })
public class TestEmpDAO {

	@Autowired
	public EmpDAO empDAO;

	@Test
//	@Ignore
	@Rollback(true)
	public void test001() throws SQLException {
		System.out.println("============ test001() =============");
		
		List<EmpVO> empList = empDAO.getAll();
		for (EmpVO empVO : empList) {
			System.out.println(empVO);
			
			DeptVO deptVO = empVO.getDeptVOGG();
			
			deptVO.setEmps(null); // ※※※避免Gson.toJson發生StackOverFlowError
			
			System.out.println(">>> deptVO >>> " + deptVO);
		}
		
		try {
			Gson gson = new Gson();
			String empListJson = gson.toJson(empList);
			System.out.println(" empListJson >>> " + empListJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}











