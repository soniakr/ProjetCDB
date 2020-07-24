package com.excilys.java.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.excilys.java.cdb.model.Company;

public class CompanyMapper implements RowMapper<Company>{
	
	/**
	 * S'occupe de la convertion du résultat en entité
	 * @param resultSet résultat de la requête
	 * @return une entité Company correspondante
	 */

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company newCompany=null;
		try {
            newCompany = new Company(rs.getLong("id"), rs.getString("name"));
        } catch (SQLException e) {
            System.err.println("Erreur -> Mapping company");
        }
        return newCompany;
	}
}

