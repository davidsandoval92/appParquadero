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

	@Column(name="valordiacarro")
	private double valordiacarro;

	@Column(name="valordiamoto")
	private double valordiamoto;

	@Column(name="valorhoracarro")
	private double valorhoracarro;

	@Column(name="valorhoramoto")
	private double valorhoramoto;

	public AppParametro() {
		// Constructor vacio.
	}

	public String getIdParametros() {
		return this.idParametros;
	}

	public void setIdParametros(String idParametros) {
		this.idParametros = idParametros;
	}

	public double getValorDiaCarro() {
		return this.valordiacarro;
	}

	public void setValorDiaCarro(double valordiacarro) {
		this.valordiacarro = valordiacarro;
	}

	public double getValorDiaMoto() {
		return this.valordiamoto;
	}

	public void setValorDiaMoto(double valordiamoto) {
		this.valordiamoto = valordiamoto;
	}

	public double getValorHoraCarro() {
		return this.valorhoracarro;
	}

	public void setValorHoraCarro(double valorhoracarro) {
		this.valorhoracarro = valorhoracarro;
	}

	public double getValorHoraMoto() {
		return this.valorhoramoto;
	}

	public void setValorHoraMoto(double valorhoramoto) {
		this.valorhoramoto = valorhoramoto;
	}

}