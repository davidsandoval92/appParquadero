package com.ceiba.service;


import com.ceiba.model.Vehiculo;

public interface VehiculoInterface {
	Iterable<Vehiculo> listAllVehiculos();

	Vehiculo getVehiculoById(String id);

	Vehiculo saveVehiculo(Vehiculo vehiculo);

	void deleteVehiculo(String id);
}
