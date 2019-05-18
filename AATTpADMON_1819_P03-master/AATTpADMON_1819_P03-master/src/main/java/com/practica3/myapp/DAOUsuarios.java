package com.practica3.myapp;


import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DAOUsuarios implements DAOInterfazUsuarios{

private JdbcTemplate jdbcTemplate;
	
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
	    this.jdbcTemplate = jdbcTemplate;
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//Primer metodo, para encontrar un usuario en una BBDD
	
	public Usuario ComprobarUsuario(String Usu, String nif) {
		String sql = "select * from usuarios where user = ? and dni = ?";
		Object[] parametros = {Usu,nif};
		MapperUsuarios mapper = new MapperUsuarios();
		List<Usuario> usuarios = this.jdbcTemplate.query(sql, parametros, mapper);
		if (usuarios.isEmpty()) return null;
		else return usuarios.get(0);
	}	
	public String obtenerClaveServicio(String nif){
		String sql = "select * from usuarios where dni=?";
		Object[ ] parametros = {nif}; //Array de objetos
		MapperUsuarios mapper = new MapperUsuarios();
		List <Usuario> usuarios =  this.jdbcTemplate.query(sql, parametros, mapper);
		if (usuarios.isEmpty()) return null;
		else  return usuarios.get(0).getClaveServicio();
	}
	//@Override
	public void InsertarFecha(String nif,String fecha) {			
		String sql = "update usuarios SET fecha = ? WHERE dni = ?";
		
		Object[ ] parametros = {fecha,nif};

		this.jdbcTemplate.update(sql,parametros);
		
	}

}
