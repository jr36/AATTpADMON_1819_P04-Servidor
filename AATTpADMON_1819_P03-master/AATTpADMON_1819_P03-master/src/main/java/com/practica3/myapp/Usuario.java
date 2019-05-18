package com.practica3.myapp;

import java.io.Serializable;

public class Usuario implements Serializable{


    private String nif;
    private String Usu;
    private String fecha;
    private String ClavePublica;
    private String firma;
    private String ClaveServicio;
    
	public Usuario(String Usu, String nif, String fecha,String firma, String ClavePublica ) {
		
		Usu=Usu;
        nif=nif;
        fecha=fecha;
        ClavePublica=ClavePublica;
        firma=firma;
        
	}
	 public Usuario(){
	    	Usu="";
	    	
	    	nif="";
	    	fecha="";
	    	ClavePublica="";
	    	firma="";
	    	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getUsu() {
		return Usu;
	}
	public void setUsu(String usu) {
		Usu = usu;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getClavePublica() {
		return ClavePublica;
	}
	public void setClavePublica(String clavePublica) {
		ClavePublica = clavePublica;
	}
	public String getFirma() {
		return firma;
	}
	public void setFirma(String firma) {
		this.firma = firma;
	}
	public String getClaveServicio() {
		return ClaveServicio;
	}
	public void setClaveServicio(String claveServicio) {
		ClaveServicio = claveServicio;
	}
	 
	
}