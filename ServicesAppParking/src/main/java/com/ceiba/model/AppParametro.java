package com.ceiba.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the app_parametros database table.
 * 
 */
@Entity
@Table(name="app_parametros")
@NamedQuery(name="AppParametro.findAll", query="SELECT a FROM AppParametro a")
public class AppParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PARAMETROS")
	private String idParametros;

	@Column(name="parametro")
	private String parametro;

	@Column(name="valor")
	private String valor;

	public AppParametro() {
		//constructor vacio
	}
	
	public AppParametro(String idParametros, String parametro, String valor) {
		this.idParametros = idParametros;
		this.parametro = parametro;
		this.valor = valor;
	}

	public String getIdParametros() {
		return this.idParametros;
	}

	public void setIdParametros(String idParametros) {
		this.idParametros = idParametros;
	}

	public String getParametro() {
		return this.parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}