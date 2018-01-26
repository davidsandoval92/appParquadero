package com.ceiba.repository;


import org.springframework.data.repository.CrudRepository;

import com.ceiba.model.Vehiculo;

public interface VehiculoRepository extends CrudRepository<Vehiculo, String> {
	
	public Iterable<Vehiculo> findBytipoVehiculoAndActivo(String tipoVehiculo, int activo);
	
	public Vehiculo findByplaca(String placa);

}
