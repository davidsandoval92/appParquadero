package com.ceiba.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the vehiculo database table.
 * 
 */
@Entity
@Table(name = "vehiculo")
@NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v")
public class Vehiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_VEHICULO")
	private String idVehiculo;

	@Column(name = "Cilindraje")
	private int cilindraje;

	@Column(name = "Color")
	private String color;

	@Column(name = "Marca")
	private String marca;

	@Column(name = "Placa")
	private String placa;
	
	public Vehiculo(){
		//Constructor Vacio
	}

	public Vehiculo(String idVehiculo, int cilindraje, String color, String marca, String placa) {
		this.idVehiculo = idVehiculo;
		this.cilindraje = cilindraje;
		this.color = color;
		this.marca = marca;
		this.placa = placa;
	}

	public Vehiculo(String idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getIdVehiculo() {
		return this.idVehiculo;
	}

	public void setIdVehiculo(String idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public int getCilindraje() {
		return this.cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getPlaca() {
		return this.placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

}