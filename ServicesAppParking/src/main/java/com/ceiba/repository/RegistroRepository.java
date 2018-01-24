package com.ceiba.repository;

import org.springframework.data.repository.CrudRepository;

import com.ceiba.model.Registro;

public interface RegistroRepository extends CrudRepository<Registro, String> {

	Registro findByidVehiculo(String idVehiculo);
}
