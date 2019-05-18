package com.practica3.myapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class MapperUsuarios implements RowMapper<Usuario>{

	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Usuario usu = new Usuario();
	
		
	    usu.setNif(rs.getString("dni"));
	    usu.setUsu(rs.getString("user"));
	    usu.setClaveServicio(rs.getString("ClaveSecreta"));
	    usu.setFecha(rs.getString("fecha"));
	
	    return usu;
	}

}
