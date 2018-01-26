package com.ceiba.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ceiba.model.Registro;

public interface RegistroRepository extends CrudRepository<Registro, String> {

	List<Registro> findByidVehiculo(String idVehiculo);
	
	Registro findByidVehiculoAndEstado(String idVehiculo, String estado);
}
