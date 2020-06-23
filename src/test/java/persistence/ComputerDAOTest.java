package persistence;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.io.FileInputStream;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import model.Computer;

public class ComputerDAOTest extends DBTestCase {
	
	public ComputerDAOTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/computer-database-empty?serverTimezone=UTC");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "usertest");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "qwerty1234");
    }		

	@Test
	public void testGetInstance() {
		 ComputerDAO computerDAO = ComputerDAO.getInstance();
	     assertNotNull(computerDAO);
	     assertEquals("Deux instances : ce n'est pas un Singleton", ComputerDAO.getInstance(), computerDAO);
	}

	@Test
	public void testConnectBD() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAll() {
		ComputerDAO computerDAO = ComputerDAO.getInstance();
		List<Computer> computers = computerDAO.getAll();
	}

	@Test
	public void testFindById() {
		int computer_id=2;
		assertThat(2,is(computer_id));
	}

	@Test
	public void testGetByPage() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountAll() {
		fail("Not yet implemented");
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/main/resources/database.xml"));
	}

}
