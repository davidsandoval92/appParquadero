package com.ceiba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.model.Registro;
import com.ceiba.repository.RegistroRepository;

@Service
public class RegistroInterfaceImpl implements RegistroInterface {

	@Autowired
	RegistroRepository registroRepository;
	
	@Override
	public Iterable<Registro> listAllRegistro() {
		return registroRepository.findAll();
	}

	@Override
	public Registro getRegistroById(String idRegistro) {
		return registroRepository.findOne(idRegistro);
	}

	@Override
	public Registro getRegistroByIdVehiculo(String idVehiculo) {
		return registroRepository.findByidVehiculo(idVehiculo);
	}

	@Override
	public Registro saveRegistro(Registro registro) {
		return registroRepository.save(registro);
	}

	@Override
	public void deleteRegistro(String idRegistro) {
		registroRepository.delete(idRegistro);
	}

}
