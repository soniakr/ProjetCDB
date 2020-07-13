package com.excilys.formation.cbd.dto.mappers;

import static org.junit.Assert.*;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.excilys.formation.cbd.dto.ComputerDTO;
import com.excilys.formation.cbd.model.Computer;
import com.excilys.formation.cbd.model.Computer.ComputerBuilder;

public class ComputerDtoMapperTest {

	private static final Long id = 1L;
	private static final String name = "nametoTest";
	private static final LocalDate introduced = LocalDate.of(2000, 9, 20);
	private static final LocalDate discontinued = LocalDate.of(2000, 9, 20);
	private static final Long id_company = 10L;

	private Computer computer = Mockito.mock(Computer.class);
	private ComputerDTO computerDto = Mockito.mock(ComputerDTO.class);

	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testConvertToComputerDTO() {

		Mockito.when(computer.getId()).thenReturn(id);
		Mockito.when(computer.getName()).thenReturn(name);
		Mockito.when(computer.getIntroduced()).thenReturn(introduced);
		Mockito.when(computer.getDiscontinued()).thenReturn(discontinued);
		Mockito.when(computer.getIdCompany()).thenReturn(id_company);

		ComputerDTO computerDto = ComputerDtoMapper.convertToComputerDTO(computer);

		ComputerDTO computerTest = new ComputerDTO();
		computerTest.setId(computer.getId().toString());
		computerTest.setName(computer.getName());

		assertEquals(computerTest.getId(), computerDto.getId());
		assertEquals(computerTest.getName(), computerDto.getName());
		assertEquals(computerTest.getIntroduced(), computerDto.getIntroduced());
		assertEquals(computerTest.getDiscontinued(), computerDto.getDiscontinued());
		//Compare company too
	}

	@Test
	public void testToComputer() {
		Mockito.when(computerDto.getId()).thenReturn(id.toString());
		Mockito.when(computerDto.getName()).thenReturn(name);
		Mockito.when(computerDto.getIntroduced()).thenReturn(introduced.toString());
		Mockito.when(computerDto.getDiscontinued()).thenReturn(discontinued.toString());
	//	Mockito.when(computerDto.getCompany().getId()).thenReturn(id_company.toString());

		Computer computerMapper = ComputerDtoMapper.toComputer(computerDto);

	    Computer computerOk =  new ComputerBuilder(name)
				.initializeWithId(id)
				.initializeWithIntroducedDate(introduced)
				.initializeWithDiscontinuedDate(discontinued)
				.initializeWithCompanyID(id_company)
				.build(); 

		assertEquals(computerOk.getId(), computerMapper.getId());
		assertEquals(computerOk.getName(), computerMapper.getName());
		assertEquals(computerOk.getIntroduced(), computerMapper.getIntroduced());
		assertEquals(computerOk.getDiscontinued(), computerMapper.getDiscontinued());
	}

}
