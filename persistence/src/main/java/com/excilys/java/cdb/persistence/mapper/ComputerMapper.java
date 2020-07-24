package com.excilys.java.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.java.cdb.model.Company;
import com.excilys.java.cdb.model.Computer;
import com.excilys.java.cdb.model.Computer.ComputerBuilder;


public class ComputerMapper implements RowMapper<Computer> {

	private static final String ID_COMPUTER = "id";
	private static final String NAME_COMPUTER = "name";
	private static final String INTRODUCED = "introduced";
	private static final String DISCONTINUED = "discontinued";
	private static final String COMPANY_ID = "company_id";
	private static final String COMPANY_NAME = "company_name";

	private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Computer newComputer = null;
		try {
			newComputer = new ComputerBuilder(rs.getString(NAME_COMPUTER)).initializeWithId(rs.getLong(ID_COMPUTER))
					.build();
			if (rs.getDate(INTRODUCED) != null) {
				newComputer.setIntroduced(rs.getDate(INTRODUCED).toLocalDate());
			}

			if (rs.getDate(DISCONTINUED) != null) {
				newComputer.setDiscontinued(rs.getDate(DISCONTINUED).toLocalDate());
			}

			newComputer.setCompany(new Company(rs.getLong(COMPANY_ID), rs.getString(COMPANY_NAME)));
		} catch (Exception e) {
			logger.error("Erreur -> Mapping resultSet/Computer");
		}
		return newComputer;
	}

}
