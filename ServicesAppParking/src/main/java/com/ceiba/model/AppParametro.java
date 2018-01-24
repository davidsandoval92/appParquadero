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

	@Column(name="valorDiaCarro")
	private double valorDiaCarro;

	@Column(name="valorDiaMoto")
	private double valorDiaMoto;

	@Column(name="valorHoraCarro")
	private double valorHoraCarro;

	@Column(name="valorHoraMoto")
	private double valorHoraMoto;

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
		return this.valorDiaCarro;
	}

	public void setValorDiaCarro(double valorDiaCarro) {
		this.valorDiaCarro = valorDiaCarro;
	}

	public double getValorDiaMoto() {
		return this.valorDiaMoto;
	}

	public void setValorDiaMoto(double valorDiaMoto) {
		this.valorDiaMoto = valorDiaMoto;
	}

	public double getValorHoraCarro() {
		return this.valorHoraCarro;
	}

	public void setValorHoraCarro(double valorHoraCarro) {
		this.valorHoraCarro = valorHoraCarro;
	}

	public double getValorHoraMoto() {
		return this.valorHoraMoto;
	}

	public void setValorHoraMoto(double valorHoraMoto) {
		this.valorHoraMoto = valorHoraMoto;
	}

}