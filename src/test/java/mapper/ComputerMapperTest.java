package mapper;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.cbd.mapper.ComputerMapper;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Computer.ComputerBuilder;

public class ComputerMapperTest {

	private static final String ID_COMPUTER = "id";
    private static final String NAME = "name";
    private static final String INTRODUCED = "introduced";
    private static final String DISCONTINUED = "discontinued";
    private static final String ID_COMPANY = "company_id";


    private static final Long id = 1L;
    private static final String name = "nametoTest";
    private static final Date introduced = Date.valueOf("2000-01-01");
    private static final Date discontinued = Date.valueOf("2002-01-01");
    private static final Long id_company = 10L;


    private ResultSet resultSet = Mockito.mock(ResultSet.class);
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
	@Test
	public void testConvert() {
		try {
	        Mockito.when(resultSet.getLong(ID_COMPUTER)).thenReturn(id);
	        Mockito.when(resultSet.getString(NAME)).thenReturn(name);
	        Mockito.when(resultSet.getDate(INTRODUCED)).thenReturn(introduced);
	        Mockito.when(resultSet.getDate(DISCONTINUED)).thenReturn(discontinued);
	        Mockito.when(resultSet.getLong(ID_COMPANY)).thenReturn(id_company);
	    } catch (SQLException e) {
	        fail("SQL exception :" + e.getMessage());
	    }
	    Computer computer = ComputerMapper.convert(resultSet);
	    Computer computerTest =  new ComputerBuilder(name)
								.initializeWithId(id)
								.initializeWithIntroducedDate(introduced.toLocalDate())
								.initializeWithDiscontinuedDate(discontinued.toLocalDate())
								.initializeWithCompanyID(id_company)
								.build(); 
	    
	    assertEquals(computerTest.getId(), computer.getId());
	    assertEquals(computerTest.getName(), computer.getName());
	    assertEquals(computerTest.getIntroduced(), computer.getIntroduced());
	    assertEquals(computerTest.getDiscontinued(), computer.getDiscontinued());
	    assertEquals(computerTest.getCompany().getId(), computer.getCompany().getId());

	    //TODO assertEquals(computerTest,computer);
	}

}
