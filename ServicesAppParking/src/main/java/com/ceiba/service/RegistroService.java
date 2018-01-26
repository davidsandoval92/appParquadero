package com.ceiba.service;

import java.util.List;

import com.ceiba.model.Registro;

public interface RegistroService {
	Iterable<Registro> listAllRegistro();

	Registro getRegistroById(String id);
	
	List<Registro> getRegistrosByIdVehiculo(String idVehiculo);

	Registro saveRegistro(Registro registro);

	void deleteRegistro(String id);
	
	Registro getRegistroByidVehiculoAndEstado(String idVehiculo, String estado);

}
