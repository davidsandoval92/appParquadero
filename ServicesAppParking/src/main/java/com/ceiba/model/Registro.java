package com.ceiba.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the registro database table.
 * 
 */
@Entity
@Table(name = "registro")
@NamedQuery(name="Registro.findAll", query="SELECT r FROM Registro r")
public class Registro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_REGISTRO")
	private String idRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechaingreso")
	private Date fechaingreso;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fechasalida")
	private Date fechasalida;

	@Column(name="valorpagar")
	private double valorpagar;
	
	@Column(name="ID_VEHICULO",insertable = false, updatable = false)
	private String idVehiculo;

	//bi-directional many-to-one association to Vehiculo
	@ManyToOne()
	@JoinColumn(name="ID_VEHICULO")
	private Vehiculo vehiculo;

	public Registro() {
		// Constructor vacio.
	}

	public String getIdRegistro() {
		return this.idRegistro;
	}

	public void setIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
	}

	public Date getFechaingreso() {
		return this.fechaingreso;
	}

	public void setFechaingreso(Date fechaingreso) {
		this.fechaingreso = fechaingreso;
	}

	public Date getFechasalida() {
		return this.fechasalida;
	}

	public void setFechasalida(Date fechasalida) {
		this.fechasalida = fechasalida;
	}

	public double getValorpagar() {
		return this.valorpagar;
	}

	public void setValorpagar(double valorpagar) {
		this.valorpagar = valorpagar;
	}

	public Vehiculo getVehiculo() {
		return this.vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	
	public String getIdVehiculo() {
		return this.idVehiculo;
	}

}