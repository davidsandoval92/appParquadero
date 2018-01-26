package com.ceiba.testdatabuilder;

import java.util.Date;

import com.ceiba.model.Registro;

public class RegistroTestDataBuilder {

	private String idRegistro;
	private String estado;
	private Date fechaingreso;
	private Date fechasalida;
	private double valorpagar;
	private String idVehiculo;

	public RegistroTestDataBuilder() {

		Date date = new Date();
		this.idRegistro = "2";
		this.estado = "activo";
		this.fechaingreso = date;
		this.fechasalida = date;
		this.valorpagar = 1555.20;
		this.idVehiculo = "1";
	}

	public RegistroTestDataBuilder withIdEstado(String estado) {
		this.estado = estado;
		return this;
	}
	
	public RegistroTestDataBuilder withIdRegistro(String idRegistro) {
		this.idRegistro = idRegistro;
		return this;
	}

	public RegistroTestDataBuilder withFechaingreso(Date fechaingreso) {
		this.fechaingreso = fechaingreso;
		return this;
	}

	public RegistroTestDataBuilder withFechasalida(Date fechasalida) {
		this.fechasalida = fechasalida;
		return this;
	}

	public RegistroTestDataBuilder withValorpagar(double valorpagar) {
		this.valorpagar = valorpagar;
		return this;
	}

	public RegistroTestDataBuilder withIdVehiculo(String idVehiculo) {
		this.idVehiculo = idVehiculo;
		return this;
	}

	public Registro build() {
		return new Registro(this.idRegistro, this.estado, this.fechaingreso, this.fechasalida, this.valorpagar, this.idVehiculo);
	}

}
