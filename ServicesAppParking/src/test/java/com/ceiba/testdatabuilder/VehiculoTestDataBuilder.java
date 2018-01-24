package com.ceiba.testdatabuilder;

import com.ceiba.model.Vehiculo;

public class VehiculoTestDataBuilder {
	
	private String Idvehiculo;
	private int Cilindraje;
	private String Color;
	private String Marca;
	private String Placa;

	
	
	public VehiculoTestDataBuilder(){
		this.Idvehiculo = "2";
		this.Cilindraje = 2500;
		this.Color = "Azul";
		this.Marca = "Audi";
		this.Placa = "NMY987";
	}
	
	public VehiculoTestDataBuilder withIdVehiculo(String Idvehiculo) {
		this.Idvehiculo = Idvehiculo;
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

	public Vehiculo buil() {
		return new Vehiculo(this.Idvehiculo, this.Cilindraje, this.Color, this.Marca, this.Placa);
	}

}
