package com.excilys.formation.cbd.persistence;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.formation.cbd.config.SpringConfig;
import com.excilys.formation.cbd.model.Company;
import com.excilys.formation.cbd.model.Computer;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class CompanyDAOTest extends DBTestCase{
	
	@Autowired
	private static CompanyDAO companyDAO;
	
	public CompanyDAOTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3306/computer-database-empty?serverTimezone=UTC");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "usertest");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "qwerty1234");
    }
	
	@Test
	public void testGetAll() {
		List<Company> companies = companyDAO.getAll();
		assertEquals(companies.size(),2);
	}

	@Test
	public void testGetByPage() {
		fail("Not yet implemented");
	}

	@Test
	public void testCountAll() {
		assertEquals(2, companyDAO.countAll());	

	}
	
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
