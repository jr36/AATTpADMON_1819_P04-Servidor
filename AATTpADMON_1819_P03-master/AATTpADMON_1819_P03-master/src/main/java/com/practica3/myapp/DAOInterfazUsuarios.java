package com.practica3.myapp;

import java.util.List;

public interface DAOInterfazUsuarios {

	public Usuario ComprobarUsuario(String user, String nif);
	public String obtenerClaveServicio(String nif);
	public void InsertarFecha(String nif,String fecha);
}
