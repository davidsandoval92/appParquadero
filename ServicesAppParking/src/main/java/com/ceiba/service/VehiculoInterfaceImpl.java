package com.ceiba.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.model.Vehiculo;
import com.ceiba.repository.VehiculoRepository;

@Service
public class VehiculoInterfaceImpl implements VehiculoInterface {

	@Autowired
	VehiculoRepository vehiculoRepository;

	@Override
	public Iterable<Vehiculo> listAllVehiculos() {
		return vehiculoRepository.findAll();
	}

	@Override
	public Vehiculo getVehiculoById(String id) {
		return vehiculoRepository.findOne(id);
	}

	@Override
	public Vehiculo saveVehiculo(Vehiculo vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public void deleteVehiculo(String id) {
		vehiculoRepository.delete(id);

	}

}
