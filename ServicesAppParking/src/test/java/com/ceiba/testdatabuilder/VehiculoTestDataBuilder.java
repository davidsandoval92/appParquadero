package com.ceiba.testdatabuilder;

import com.ceiba.model.Vehiculo;

public class VehiculoTestDataBuilder {
	
	private String Idvehiculo;
	private int Activo;
	private int Cilindraje;
	private String Color;
	private String Marca;
	private String Placa;
	private String tipovehiculo;

	
	
	public VehiculoTestDataBuilder(){
		this.Idvehiculo = "2";
		this.Activo = 1;
		this.Cilindraje = 2500;
		this.Color = "Azul";
		this.Marca = "Audi";
		this.Placa = "NMY987";
		this.tipovehiculo = "carro";
	}
	
	public VehiculoTestDataBuilder withIdVehiculo(String Idvehiculo) {
		this.Idvehiculo = Idvehiculo;
		return this;
	}
	
	public VehiculoTestDataBuilder withActivo(int Activo) {
		this.Activo = Activo;
		return this;
	}
	
	public VehiculoTestDataBuilder withCilindraje(int Cilindraje) {
		this.Cilindraje = Cilindraje;
		return this;
	}
	
	public VehiculoTestDataBuilder withColor(String Color) {
		this.Color = Color;
		return this;
	}
	
	public VehiculoTestDataBuilder withMarca(String Marca) {
		this.Marca = Marca;
		return this;
	}

	public VehiculoTestDataBuilder withPlaca(String Placa) {
		this.Placa = Placa;
		return this;
	}
	
	public VehiculoTestDataBuilder withTipoVehiculo(String tipovehiculo) {
		this.tipovehiculo = tipovehiculo;
		return this;
	}

	public Vehiculo build() {
		return new Vehiculo(this.Idvehiculo, this.Activo, this.Cilindraje, this.Color, this.Marca, this.Placa, this.tipovehiculo);
	}

}
