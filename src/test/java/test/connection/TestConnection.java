package test.connection;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.AssertionErrors;
import org.springframework.transaction.annotation.Transactional;

import _00_Config.RootConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
@Transactional
@ActiveProfiles(profiles = {"sqlite"})
//@ActiveProfiles(profiles = {"mssql_itoa"})
public class TestConnection {

	@Autowired
	public DataSource ds;

	@Test
//	@Ignore
	@Rollback(true)
	public void test001() throws SQLException {
		String dbProductName = ds.getConnection().getMetaData().getDatabaseProductName();
		System.out.println(" @ 資料庫廠商 >>> " + dbProductName);
		AssertionErrors.assertEquals("FUCK", "SQLite"/*expected*/, dbProductName);
	}

}











