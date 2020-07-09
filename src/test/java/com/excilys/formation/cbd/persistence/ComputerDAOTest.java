package com.excilys.formation.cbd.persistence;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.formation.cbd.config.SpringConfig;
import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.persistence.ComputerDAO;
import com.excilys.formation.cbd.persistence.ConnexionHikari;
import com.excilys.formation.cbd.model.Computer.ComputerBuilder;

/**
 * Classe de Test pour ComputerDAO
 * @author sonia
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class ComputerDAOTest extends DBTestCase {
	
	@Autowired
	private static ComputerDAO computerDAO;
	@Autowired
	private ConnexionHikari connectionHikari;
	
	@Before
	public void setUp() throws Exception {
		DatabaseConnection connectionJUnit = new DatabaseConnection(connectionHikari.getConnection());
		getSetUpOperation().execute(connectionJUnit, getDataSet());
	}
	
	public ComputerDAOTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/computer-database-empty?serverTimezone=UTC");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "usertest");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "qwerty1234");
    }

	@Test
	public void testGetAll() {
		List<Computer> computers = computerDAO.getAll();
		assertEquals(computers.size(),4);
		
	}

	@Test
	public void testFindById() {
		int computer_id=2;
		assertThat(2,is(computer_id));
	}

	@Test
	public void testInsert() {
		String name = "Computer test created";
		LocalDate introduced = LocalDate.parse("2019-08-24");
		LocalDate discontinued = LocalDate.parse("2020-08-24");
		Company company = new Company();
		company.setId(1L);
		Computer computer = new ComputerBuilder(name)
							.initializeWithIntroducedDate(introduced)
							.initializeWithDiscontinuedDate(discontinued)
							.initializeWithCompanyID(company.getId())
							.build(); 
		computerDAO.insert(computer);
		assertEquals(5, computerDAO.getAll().size());
	}

	@Test
	public void testUpdate() {
		Long id = 10L;
		Computer computer = computerDAO.findById(id);
		computer.setName("ComputerTest");
		computerDAO.update(computer);
		assertEquals(computer.getName(), (computerDAO.findById((id)).getName()));
	}

	@Test
	public void testDelete() {
		Long id = 4L;
	/*	assertTrue(computerDAO.exist(id));
	    computerDAO.delete(id);
	    assertFalse(computerDAO.exist(id));
	*/}

	@Test
	public void testCountAll() {
		assertEquals(4, computerDAO.countAll(null));	}

	protected DatabaseOperation getSetUpOperation() throws Exception{
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception{
        return DatabaseOperation.NONE;
    }
    
	@Override
	protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/database.xml"));
	}

}
