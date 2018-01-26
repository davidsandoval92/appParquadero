package com.ceiba.service;


import com.ceiba.model.Vehiculo;

public interface VehiculoService {
	Iterable<Vehiculo> listAllVehiculos();

	Vehiculo getVehiculoById(String id);

	Vehiculo saveVehiculo(Vehiculo vehiculo);

	void deleteVehiculo(String id);
	
	Iterable<Vehiculo> cantidadVehiculosActivos(String tipovehiculo, int activo);
	
	Vehiculo getVehiculoByPlaca(String placa);
}
