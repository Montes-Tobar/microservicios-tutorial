package com.example.usuario.service.entidad;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/** Se utiliza la anotación @Entity porque se está utilizando la
 * dependencia de base de datos en memoria H2*/
@Entity
public class Usuario {
	
	/** Se usa la anotación @Id para indicar que el atributo id 
	 * clave primaria. Se usa el @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * para indicar que es un atributo autoincrementable*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	private String email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
