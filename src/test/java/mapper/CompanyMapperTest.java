package mapper;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import model.Company;

public class CompanyMapperTest {

	private static final String ID_COMPANY = "id";
    private static final String NAME = "name";

    private final Long id = 1L;
    private final String name = "nametoTest";

    private ResultSet resultSet = Mockito.mock(ResultSet.class);
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
	@Test
	public void testConvert() {
		try {
	        Mockito.when(resultSet.getLong(ID_COMPANY)).thenReturn(id);
	        Mockito.when(resultSet.getString(NAME)).thenReturn(name);
	    } catch (SQLException e) {
	        fail("sql exception :" + e.getMessage());
	    }
	    Company company = CompanyMapper.convert(resultSet);
	    Company companyTest = new Company(id,name);
	
	    assertEquals(companyTest.getId(), company.getId());
	    assertEquals(companyTest.getName(), company.getName());
	}

}
