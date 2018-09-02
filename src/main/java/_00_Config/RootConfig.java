package _00_Config;

import java.io.File;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.Pragma;

import com.ctbc.utils.MyUtils;

@Configuration
@ComponentScan(basePackages = "com.ctbc.model.dao.**")
@EnableTransactionManagement
@PropertySource(value = { "classpath:maven_pom.properties" }, encoding = "utf-8")
public class RootConfig {

	@Autowired
	private Environment springEnv;

	static {
		Properties sysProps = System.getProperties();
		Set<Entry<Object, Object>> entrySet = sysProps.entrySet();
		System.out.println("============================================");
		for (Entry<Object, Object> entry : entrySet) {
			System.out.println(entry.getKey() + " ─ " + entry.getValue());
		}
		System.out.println("============================================");
	}

	private Properties hibernateProperties(String profileName) {
		return new Properties() {
			private static final long serialVersionUID = 1L;
			{

				setProperty("hibernate.show_sql", "true");
				setProperty("hibernate.format_sql", "true");

				// setProperty("hibernate.current_session_context_class", "thread"); // 留著會報 org.hibernate.HibernateException: Calling method 'createQuery' is not valid without an active transaction (Current status: NOT_ACTIVE)
				/* 【把交易交給Spring管理】 */
				setProperty("hibernate.current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext"); // 【參考】https://blog.csdn.net/leisure_life/article/details/70230607

				// setProperty("hibernate.globally_quoted_identifiers", "true");

				switch (profileName) {
					case "sqlite":
						setProperty("hibernate.hbm2ddl.auto", "none"); // validate | update | create | create-drop | none 
						setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect"); // SQL 方言
						setProperty("hibernate.hbm2ddl.import_files", "init_db_data/init_Sqlite_Version.sql");// DB 初始資料
						break;
					case "mssql_itoa":
						setProperty("hibernate.hbm2ddl.auto", "none"); // validate | update | create | create-drop | none
						setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");// SQL 方言
						setProperty("hibernate.hbm2ddl.import_files", "init_db_data/init_MSSQL_Version.sql");// DB 初始資料
						break;
				}

			}
		};
	}

	@Bean
	@Profile("sqlite")
	public DataSource driverManagerDs() {
		System.err.println("====================【sqlite】===================");
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// 若無此設置，SQLite查日期會報錯
		// 參考：https://github.com/xerial/sqlite-jdbc/issues/88
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		SQLiteConfig sqLiteConfig = new SQLiteConfig();
		Properties props = sqLiteConfig.toProperties();
		props.setProperty(Pragma.DATE_STRING_FORMAT.pragmaName, "yyyy-MM-dd");

		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// java.endorsed.dirs ─ E:\CTBC_workspace_Oxygen_3a\apache-tomcat-9.0.7\endorsed
		String workspacePath = MyUtils.subStringBeforeLastNum(System.getProperty("java.endorsed.dirs"), "\\", 2);
		String sqlitephysicalLoc =  workspacePath + "/" + springEnv.getProperty("pom.project.artifactId") + "/" + "testDB.db"; 
		System.out.println("Sqlite DB實體位置：" + sqlitephysicalLoc );
		
		if (new File(sqlitephysicalLoc).exists() == false /*當用main或junit啟動時必須如下指定路徑*/) {
			sqlitephysicalLoc = "E:/CTBC_workspace_Oxygen_3a/HibernateOptimisticLockTest/testDB.db";
		}
		
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:sqlite:" + sqlitephysicalLoc);
		ds.setConnectionProperties(props);
		ds.setDriverClassName("org.sqlite.JDBC");
		return ds;
	}

	@Bean
	@Profile("mssql_itoa")
	public DataSource driverManagerDatasource3() {
		System.err.println("====================【mssql_itoa】===================");
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl("jdbc:sqlserver://172.24.17.52:1803;databaseName=ITOA_MAIN");
		ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		ds.setUsername("ITOA_MAIN_mod");
		ds.setPassword("f3ru9cj4");
		return ds;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource ds) {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(ds);
		sessionFactory.setPackagesToScan(new String[] { "com.ctbc.model.vo" });
		sessionFactory.setHibernateProperties(this.hibernateProperties(springEnv.getActiveProfiles()[0] /* 當前啟動的ProfileName */ ));
		return sessionFactory;
	}

	@Bean
	public PlatformTransactionManager txManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}

	public static void main(String[] args) throws SQLException {

		//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		System.setProperty("spring.profiles.active", "sqlite");
//		System.setProperty("spring.profiles.active", "mssql_itoa");
		//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

		ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(RootConfig.class);
		DataSource ds = ctx.getBean(DataSource.class);
		System.out.println(ds);
		System.out.println(" @ 資料庫廠商 >>> " + ds.getConnection().getMetaData().getDatabaseProductName());
		ctx.close();
	}
}
