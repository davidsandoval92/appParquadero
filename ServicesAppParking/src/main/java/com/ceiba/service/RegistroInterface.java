package com.ceiba.service;

import com.ceiba.model.Registro;

public interface RegistroInterface {
	Iterable<Registro> listAllRegistro();

	Registro getRegistroById(String id);
	
	Registro getRegistroByIdVehiculo(String idVehiculo);

	Registro saveRegistro(Registro vehiculo);

	void deleteRegistro(String id);

}
